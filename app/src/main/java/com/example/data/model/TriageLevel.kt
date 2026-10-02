package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.HealthGreenContainer
import com.example.ui.theme.HealthGreenOnContainer
import com.example.ui.theme.UrgentRed
import com.example.ui.theme.UrgentRedContainer
import com.example.ui.theme.UrgentRedOnContainer
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberContainer
import com.example.ui.theme.WarningAmberOnContainer

enum class TriageLevel(
    val color: Color,
    val containerColor: Color,
    val onContainerColor: Color
) {
    ROUTINE(
        color = HealthGreen,
        containerColor = HealthGreenContainer,
        onContainerColor = HealthGreenOnContainer
    ),
    DOCTOR_CONSULT(
        color = WarningAmber,
        containerColor = WarningAmberContainer,
        onContainerColor = WarningAmberOnContainer
    ),
    URGENT(
        color = UrgentRed,
        containerColor = UrgentRedContainer,
        onContainerColor = UrgentRedOnContainer
    );

    fun getLabel(lang: AppLanguage): String = when (this) {
        ROUTINE -> when (lang) {
            AppLanguage.MARATHI -> "नियमित काळजी / घरगुती उपचार"
            AppLanguage.HINDI -> "नियमित देखभाल / घरेलू उपचार"
            AppLanguage.ENGLISH -> "Routine Care / Self-Care"
        }
        DOCTOR_CONSULT -> when (lang) {
            AppLanguage.MARATHI -> "डॉक्टरांचा सल्ला आवश्यक (PHC/CHC)"
            AppLanguage.HINDI -> "डॉक्टर से परामर्श आवश्यक (PHC/CHC)"
            AppLanguage.ENGLISH -> "Doctor Consultation Needed"
        }
        URGENT -> when (lang) {
            AppLanguage.MARATHI -> "तातडीने वैद्यकीय मदत हवी (रुग्णालय/१०८)"
            AppLanguage.HINDI -> "तत्काल आपातकालीन चिकित्सा (अस्पताल/108)"
            AppLanguage.ENGLISH -> "Urgent Medical Attention Needed"
        }
    }

    fun getActionAdvice(lang: AppLanguage): String = when (this) {
        ROUTINE -> when (lang) {
            AppLanguage.MARATHI -> "विश्रांती घ्या, भरपूर पाणी/ओआरएस प्या. लक्षणे वाढल्यास डॉक्टरांकडे जा."
            AppLanguage.HINDI -> "आराम करें, भरपूर पानी/ओआरएस पिएं। लक्षण बिगड़ने पर डॉक्टर को दिखाएं।"
            AppLanguage.ENGLISH -> "Rest at home, drink plenty of water/ORS. Consult doctor if symptoms worsen."
        }
        DOCTOR_CONSULT -> when (lang) {
            AppLanguage.MARATHI -> "जवळच्या प्राथमिक आरोग्य केंद्र (PHC) किंवा डॉक्टरांना २४ ते ४८ तासांत दाखवा."
            AppLanguage.HINDI -> "निकटतम प्राथमिक स्वास्थ्य केंद्र (PHC) या डॉक्टर से 24-48 घंटों में मिलें।"
            AppLanguage.ENGLISH -> "Visit your nearest Primary Health Centre (PHC) or doctor within 24-48 hours."
        }
        URGENT -> when (lang) {
            AppLanguage.MARATHI -> "वेळ वाया घालवू नका! ताबडतोब १०८ रुग्णवाहिका बोलवा किंवा जिल्हा रुग्णालयात जा."
            AppLanguage.HINDI -> "समय न गंवाएं! तुरंत 108 एम्बुलेंस को कॉल करें या निकटतम अस्पताल जाएं।"
            AppLanguage.ENGLISH -> "Do not delay! Immediately call 108 ambulance or rush to nearest emergency hospital."
        }
    }
}
