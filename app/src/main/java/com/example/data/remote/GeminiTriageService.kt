package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.AppLanguage
import com.example.data.model.TriageLevel
import com.example.data.model.TriageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiTriageService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun getTriageAssessment(
        symptomIds: Set<String>,
        customSymptomsText: String,
        durationId: String,
        selectedRedFlags: Set<String>,
        lang: AppLanguage
    ): TriageResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        // If no API key configured or offline, fallback immediately to offline clinical engine
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.d("GeminiTriageService", "Using OfflineTriageEngine due to placeholder/blank API key")
            return@withContext OfflineTriageEngine.evaluate(
                symptomIds, customSymptomsText, durationId, selectedRedFlags, lang
            )
        }

        val promptLanguageName = when (lang) {
            AppLanguage.MARATHI -> "Marathi (मराठी)"
            AppLanguage.HINDI -> "Hindi (हिंदी)"
            AppLanguage.ENGLISH -> "English"
        }

        val symptomsDescription = buildString {
            append("Selected symptoms: ").append(symptomIds.joinToString(", "))
            if (customSymptomsText.isNotBlank()) {
                append(". User description: ").append(customSymptomsText)
            }
            append(". Duration: ").append(durationId)
            if (selectedRedFlags.isNotEmpty()) {
                append(". Red flags reported: ").append(selectedRedFlags.joinToString(", "))
            }
        }

        val systemInstructionText = """
            You are JeevanAI, an AI healthcare triage assistant for rural and Tier 3/4 communities in India.
            Your role is to assess symptoms, identify triage level (ROUTINE, DOCTOR_CONSULT, or URGENT), and guide users to appropriate rural healthcare (ASHA worker, Sub-center, Primary Health Centre PHC, Community Health Centre CHC, or 108 Emergency).
            You DO NOT diagnose diseases or replace medical doctors.
            You MUST respond in $promptLanguageName.
            Output ONLY valid JSON matching this structure:
            {
              "level": "ROUTINE" | "DOCTOR_CONSULT" | "URGENT",
              "title": "short headline in $promptLanguageName",
              "summary": "2-3 clear empathetic sentences in $promptLanguageName",
              "actionSteps": ["step 1", "step 2", "step 3"],
              "homeRemedies": ["safe home remedy or hydration tip 1", "tip 2"],
              "redFlagWarnings": ["warning sign 1", "warning sign 2"],
              "recommendedFacility": "e.g. Primary Health Centre (PHC) / 108 Ambulance",
              "spokenSummary": "Short 1-2 sentence summary in $promptLanguageName suitable for reading aloud"
            }
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            val contentsArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", "Assess these symptoms: $symptomsDescription")
                        })
                    })
                })
            }
            put("contents", contentsArray)

            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", systemInstructionText)
                    })
                })
            })

            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.2)
            })
        }

        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            if (!response.isSuccessful || responseBody.isBlank()) {
                Log.w("GeminiTriageService", "HTTP error ${response.code}: $responseBody")
                return@withContext OfflineTriageEngine.evaluate(
                    symptomIds, customSymptomsText, durationId, selectedRedFlags, lang
                )
            }

            val rootJson = JSONObject(responseBody)
            val candidates = rootJson.optJSONArray("candidates")
            val candidate = candidates?.optJSONObject(0)
            val content = candidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text") ?: ""

            if (text.isNotBlank()) {
                val parsed = JSONObject(text)
                val levelStr = parsed.optString("level", "ROUTINE")
                val level = when (levelStr.uppercase()) {
                    "URGENT" -> TriageLevel.URGENT
                    "DOCTOR_CONSULT" -> TriageLevel.DOCTOR_CONSULT
                    else -> TriageLevel.ROUTINE
                }

                val actionStepsList = mutableListOf<String>()
                val actionArray = parsed.optJSONArray("actionSteps")
                if (actionArray != null) {
                    for (i in 0 until actionArray.length()) {
                        actionStepsList.add(actionArray.getString(i))
                    }
                }

                val homeRemediesList = mutableListOf<String>()
                val remediesArray = parsed.optJSONArray("homeRemedies")
                if (remediesArray != null) {
                    for (i in 0 until remediesArray.length()) {
                        homeRemediesList.add(remediesArray.getString(i))
                    }
                }

                val redFlagsList = mutableListOf<String>()
                val flagsArray = parsed.optJSONArray("redFlagWarnings")
                if (flagsArray != null) {
                    for (i in 0 until flagsArray.length()) {
                        redFlagsList.add(flagsArray.getString(i))
                    }
                }

                return@withContext TriageResult(
                    level = level,
                    title = parsed.optString("title", level.getLabel(lang)),
                    summary = parsed.optString("summary", ""),
                    actionSteps = actionStepsList.ifEmpty { listOf(level.getActionAdvice(lang)) },
                    homeRemedies = homeRemediesList,
                    redFlagWarnings = redFlagsList,
                    recommendedFacility = parsed.optString("recommendedFacility", "Primary Health Centre (PHC)"),
                    spokenSummary = parsed.optString("spokenSummary", parsed.optString("summary", "")),
                    isAiGenerated = true
                )
            } else {
                return@withContext OfflineTriageEngine.evaluate(
                    symptomIds, customSymptomsText, durationId, selectedRedFlags, lang
                )
            }
        } catch (e: Exception) {
            Log.e("GeminiTriageService", "Gemini API call failed, falling back to offline triage", e)
            return@withContext OfflineTriageEngine.evaluate(
                symptomIds, customSymptomsText, durationId, selectedRedFlags, lang
            )
        }
    }
}
