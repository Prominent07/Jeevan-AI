package com.example.ui.localization

import com.example.data.model.AppLanguage

object AppStrings {

    fun appName(lang: AppLanguage): String = "JeevanAI"

    fun appTagline(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "ग्रामीण व दुर्गम भागासाठी स्मार्ट आरोग्य मित्र"
        AppLanguage.HINDI -> "ग्रामीण व दूरदराज क्षेत्रों के लिए स्मार्ट स्वास्थ्य साथी"
        AppLanguage.ENGLISH -> "Smart AI Health Guide for Rural & Tier 3/4 Communities"
    }

    fun medicalDisclaimer(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "⚠️ महत्त्वाची सूचना: जीवनAI केवळ प्राथमिक मार्गदर्शन आणि वर्गीकरण (Triage) करते. हे कोणत्याही आजाराचे अंतिम निदान करत नाही आणि डॉक्टरांचा पर्याय नाही. गंभीर स्थितीत लगेच रुग्णालयात जा."
        AppLanguage.HINDI -> "⚠️ महत्वपूर्ण सूचना: जीवनAI केवल प्राथमिक मार्गदर्शन और ट्राइएज (Triage) प्रदान करता है। यह किसी बीमारी का निदान नहीं करता और डॉक्टर का विकल्प नहीं है। गंभीर स्थिति में तुरंत अस्पताल जाएं।"
        AppLanguage.ENGLISH -> "⚠️ Medical Disclaimer: JeevanAI provides triage and basic health guidance only. It does NOT diagnose diseases or replace a medical doctor. For emergencies, visit a hospital immediately."
    }

    // Tabs / Navigation
    fun tabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "मुख्य पान"
        AppLanguage.HINDI -> "होम"
        AppLanguage.ENGLISH -> "Home"
    }

    fun tabTriage(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "AI आरोग्य तपासणी"
        AppLanguage.HINDI -> "AI स्वास्थ्य जांच"
        AppLanguage.ENGLISH -> "AI Triage"
    }

    fun tabFirstAid(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "प्रथमोपचार"
        AppLanguage.HINDI -> "प्राथमिक उपचार"
        AppLanguage.ENGLISH -> "First Aid"
    }

    fun tabFacilities(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "जवळचे रुग्णालय"
        AppLanguage.HINDI -> "निकटतम अस्पताल"
        AppLanguage.ENGLISH -> "Clinics & PHC"
    }

    fun tabRecords(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "आरोग्य नोंदी"
        AppLanguage.HINDI -> "स्वास्थ्य रिकॉर्ड"
        AppLanguage.ENGLISH -> "Health Records"
    }

    fun tabMedicines(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "औषधांची वेळ"
        AppLanguage.HINDI -> "दवाइयों की याद"
        AppLanguage.ENGLISH -> "Medicines"
    }

    // Emergency SOS
    fun sosButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "तातडीची मदत (SOS)"
        AppLanguage.HINDI -> "आपातकालीन सहायता (SOS)"
        AppLanguage.ENGLISH -> "Emergency SOS"
    }

    fun callAmbulance108(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "१०८ मोफत रुग्णवाहिका"
        AppLanguage.HINDI -> "108 निःशुल्क एम्बुलेंस"
        AppLanguage.ENGLISH -> "108 Free Ambulance"
    }

    fun callMaternity102(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "१०२ गरोदर माता व बालक वाहन"
        AppLanguage.HINDI -> "102 गर्भवती महिला व शिशु वाहन"
        AppLanguage.ENGLISH -> "102 Maternity Transport"
    }

    fun callHealthHelpline104(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "१०४ आरोग्य सल्ला हेल्पलाइन"
        AppLanguage.HINDI -> "104 स्वास्थ्य परामर्श हेल्पलाइन"
        AppLanguage.ENGLISH -> "104 Medical Helpline"
    }

    fun callPoliceEmergency112(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "११२ राष्ट्रीय आपत्कालीन सेवा"
        AppLanguage.HINDI -> "112 राष्ट्रीय आपातकालीन सेवा"
        AppLanguage.ENGLISH -> "112 All Emergencies"
    }

    // Home Screen
    fun welcomeHeading(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "नमस्कार! आज तुम्हाला कसे वाटत आहे?"
        AppLanguage.HINDI -> "नमस्ते! आज आप कैसा महसूस कर रहे हैं?"
        AppLanguage.ENGLISH -> "Hello! How are you feeling today?"
    }

    fun startVoiceTriage(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "बोलून सांगा (Voice Assistant)"
        AppLanguage.HINDI -> "बोलकर बताएं (Voice Assistant)"
        AppLanguage.ENGLISH -> "Speak Symptoms (Voice)"
    }

    fun startTextTriage(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "लक्षणे निवडून तपासा"
        AppLanguage.HINDI -> "लक्षण चुनकर जांचें"
        AppLanguage.ENGLISH -> "Check by Symptoms"
    }

    fun listenAudio(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "ऐका"
        AppLanguage.HINDI -> "सुनें"
        AppLanguage.ENGLISH -> "Listen"
    }

    fun stopAudio(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "थांबवा"
        AppLanguage.HINDI -> "रोकें"
        AppLanguage.ENGLISH -> "Stop"
    }

    // Triage Steps
    fun step1Title(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "पायरी १: मुख्य लक्षण काय आहे?"
        AppLanguage.HINDI -> "कदम 1: मुख्य लक्षण क्या है?"
        AppLanguage.ENGLISH -> "Step 1: What is the main symptom?"
    }

    fun step2Title(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "पायरी २: त्रास किती दिवसांपासून आहे?"
        AppLanguage.HINDI -> "कदम 2: समस्या कितने दिनों से है?"
        AppLanguage.ENGLISH -> "Step 2: How long have you felt this?"
    }

    fun step3Title(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "पायरी ३: कोणताही गंभीर धोका आहे का?"
        AppLanguage.HINDI -> "कदम 3: क्या कोई गंभीर खतरा है?"
        AppLanguage.ENGLISH -> "Step 3: Any severe red flags?"
    }

    fun step4Title(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "AI आरोग्य सल्ला व निर्णय"
        AppLanguage.HINDI -> "AI स्वास्थ्य सलाह व निर्णय"
        AppLanguage.ENGLISH -> "AI Health Triage Result"
    }

    fun describeSymptomsPlaceholder(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "किंवा येथे स्वतःचे त्रास लिहून सांगा (उदा. कालपासून ताप व डोकेदुखी आहे)..."
        AppLanguage.HINDI -> "या यहां अपनी परेशानी लिखकर बताएं (जैसे कल से बुखार और सिरदर्द है)..."
        AppLanguage.ENGLISH -> "Or type your symptoms here (e.g. fever and severe headache since yesterday)..."
    }

    fun tapToSpeak(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "माईक दाबा आणि बोला"
        AppLanguage.HINDI -> "माइक दबाएं और बोलें"
        AppLanguage.ENGLISH -> "Tap Mic to Speak"
    }

    fun listening(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "ऐकत आहे... बोला"
        AppLanguage.HINDI -> "सुन रहे हैं... बोलिए"
        AppLanguage.ENGLISH -> "Listening... Please speak"
    }

    fun analyzingWithAI(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "AI लक्षणे तपासत आहे... कृपया थांबा"
        AppLanguage.HINDI -> "AI लक्षणों का विश्लेषण कर रहा है... कृपया प्रतीक्षा करें"
        AppLanguage.ENGLISH -> "AI is analyzing symptoms... Please wait"
    }

    fun nextButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "पुढे जा"
        AppLanguage.HINDI -> "आगे बढ़ें"
        AppLanguage.ENGLISH -> "Next"
    }

    fun backButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "मागे जा"
        AppLanguage.HINDI -> "पीछे जाएं"
        AppLanguage.ENGLISH -> "Back"
    }

    fun restartTriage(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "नवीन तपासणी करा"
        AppLanguage.HINDI -> "नई जांच शुरू करें"
        AppLanguage.ENGLISH -> "Start New Triage"
    }

    fun saveToHealthRecord(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "आरोग्य नोंदवहीत जतन करा"
        AppLanguage.HINDI -> "स्वास्थ्य रिकॉर्ड में सहेजें"
        AppLanguage.ENGLISH -> "Save to Health Records"
    }

    fun savedSuccessfully(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "नोंद यशस्वीरित्या साठवली!"
        AppLanguage.HINDI -> "रिकॉर्ड सफलतापूर्वक सहेजा गया!"
        AppLanguage.ENGLISH -> "Saved to records successfully!"
    }

    // Medicine Reminders
    fun todayMedicines(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "आजची औषधे"
        AppLanguage.HINDI -> "आज की दवाइयां"
        AppLanguage.ENGLISH -> "Today's Medicines"
    }

    fun addMedicine(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "+ नवीन औषध जोडा"
        AppLanguage.HINDI -> "+ नई दवा जोड़ें"
        AppLanguage.ENGLISH -> "+ Add Medicine"
    }

    fun taken(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "घेतले ✓"
        AppLanguage.HINDI -> "ले ली ✓"
        AppLanguage.ENGLISH -> "Taken ✓"
    }

    fun pending(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "बाकी आहे"
        AppLanguage.HINDI -> "बाकी है"
        AppLanguage.ENGLISH -> "Pending"
    }

    fun morning(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "सकाळी"
        AppLanguage.HINDI -> "सुबह"
        AppLanguage.ENGLISH -> "Morning"
    }

    fun afternoon(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "दुपारी"
        AppLanguage.HINDI -> "दोपहर"
        AppLanguage.ENGLISH -> "Afternoon"
    }

    fun night(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "रात्री"
        AppLanguage.HINDI -> "रात"
        AppLanguage.ENGLISH -> "Night"
    }

    fun beforeFood(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "जेवणापूर्वी"
        AppLanguage.HINDI -> "खाने से पहले"
        AppLanguage.ENGLISH -> "Before Food"
    }

    fun afterFood(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "जेवणानंतर"
        AppLanguage.HINDI -> "खाने के बाद"
        AppLanguage.ENGLISH -> "After Food"
    }

    // Facilities
    fun callFacility(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "फोन करा"
        AppLanguage.HINDI -> "कॉल करें"
        AppLanguage.ENGLISH -> "Call"
    }

    fun getDirections(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "दिशा दाखवा (नकाशा)"
        AppLanguage.HINDI -> "रास्ता देखें (नक्शा)"
        AppLanguage.ENGLISH -> "Directions"
    }

    fun open24Hours(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "२४ तास आपत्कालीन सेवा उपलब्ध"
        AppLanguage.HINDI -> "24 घंटे आपातकालीन सेवा उपलब्ध"
        AppLanguage.ENGLISH -> "24/7 Emergency Care Available"
    }

    fun genericMedicineStore(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "स्वस्त जेनेरिक औषध केंद्र (जन औषधी)"
        AppLanguage.HINDI -> "सस्ती जेनेरिक दवा केंद्र (जन औषधि)"
        AppLanguage.ENGLISH -> "Jan Aushadhi Generic Medicine"
    }

    // Health Records
    fun familyMembers(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "कुटुंबातील सदस्य"
        AppLanguage.HINDI -> "परिवार के सदस्य"
        AppLanguage.ENGLISH -> "Family Members"
    }

    fun addFamilyMember(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "+ नवीन सदस्य जोडा"
        AppLanguage.HINDI -> "+ नया सदस्य जोड़ें"
        AppLanguage.ENGLISH -> "+ Add Family Member"
    }

    fun pastConsultations(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "मागील तपासणी नोंदी"
        AppLanguage.HINDI -> "पिछली जांच का इतिहास"
        AppLanguage.ENGLISH -> "Past Consultations"
    }

    fun abhaCardNumber(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "आयुष्मान भारत / आभा कार्ड क्र."
        AppLanguage.HINDI -> "आयुष्मान भारत / आभा कार्ड नं."
        AppLanguage.ENGLISH -> "ABHA / Ayushman Card No."
    }

    fun chronicConditions(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "जुने आजार (उदा. बीपी, साखर, दमा)"
        AppLanguage.HINDI -> "पुरानी बीमारियां (जैसे बीपी, शुगर, दमा)"
        AppLanguage.ENGLISH -> "Chronic Conditions (BP, Diabetes, etc.)"
    }

    fun emergencyContactPhone(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "आपत्कालीन संपर्क फोन नंबर"
        AppLanguage.HINDI -> "आपातकालीन संपर्क फोन नंबर"
        AppLanguage.ENGLISH -> "Emergency Contact Phone"
    }

    fun sendEmergencySms(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> "कुटुंबाला SOS संदेश पाठवा"
        AppLanguage.HINDI -> "परिवार को SOS संदेश भेजें"
        AppLanguage.ENGLISH -> "Send SOS SMS to Family"
    }
}
