package com.example.data.remote

import com.example.data.model.AppLanguage
import com.example.data.model.TriageLevel
import com.example.data.model.TriageResult

object OfflineTriageEngine {

    fun evaluate(
        symptomIds: Set<String>,
        customSymptomsText: String,
        durationId: String,
        selectedRedFlags: Set<String>,
        lang: AppLanguage
    ): TriageResult {
        val hasRedFlag = selectedRedFlags.isNotEmpty() ||
                symptomIds.contains("chest_pain") ||
                symptomIds.contains("snake_bite") ||
                symptomIds.contains("maternal") ||
                symptomIds.contains("unconscious") ||
                symptomIds.contains("breathing")

        val isModerate = symptomIds.contains("vomiting") ||
                symptomIds.contains("injury") ||
                symptomIds.contains("headache") ||
                durationId == "one_week" ||
                durationId == "chronic"

        val triageLevel = when {
            hasRedFlag -> TriageLevel.URGENT
            isModerate -> TriageLevel.DOCTOR_CONSULT
            else -> TriageLevel.ROUTINE
        }

        return when (triageLevel) {
            TriageLevel.URGENT -> createUrgentResult(symptomIds, lang)
            TriageLevel.DOCTOR_CONSULT -> createDoctorResult(symptomIds, durationId, lang)
            TriageLevel.ROUTINE -> createRoutineResult(symptomIds, lang)
        }
    }

    private fun createUrgentResult(symptomIds: Set<String>, lang: AppLanguage): TriageResult {
        val isSnakeBite = symptomIds.contains("snake_bite")
        val isChestPain = symptomIds.contains("chest_pain")
        val isBreathing = symptomIds.contains("breathing")
        val isMaternal = symptomIds.contains("maternal")

        return when (lang) {
            AppLanguage.MARATHI -> TriageResult(
                level = TriageLevel.URGENT,
                title = "तातडीची वैद्यकीय मदत आवश्यक (Red Alert)",
                summary = "तुमच्या लक्षणांमध्ये गंभीर धोक्याचे संकेत दिसत आहेत. वेळ न दवडता ताबडतोब रुग्णालयात जा किंवा १०८ ला फोन करा.",
                actionSteps = listOf(
                    "१०८ रुग्णवाहिकेशी तात्काळ संपर्क साधा (मोफत आपत्कालीन वाहन).",
                    "रुग्णाला बसवून ठेवा, शांत राहण्यास सांगा आणि कपडे सैल करा.",
                    if (isSnakeBite) "चावलेला हात/पाय हलवू नका; ताबडतोब अँटी-व्हेनम असलेल्या रुग्णालयात जा."
                    else if (isChestPain) "शारीरिक श्रम पूर्ण बंद करा. त्वरित ईसीजी (ECG) सुविधा असलेल्या रुग्णालयात जा."
                    else if (isBreathing) "हवेशीर जागी बसा, डोके थोडे वर उंचावून ठेवा."
                    else if (isMaternal) "१०२ किंवा १०८ ला फोन करून तातडीने प्रसूती कक्षात जा."
                    else "जवळच्या ग्रामीण रुग्णालय (CHC) किंवा जिल्हा रुग्णालयाकडे निघा."
                ),
                homeRemedies = listOf(
                    "अशा गंभीर स्थितीत घरगुती उपचारांवर वेळ वाया घालवू नका.",
                    "तोंडाने खायला किंवा प्यायला काहीही देऊ नका जर व्यक्ती बेशुद्ध किंवा उलट्या करत असेल."
                ),
                redFlagWarnings = listOf(
                    "छातीत डाव्या बाजूला तीव्र कळ किंवा घाम येणे",
                    "श्वास घेण्यास तीव्र अडचण किंवा ओठ निळे पडणे",
                    "साप किंवा विषारी कीटक चावणे",
                    "बेशुद्ध पडणे किंवा झटके येणे"
                ),
                recommendedFacility = "उप-जिल्हा किंवा जिल्हा सामान्य रुग्णालय (Civil Hospital) / १०८ रुग्णवाहिका",
                spokenSummary = "तातडीची वैद्यकीय मदत आवश्यक आहे. कृपया ताबडतोब जवळच्या रुग्णालयात जा किंवा १०८ ला फोन करा.",
                isAiGenerated = false
            )
            AppLanguage.HINDI -> TriageResult(
                level = TriageLevel.URGENT,
                title = "तत्काल आपातकालीन चिकित्सा आवश्यक (Red Alert)",
                summary = "आपके लक्षणों में गंभीर खतरे के संकेत हैं। बिना समय गंवाए तुरंत अस्पताल जाएं या 108 पर कॉल करें।",
                actionSteps = listOf(
                    "तुरंत 108 निःशुल्क एम्बुलेंस को कॉल करें।",
                    "मरीज को शांत रखें और तंग कपड़े ढीले करें।",
                    if (isSnakeBite) "काटे गए अंग को बिल्कुल न हिलाएं; तुरंत एंटी-वेनम वाले अस्पताल जाएं।"
                    else if (isChestPain) "शारीरिक मेहनत रोकें, तुरंत ईसीजी (ECG) वाले अस्पताल जाएं।"
                    else if (isBreathing) "हवादार जगह पर बैठें और सिर ऊंचा रखें।"
                    else if (isMaternal) "तुरंत 102 या 108 एम्बुलेंस से अस्पताल पहुंचें।"
                    else "निकटतम सामुदायिक स्वास्थ्य केंद्र (CHC) या जिला अस्पताल जाएं।"
                ),
                homeRemedies = listOf(
                    "गंभीर स्थिति में घरेलू नुस्खों पर समय बर्बाद न करें।",
                    "बेहोशी या उल्टी की स्थिति में मुंह में कुछ भी न डालें।"
                ),
                redFlagWarnings = listOf(
                    "छाती में तेज जकड़न या ठंडा पसीना",
                    "सांस लेने में अत्यधिक तकलीफ या होंठ नीले पड़ना",
                    "सर्पदंश या जहरीला कीड़ा काटना",
                    "बेहोशी या झटके आना"
                ),
                recommendedFacility = "जिला नागरिक अस्पताल (Civil Hospital) / 108 आपातकालीन एम्बुलेंस",
                spokenSummary = "तत्काल चिकित्सा आवश्यक है। कृपया बिना देरी किए नजदीकी अस्पताल जाएं या 108 पर कॉल करें।",
                isAiGenerated = false
            )
            AppLanguage.ENGLISH -> TriageResult(
                level = TriageLevel.URGENT,
                title = "Urgent Medical Attention Needed (Red Alert)",
                summary = "Your symptoms indicate serious danger signs. Seek immediate emergency care or call 108 ambulance now.",
                actionSteps = listOf(
                    "Call 108 Free Emergency Ambulance immediately.",
                    "Keep patient seated comfortably and loosen restrictive clothes.",
                    if (isSnakeBite) "Keep bitten limb immobilized; rush to hospital with Anti-Snake Venom."
                    else if (isChestPain) "Avoid physical exertion; go to facility with emergency ECG support."
                    else if (isBreathing) "Sit upright in a well-ventilated area."
                    else if (isMaternal) "Call 102/108 immediately for urgent maternity transport."
                    else "Head immediately to the nearest Sub-District or Civil Hospital."
                ),
                homeRemedies = listOf(
                    "Do not waste time with home remedies in emergency situations.",
                    "Do not give oral fluids if patient is drowsy or vomiting."
                ),
                redFlagWarnings = listOf(
                    "Severe crushing chest pain or sweating",
                    "Extreme shortness of breath or blue lips",
                    "Snake bite or toxin exposure",
                    "Unconsciousness or seizures"
                ),
                recommendedFacility = "District Civil Hospital / 108 Emergency Ambulance",
                spokenSummary = "Urgent medical attention is needed. Please call 108 or go to the nearest hospital immediately.",
                isAiGenerated = false
            )
        }
    }

    private fun createDoctorResult(symptomIds: Set<String>, durationId: String, lang: AppLanguage): TriageResult {
        return when (lang) {
            AppLanguage.MARATHI -> TriageResult(
                level = TriageLevel.DOCTOR_CONSULT,
                title = "डॉक्टरांचा सल्ला आवश्यक (PHC / CHC)",
                summary = "तुमचा त्रास काही दिवसांपासून चालू आहे किंवा वाढण्याची शक्यता आहे. २४ ते ४८ तासांत जवळच्या प्राथमिक आरोग्य केंद्रातील (PHC) डॉक्टरांना दाखवणे योग्य ठरेल.",
                actionSteps = listOf(
                    "जवळच्या प्राथमिक आरोग्य केंद्र (PHC) किंवा ग्रामीण रुग्णालयात (CHC) बाह्यरुग्ण (OPD) वेळेत जा.",
                    "डॉक्टरांना त्रास किती दिवसांपासून आहे आणि कोणती औषधे घेतली ते सांगा.",
                    "ताप असल्यास तापमापीने (थर्मामीटर) दिवसातून २-३ वेळा मोजा.",
                    "भरपूर उकळून थंड केलेले पाणी, मऊ भात, खिचडी आणि मुगाचे कढण घ्या."
                ),
                homeRemedies = listOf(
                    "हळदीचे कोमट दूध किंवा तुळस-आले काढा (खोकला/सर्दीसाठी)",
                    "घरगुती ओआरएस (ORS) पाणी (उलटी किंवा जुलाबासाठी)",
                    "कपाळावर साध्या पाण्याच्या पट्ट्या (तापासाठी)"
                ),
                redFlagWarnings = listOf(
                    "ताप १०२°F पेक्षा जास्त वाढल्यास",
                    "श्वास घेताना धाप लागल्यास",
                    "पाणी किंवा जेवण अजिबात पोटात न टिकल्यास"
                ),
                recommendedFacility = "प्राथमिक आरोग्य केंद्र (PHC) किंवा ग्रामीण रुग्णालय (CHC)",
                spokenSummary = "तुम्हाला डॉक्टरांचा सल्ला घेण्याची गरज आहे. कृपया २४ ते ४८ तासांत जवळच्या प्राथमिक आरोग्य केंद्रात जा.",
                isAiGenerated = false
            )
            AppLanguage.HINDI -> TriageResult(
                level = TriageLevel.DOCTOR_CONSULT,
                title = "डॉक्टर से परामर्श आवश्यक (PHC / CHC)",
                summary = "आपकी समस्या कुछ दिनों से बनी हुई है। 24 से 48 घंटे के भीतर नजदीकी प्राथमिक स्वास्थ्य केंद्र (PHC) या डॉक्टर से जांच कराना सुरक्षित रहेगा।",
                actionSteps = listOf(
                    "नजदीकी प्राथमिक स्वास्थ्य केंद्र (PHC) या सामुदायिक स्वास्थ्य केंद्र (CHC) जाएं।",
                    "डॉक्टर को बताएं कि परेशानी कब से है और आपने कोई दवा ली है या नहीं।",
                    "तापमान दिन में 2-3 बार मापें।",
                    "उबला हुआ पानी, मूंग दाल खिचड़ी और हल्का सुपाच्य भोजन लें।"
                ),
                homeRemedies = listOf(
                    "तुलसी-अदरक का काढ़ा या हल्दी वाला दूध",
                    "घरेलू ओआरएस (ORS) का घोल",
                    "बुखार में माथे पर ताजे पानी की पट्टियां"
                ),
                redFlagWarnings = listOf(
                    "बुखार 102°F से अधिक बढ़ जाना",
                    "सांस फूलने लगना",
                    "पानी या भोजन बिल्कुल न पचना"
                ),
                recommendedFacility = "प्राथमिक स्वास्थ्य केंद्र (PHC) या सामुदायिक स्वास्थ्य केंद्र (CHC)",
                spokenSummary = "आपको डॉक्टर से परामर्श लेना चाहिए। कृपया 24 से 48 घंटे में नजदीकी प्राथमिक स्वास्थ्य केंद्र जाएं।",
                isAiGenerated = false
            )
            AppLanguage.ENGLISH -> TriageResult(
                level = TriageLevel.DOCTOR_CONSULT,
                title = "Doctor Consultation Recommended",
                summary = "Your symptoms have persisted for a few days. We recommend visiting a doctor at your nearest Primary Health Centre (PHC) within 24-48 hours.",
                actionSteps = listOf(
                    "Visit your nearest Primary Health Centre (PHC) or Community Health Centre (CHC) during OPD hours.",
                    "Tell the healthcare worker the exact duration of symptoms and any medications taken.",
                    "Record body temperature 2-3 times daily if having fever.",
                    "Stay hydrated with clean boiled water, soups, and easy-to-digest food."
                ),
                homeRemedies = listOf(
                    "Warm turmeric milk or herbal decoction for cold & cough",
                    "Oral Rehydration Solution (ORS) for upset stomach",
                    "Tepid water sponging on forehead for fever"
                ),
                redFlagWarnings = listOf(
                    "Fever spiking over 102°F",
                    "Onset of breathlessness or persistent chest tightness",
                    "Inability to keep liquids down"
                ),
                recommendedFacility = "Primary Health Centre (PHC) or Community Health Centre (CHC)",
                spokenSummary = "A doctor consultation is recommended. Please visit your nearest Primary Health Centre within 24 to 48 hours.",
                isAiGenerated = false
            )
        }
    }

    private fun createRoutineResult(symptomIds: Set<String>, lang: AppLanguage): TriageResult {
        return when (lang) {
            AppLanguage.MARATHI -> TriageResult(
                level = TriageLevel.ROUTINE,
                title = "नियमित काळजी व घरगुती उपचार (Routine Care)",
                summary = "सध्या तुमची लक्षणे सौम्य दिसत आहेत. योग्य विश्रांती आणि घरगुती काळजीने तुम्हाला आराम मिळू शकतो.",
                actionSteps = listOf(
                    "शरीराला पूर्ण विश्रांती द्या आणि पुरेशी झोप घ्या.",
                    "दिवसभरात २ ते ३ लिटर स्वच्छ, कोमट पाणी किंवा ताजे द्रवपदार्थ प्या.",
                    "हलका, पचायला सोपा आहार घ्या (उदा. डाळ-भात, पेज, फळे).",
                    "लक्षणे २-३ दिवसांत बरी न झाल्यास किंवा वाढल्यास डॉक्टरांकडे जा."
                ),
                homeRemedies = listOf(
                    "घसा खवखवत असल्यास कोमट पाण्यात मीठ टाकून गुळण्या करा.",
                    "सर्दी असल्यास साध्या गरम पाण्याची वाफ घ्या.",
                    "बद्धकोष्ठता असल्यास कोमट पाणी आणि फायबरयुक्त भाजीपाला घ्या."
                ),
                redFlagWarnings = listOf(
                    "अचानक तीव्र ताप आल्यास",
                    "श्वास घेण्यास त्रास जाणवू लागल्यास",
                    "तीव्र अशक्तपणा किंवा चक्कर आल्यास"
                ),
                recommendedFacility = "स्थानिक आशा सेविका (ASHA Worker) किंवा प्राथमिक आरोग्य केंद्र (PHC)",
                spokenSummary = "तुमची लक्षणे सौम्य आहेत. पुरेसा आराम करा आणि भरपूर पाणी प्या. त्रास वाढल्यास डॉक्टरांकडे जा.",
                isAiGenerated = false
            )
            AppLanguage.HINDI -> TriageResult(
                level = TriageLevel.ROUTINE,
                title = "नियमित देखभाल और घरेलू देखभाल (Routine Care)",
                summary = "वर्तमान में आपके लक्षण हल्के प्रतीत होते हैं। उचित आराम और घरेलू देखभाल से सुधार हो सकता है।",
                actionSteps = listOf(
                    "शरीर को पूरा आराम दें और भरपूर नींद लें।",
                    "दिन में 2 से 3 लीटर साफ, गुनगुना पानी या तरल पदार्थ पिएं।",
                    "हल्का और सुपाच्य भोजन लें (जैसे दलिया, खिचड़ी, सूप)।",
                    "यदि लक्षण 2-3 दिनों में ठीक न हों तो डॉक्टर से मिलें।"
                ),
                homeRemedies = listOf(
                    "गले में खराश होने पर गुनगुने पानी में नमक डालकर गरारे करें।",
                    "जुकाम में सादे गर्म पानी की भाप लें।",
                    "ताजे फल और हरी सब्जियां खाएं।"
                ),
                redFlagWarnings = listOf(
                    "अचानक तेज बुखार आना",
                    "सांस लेने में भारीपन होना",
                    "अत्यधिक कमजोरी महसूस होना"
                ),
                recommendedFacility = "स्थानीय आशा कार्यकर्ता (ASHA Worker) या प्राथमिक स्वास्थ्य केंद्र (PHC)",
                spokenSummary = "आपके लक्षण हल्के हैं। आराम करें और खूब पानी पिएं। समस्या बढ़ने पर डॉक्टर से मिलें।",
                isAiGenerated = false
            )
            AppLanguage.ENGLISH -> TriageResult(
                level = TriageLevel.ROUTINE,
                title = "Routine Care & Home Monitoring",
                summary = "Your symptoms appear mild. Home care, hydration, and proper rest are recommended at this stage.",
                actionSteps = listOf(
                    "Get adequate bed rest and avoid heavy physical labor.",
                    "Drink 2-3 liters of clean, warm water or fresh fluids throughout the day.",
                    "Eat light, easily digestible meals (rice porridge, lentil soup, fruits).",
                    "Consult a doctor if symptoms do not improve within 48-72 hours."
                ),
                homeRemedies = listOf(
                    "Warm salt water gargles for throat irritation",
                    "Steam inhalation with plain water for mild nasal congestion",
                    "Adequate hydration and fresh fruits"
                ),
                redFlagWarnings = listOf(
                    "Sudden spike in high fever",
                    "Difficulty in breathing",
                    "Severe weakness or inability to stand"
                ),
                recommendedFacility = "Local ASHA Healthcare Worker or Nearest PHC",
                spokenSummary = "Your symptoms appear mild. Please rest well and stay hydrated. See a doctor if symptoms worsen.",
                isAiGenerated = false
            )
        }
    }
}
