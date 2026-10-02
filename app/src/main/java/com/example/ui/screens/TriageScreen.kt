package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.DefaultDurationOptions
import com.example.data.model.DefaultRedFlagQuestions
import com.example.data.model.DefaultSymptomPresets
import com.example.data.model.TriageLevel
import com.example.data.model.TriageResult
import com.example.ui.components.MedicalDisclaimerCard
import com.example.ui.components.SpeakerAudioButton
import com.example.ui.components.TriageLevelBadge
import com.example.ui.localization.AppStrings
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.TealOnPrimary
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.UrgentRed

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TriageScreen(
    lang: AppLanguage,
    triageStep: Int,
    selectedSymptoms: Set<String>,
    customSymptomInput: String,
    selectedDuration: String,
    selectedRedFlags: Set<String>,
    isAnalyzing: Boolean,
    triageResult: TriageResult?,
    isSpeaking: Boolean,
    onToggleSymptom: (String) -> Unit,
    onSetCustomSymptom: (String) -> Unit,
    onAppendVoiceTranscript: (String) -> Unit,
    onSetDuration: (String) -> Unit,
    onToggleRedFlag: (String) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onRestartTriage: () -> Unit,
    onSaveToRecord: () -> Unit,
    onSpeak: (String) -> Unit,
    onStopAudio: () -> Unit,
    onNavigateFacilities: () -> Unit,
    onCallAmbulance: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Speech Recognition Launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenData = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = spokenData?.firstOrNull()
            if (!recognizedText.isNullOrBlank()) {
                onAppendVoiceTranscript(recognizedText)
            }
        }
    }

    fun launchVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang.localeTag)
            putExtra(RecognizerIntent.EXTRA_PROMPT, AppStrings.tapToSpeak(lang))
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            // Speech recognition not supported on device fallback gracefully
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("triage_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step Header & Progress
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AppStrings.tabTriage(lang),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary
                    )
                    if (triageStep > 1) {
                        OutlinedButton(
                            onClick = onRestartTriage,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("restart_triage_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Restart", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = AppStrings.restartTriage(lang), fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                // Visual Step Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (step in 1..4) {
                        val isComplete = step < triageStep
                        val isCurrent = step == triageStep
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    when {
                                        isComplete -> HealthGreen
                                        isCurrent -> TealPrimary
                                        else -> Color(0xFFCBD5E1)
                                    }
                                )
                        )
                    }
                }
            }
        }

        // STEP 1: CHIEF SYMPTOMS & VOICE INPUT
        if (triageStep == 1) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = AppStrings.step1Title(lang),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Big Voice Input Assistant Button
                        Button(
                            onClick = { launchVoiceRecognition() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("voice_input_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TealPrimary,
                                contentColor = TealOnPrimary
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Mic",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = AppStrings.tapToSpeak(lang),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Custom Symptom Text Field
                        OutlinedTextField(
                            value = customSymptomInput,
                            onValueChange = onSetCustomSymptom,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("custom_symptom_textfield"),
                            placeholder = {
                                Text(
                                    text = AppStrings.describeSymptomsPlaceholder(lang),
                                    fontSize = 14.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            minLines = 2,
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "किंवा खालील लक्षणांपैकी निवडा (Tap to Select):",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Symptom Preset Chips
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DefaultSymptomPresets.forEach { preset ->
                                val isSelected = selectedSymptoms.contains(preset.id)
                                val chipBorder = if (preset.isRedFlag) {
                                    BorderStroke(1.dp, UrgentRed.copy(alpha = 0.5f))
                                } else {
                                    BorderStroke(1.dp, if (isSelected) TealPrimary else Color(0xFFCBD5E1))
                                }

                                Surface(
                                    modifier = Modifier
                                        .testTag("symptom_chip_${preset.id}")
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable { onToggleSymptom(preset.id) },
                                    color = if (isSelected) {
                                        if (preset.isRedFlag) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.primaryContainer
                                    } else MaterialTheme.colorScheme.surface,
                                    border = chipBorder,
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = if (preset.isRedFlag) UrgentRed else TealPrimary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                        }
                                        Text(
                                            text = preset.getLabel(lang),
                                            fontSize = 14.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) {
                                                if (preset.isRedFlag) UrgentRed else TealPrimary
                                            } else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Next button
            item {
                Button(
                    onClick = onNextStep,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("step1_next_button"),
                    enabled = selectedSymptoms.isNotEmpty() || customSymptomInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = AppStrings.nextButton(lang),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        }

        // STEP 2: DURATION QUESTIONS
        if (triageStep == 2) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = AppStrings.step2Title(lang),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        DefaultDurationOptions.forEach { option ->
                            val isSelected = selectedDuration == option.id
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .testTag("duration_option_${option.id}")
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onSetDuration(option.id) },
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) BorderStroke(1.5.dp, TealPrimary) else null,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) TealPrimary else Color.Transparent)
                                            .border(2.dp, if (isSelected) TealPrimary else Color.Gray, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = option.getLabel(lang),
                                        fontSize = 15.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Navigation Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onPreviousStep,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("step2_back_button"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = AppStrings.backButton(lang), fontSize = 15.sp)
                    }
                    Button(
                        onClick = onNextStep,
                        modifier = Modifier
                            .weight(1.4f)
                            .height(52.dp)
                            .testTag("step2_next_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(text = AppStrings.nextButton(lang), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                    }
                }
            }
        }

        // STEP 3: RED FLAGS & SEVERITY CHECK
        if (triageStep == 3) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Alert",
                                tint = UrgentRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = AppStrings.step3Title(lang),
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = UrgentRed
                            )
                        }
                        Text(
                            text = "खालीलपैकी कोणतेही लक्षण आढळल्यास त्यावर क्लिक करा (किंवा काही नसल्यास थेट पुढे जा):",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                        )

                        DefaultRedFlagQuestions.forEach { question ->
                            val isChecked = selectedRedFlags.contains(question.id)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .testTag("redflag_${question.id}")
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onToggleRedFlag(question.id) },
                                color = if (isChecked) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isChecked) BorderStroke(1.5.dp, UrgentRed) else null,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isChecked) UrgentRed else Color.Transparent)
                                            .border(2.dp, if (isChecked) UrgentRed else Color.Gray, RoundedCornerShape(4.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isChecked) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Checked",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = question.getLabel(lang),
                                        fontSize = 14.sp,
                                        fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isChecked) UrgentRed else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Navigation Row with Analyze Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onPreviousStep,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("step3_back_button"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = AppStrings.backButton(lang), fontSize = 15.sp)
                    }
                    Button(
                        onClick = onNextStep,
                        modifier = Modifier
                            .weight(1.6f)
                            .height(52.dp)
                            .testTag("step3_submit_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        if (isAnalyzing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "तपासत आहे...", fontSize = 14.sp)
                        } else {
                            Text(text = "AI तपासणी करा", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Submit")
                        }
                    }
                }
            }
        }

        // STEP 4: TRIAGE RESULT CARD
        if (triageStep == 4 && triageResult != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("triage_result_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = BorderStroke(2.dp, triageResult.level.color)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TriageLevelBadge(level = triageResult.level, lang = lang)
                            SpeakerAudioButton(
                                textToSpeak = triageResult.spokenSummary.ifBlank { triageResult.summary },
                                isSpeaking = isSpeaking,
                                onSpeak = onSpeak,
                                onStop = onStopAudio,
                                lang = lang
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = triageResult.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = triageResult.level.color
                        )

                        Text(
                            text = triageResult.summary,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        // Action Steps Checklist
                        if (triageResult.actionSteps.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "काय करावे? (Action Steps):",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            triageResult.actionSteps.forEach { step ->
                                Row(
                                    modifier = Modifier.padding(top = 6.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(text = "• ", fontWeight = FontWeight.Bold, color = triageResult.level.color)
                                    Text(text = step, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        // Home Care / Hydration
                        if (triageResult.homeRemedies.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "घरगुती काळजी व विश्रांती (Home Care):",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = HealthGreen
                            )
                            triageResult.homeRemedies.forEach { remedy ->
                                Row(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(text = "✓ ", fontWeight = FontWeight.Bold, color = HealthGreen)
                                    Text(text = remedy, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        // Recommended Facility
                        if (triageResult.recommendedFacility.isNotBlank()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalHospital,
                                        contentDescription = "Hospital",
                                        tint = TealPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "शिफारस केलेले केंद्र:",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = triageResult.recommendedFacility,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Urgent Direct Dial button if Red Alert
            if (triageResult.level == TriageLevel.URGENT) {
                item {
                    Button(
                        onClick = onCallAmbulance,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("call_108_urgent_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = UrgentRed),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Call 108")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = AppStrings.callAmbulance108(lang), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Action Buttons: Save to Record & View Facilities
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onSaveToRecord,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("save_triage_record_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "Save")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "जतन करा", fontSize = 14.sp)
                    }
                    Button(
                        onClick = onNavigateFacilities,
                        modifier = Modifier
                            .weight(1.3f)
                            .height(50.dp)
                            .testTag("view_facilities_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.LocalHospital, contentDescription = "Facilities")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "रुग्णालय शोधा", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Start New Triage
            item {
                Button(
                    onClick = onRestartTriage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("start_new_triage_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = AppStrings.restartTriage(lang),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Disclaimer
        item {
            MedicalDisclaimerCard(lang = lang)
        }
    }
}
