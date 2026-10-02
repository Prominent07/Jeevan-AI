package com.example.data.model

data class FacilityItem(
    val id: String,
    val nameMarathi: String,
    val nameHindi: String,
    val nameEnglish: String,
    val type: FacilityType,
    val addressMarathi: String,
    val addressHindi: String,
    val addressEnglish: String,
    val phone: String,
    val distanceKm: Double,
    val is24x7: Boolean,
    val servicesMarathi: List<String>,
    val servicesHindi: List<String>,
    val servicesEnglish: List<String>,
    val latitude: Double,
    val longitude: Double
) {
    fun getName(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> nameMarathi
        AppLanguage.HINDI -> nameHindi
        AppLanguage.ENGLISH -> nameEnglish
    }

    fun getAddress(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> addressMarathi
        AppLanguage.HINDI -> addressHindi
        AppLanguage.ENGLISH -> addressEnglish
    }

    fun getServices(lang: AppLanguage): List<String> = when (lang) {
        AppLanguage.MARATHI -> servicesMarathi
        AppLanguage.HINDI -> servicesHindi
        AppLanguage.ENGLISH -> servicesEnglish
    }
}

enum class FacilityType(
    val labelMarathi: String,
    val labelHindi: String,
    val labelEnglish: String
) {
    ALL("सर्व", "सभी", "All"),
    PHC("प्राथमिक आरोग्य केंद्र (PHC)", "प्राथमिक स्वास्थ्य केंद्र (PHC)", "Primary Health Centre (PHC)"),
    CHC("ग्रामीण रुग्णालय (CHC)", "सामुदायिक स्वास्थ्य केंद्र (CHC)", "Community Health Centre (CHC)"),
    DISTRICT_HOSPITAL("जिल्हा रुग्णालय", "जिला अस्पताल", "District Hospital"),
    JAN_AUSHADHI("जन औषधी केंद्र", "जन औषधि केंद्र", "Jan Aushadhi (Generic Pharmacy)"),
    AMBULANCE("१०८ रुग्णवाहिका केंद्र", "108 एम्बुलेंस स्टेशन", "108 Ambulance Station")
}

val PreloadedHealthcareFacilities = listOf(
    FacilityItem(
        id = "phc_1",
        nameMarathi = "प्राथमिक आरोग्य केंद्र (PHC) वडगाव",
        nameHindi = "प्राथमिक स्वास्थ्य केंद्र (PHC) वडगांव",
        nameEnglish = "Primary Health Centre (PHC) Wadgaon",
        type = FacilityType.PHC,
        addressMarathi = "वडगाव ग्रामपंचायत जवळ, तालुका केंद्र",
        addressHindi = "वडगांव ग्राम पंचायत के पास, तहसील केंद्र",
        addressEnglish = "Near Gram Panchayat, Wadgaon Village",
        phone = "02026110800",
        distanceKm = 2.4,
        is24x7 = true,
        servicesMarathi = listOf("मोफत बाह्यरुग्ण (OPD)", "प्रसूती कक्ष (Maternity)", "लसीकरण", "मोफत आवश्यक औषधे"),
        servicesHindi = listOf("निःशुल्क ओपीडी (OPD)", "प्रसव कक्ष", "टीकाकरण", "मुफ्त दवाइयां"),
        servicesEnglish = listOf("Free OPD", "24/7 Delivery Ward", "Immunization", "Essential Medicines"),
        latitude = 18.5204,
        longitude = 73.8567
    ),
    FacilityItem(
        id = "chc_1",
        nameMarathi = "ग्रामीण रुग्णालय (CHC) शिरूर",
        nameHindi = "सामुदायिक स्वास्थ्य केंद्र (CHC) शिरूर",
        nameEnglish = "Community Health Centre (CHC) Shirur",
        type = FacilityType.CHC,
        addressMarathi = "मुख्य रस्ता, तहसील कचेरी समोर, शिरूर",
        addressHindi = "मुख्य मार्ग, तहसील कार्यालय के सामने, शिरूर",
        addressEnglish = "Main Road, Opposite Tehsil Office, Shirur",
        phone = "02138222108",
        distanceKm = 8.1,
        is24x7 = true,
        servicesMarathi = listOf("३० खाटांचे रुग्णालय", "अँटी-व्हेनम (साप विष प्रतिबंधक)", "एक्स-रे व लॅब", "ऑपरेशन थिएटर"),
        servicesHindi = listOf("30 बेड अस्पताल", "एंटी-वेनम (सर्पदंश टीका)", "एक्स-रे और लैब", "ऑपरेशन थिएटर"),
        servicesEnglish = listOf("30-Bed Hospital", "Anti-Snake Venom (ASV)", "X-Ray & Pathology Lab", "Emergency Minor OT"),
        latitude = 18.8267,
        longitude = 74.3789
    ),
    FacilityItem(
        id = "dist_1",
        nameMarathi = "उप-जिल्हा व सामान्य रुग्णालय",
        nameHindi = "उप-जिला एवं सामान्य अस्पताल",
        nameEnglish = "Sub-District & Civil Hospital",
        type = FacilityType.DISTRICT_HOSPITAL,
        addressMarathi = "सिव्हिल लाईन्स, मुख्य बस स्थानकाजवळ",
        addressHindi = "सिविल लाइंस, मुख्य बस स्टैंड के पास",
        addressEnglish = "Civil Lines, Near Central Bus Stand",
        phone = "108",
        distanceKm = 16.5,
        is24x7 = true,
        servicesMarathi = listOf("आयसीयू व व्हेंटिलेटर", "रक्तपेढी (Blood Bank)", "स्त्रीरोग व बालरोग तज्ज्ञ", "मोफत शस्त्रक्रिया"),
        servicesHindi = listOf("आईसीयू और वेंटिलेटर", "ब्लड बैंक", "स्त्री एवं बाल रोग विशेषज्ञ", "मुफ्त सर्जरी"),
        servicesEnglish = listOf("ICU & Trauma Care", "Blood Bank", "Pediatric & Gynecology Specialists", "Free Surgeries (PMJAY)"),
        latitude = 18.5304,
        longitude = 73.8467
    ),
    FacilityItem(
        id = "jan_1",
        nameMarathi = "प्रधानमंत्री भारतीय जनऔषधी केंद्र",
        nameHindi = "प्रधानमंत्री भारतीय जन औषधि केंद्र",
        nameEnglish = "PM Jan Aushadhi Generic Pharmacy",
        type = FacilityType.JAN_AUSHADHI,
        addressMarathi = "बाजारपेठ पेठ, डॉ. बाबासाहेब आंबेडकर चौक",
        addressHindi = "मुख्य बाजार, डॉ. आंबेडकर चौक",
        addressEnglish = "Market Yard Road, Ambedkar Chowk",
        phone = "18001808080",
        distanceKm = 3.2,
        is24x7 = false,
        servicesMarathi = listOf("५०% ते ९०% स्वस्त जेनेरिक औषधे", "बीपी व शुगरच्या गोळ्या", "सॅनिटरी पॅड (१ रु.)", "ग्लुकोमीटर व बीपी मशीन"),
        servicesHindi = listOf("50%-90% सस्ती जेनेरिक दवाइयां", "बीपी और शुगर की गोलियां", "सैनिटरी नैपकिन (1 रु.)", "बीपी व शुगर जांच मशीनें"),
        servicesEnglish = listOf("50-90% Discounted Generic Medicines", "Chronic BP & Diabetes Meds", "Sanitary Napkins (₹1)", "BP Monitors & Strips"),
        latitude = 18.5250,
        longitude = 73.8600
    ),
    FacilityItem(
        id = "amb_1",
        nameMarathi = "१०८ आपत्कालीन रुग्णवाहिका तळ",
        nameHindi = "108 आपातकालीन एम्बुलेंस बेस",
        nameEnglish = "108 Emergency Ambulance Hub",
        type = FacilityType.AMBULANCE,
        addressMarathi = "तालुका पोलीस स्टेशन व रुग्णालय परिसर",
        addressHindi = "तहसील पुलिस स्टेशन व अस्पताल परिसर",
        addressEnglish = "Tehsil Police Station & Hospital Base",
        phone = "108",
        distanceKm = 1.8,
        is24x7 = true,
        servicesMarathi = listOf("ऑक्सिजन युक्त वाहन", "प्रशिक्षित पॅरामेडिक", "मोफत तात्काळ मदत", "जीपीएस ट्रॅकिंग"),
        servicesHindi = listOf("ऑक्सीजन युक्त वाहन", "प्रशिक्षित पैरामेडिक", "निःशुल्क त्वरित सेवा", "जीपीएस ट्रैकिंग"),
        servicesEnglish = listOf("Oxygen-Equipped ALS Ambulance", "Trained Emergency Paramedic", "100% Free Toll-Free Service", "GPS Tracking"),
        latitude = 18.5180,
        longitude = 73.8520
    )
)
