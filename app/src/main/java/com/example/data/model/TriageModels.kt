package com.example.data.model

data class TriageResult(
    val level: TriageLevel,
    val title: String,
    val summary: String,
    val actionSteps: List<String>,
    val homeRemedies: List<String>,
    val redFlagWarnings: List<String>,
    val recommendedFacility: String,
    val spokenSummary: String,
    val isAiGenerated: Boolean = true
)

data class SymptomPreset(
    val id: String,
    val iconName: String,
    val labelMarathi: String,
    val labelHindi: String,
    val labelEnglish: String,
    val isRedFlag: Boolean = false
) {
    fun getLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> labelMarathi
        AppLanguage.HINDI -> labelHindi
        AppLanguage.ENGLISH -> labelEnglish
    }
}

val DefaultSymptomPresets = listOf(
    SymptomPreset("fever", "fever", "ताप (Fever)", "बुखार (Fever)", "Fever / High Body Temp"),
    SymptomPreset("cough", "cough", "खोकला व कफ", "खांसी और बलगम", "Cough & Cold"),
    SymptomPreset("chest_pain", "chest", "छातीत तीव्र वेदना", "छाती में तेज दर्द", "Chest Pain / Pressure", isRedFlag = true),
    SymptomPreset("breathing", "breathing", "श्वास घेण्यास त्रास", "सांस लेने में तकलीफ", "Difficulty Breathing", isRedFlag = true),
    SymptomPreset("stomach", "stomach", "पोटदुखी / अतिसार", "पेट दर्द / दस्त", "Stomach Ache / Diarrhea"),
    SymptomPreset("vomiting", "vomiting", "वारंवार उलटी होणे", "लगातार उल्टी होना", "Severe Vomiting"),
    SymptomPreset("headache", "headache", "तीव्र डोकेदुखी", "तेज सिरदर्द", "Severe Headache"),
    SymptomPreset("injury", "injury", "जखम व रक्तस्त्राव", "घाव और रक्तस्राव", "Wound & Bleeding"),
    SymptomPreset("dizziness", "dizziness", "चक्कर / बेशुद्धावस्था", "चक्कर / बेहोशी", "Dizziness / Fainting", isRedFlag = true),
    SymptomPreset("snake_bite", "bite", "साप / कीटक चावणे", "सांप / कीड़ा काटना", "Snake or Insect Bite", isRedFlag = true),
    SymptomPreset("maternal", "pregnant", "गरोदरपणात धोकादायक लक्षणे", "गर्भावस्था में खतरे के लक्षण", "Pregnancy Complication", isRedFlag = true),
    SymptomPreset("joint_pain", "joint", "सांधेदुखी / अंगदुखी", "जोड़ों का दर्द / बदन दर्द", "Joint & Body Pain")
)

data class DurationOption(
    val id: String,
    val marathi: String,
    val hindi: String,
    val english: String
) {
    fun getLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> marathi
        AppLanguage.HINDI -> hindi
        AppLanguage.ENGLISH -> english
    }
}

val DefaultDurationOptions = listOf(
    DurationOption("today", "आजपासून सुरू (< २४ तास)", "आज से शुरू (< 24 घंटे)", "Started today (< 24 hrs)"),
    DurationOption("few_days", "२ ते ३ दिवस", "2 से 3 दिन", "2 to 3 days"),
    DurationOption("one_week", "१ आठवडा किंवा जास्त", "1 सप्ताह या अधिक", "1 week or more"),
    DurationOption("chronic", "१ महिन्यापेक्षा जास्त", "1 महीने से अधिक", "More than a month")
)

data class RedFlagQuestion(
    val id: String,
    val marathi: String,
    val hindi: String,
    val english: String
) {
    fun getLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> marathi
        AppLanguage.HINDI -> hindi
        AppLanguage.ENGLISH -> english
    }
}

val DefaultRedFlagQuestions = listOf(
    RedFlagQuestion("extreme_breath", "श्वास घेताना दम लागत आहे का?", "सांस फूल रही है या घबराहट हो रही है?", "Extreme shortness of breath or blue lips?"),
    RedFlagQuestion("chest_pressure", "छातीत जडपणा किंवा घाम येत आहे का?", "छाती में भारीपन या पसीना आ रहा है?", "Heavy chest tightness spreading to left arm?"),
    RedFlagQuestion("unconscious", "व्यक्ती बेशुद्ध किंवा अडखळत बोलत आहे का?", "मरीज बेहोश या बहकी बातें कर रहा है?", "Unconsciousness, confusion, or unable to wake up?"),
    RedFlagQuestion("high_fever_convulsion", "खूप तीव्र ताप (१०३°F+) किंवा झटके येत आहेत का?", "बहुत तेज बुखार (103°F+) या झटके आ रहे हैं?", "Fever above 103°F or pediatric convulsions?"),
    RedFlagQuestion("uncontrolled_bleed", "थांबत नसलेला रक्तस्त्राव किंवा रक्ताची उलटी?", "रक्तस्राव जो रुक नहीं रहा या खून की उल्टी?", "Uncontrolled bleeding or blood in vomit/cough?")
)
