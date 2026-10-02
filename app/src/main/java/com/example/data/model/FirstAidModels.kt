package com.example.data.model

data class FirstAidItem(
    val id: String,
    val titleMarathi: String,
    val titleHindi: String,
    val titleEnglish: String,
    val iconCategory: String,
    val urgencyLevel: TriageLevel,
    val stepsMarathi: List<String>,
    val stepsHindi: List<String>,
    val stepsEnglish: List<String>,
    val dontsMarathi: List<String>,
    val dontsHindi: List<String>,
    val dontsEnglish: List<String>,
    val emergencyContact: String = "108"
) {
    fun getTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.MARATHI -> titleMarathi
        AppLanguage.HINDI -> titleHindi
        AppLanguage.ENGLISH -> titleEnglish
    }

    fun getSteps(lang: AppLanguage): List<String> = when (lang) {
        AppLanguage.MARATHI -> stepsMarathi
        AppLanguage.HINDI -> stepsHindi
        AppLanguage.ENGLISH -> stepsEnglish
    }

    fun getDonts(lang: AppLanguage): List<String> = when (lang) {
        AppLanguage.MARATHI -> dontsMarathi
        AppLanguage.HINDI -> dontsHindi
        AppLanguage.ENGLISH -> dontsEnglish
    }
}

val PreloadedFirstAidGuides = listOf(
    FirstAidItem(
        id = "snake_bite",
        titleMarathi = "सर्पदंश (साप चावणे)",
        titleHindi = "सर्पदंश (सांप का काटना)",
        titleEnglish = "Snake Bite Emergency",
        iconCategory = "warning",
        urgencyLevel = TriageLevel.URGENT,
        stepsMarathi = listOf(
            "रुग्णाला शांत ठेवा, हालचाल करू देऊ नका (हालचालीमुळे विष वेगाने पसरते).",
            "चावलेला भाग (हात/पाय) हृदयाच्या पातळीखाली स्थिर ठेवा आणि लाकडी पट्टीने बांधून ठेवा.",
            "चावलेल्या जागेवरील अंगठी, घड्याळ, घट्ट कपडे त्वरित सैल करा किंवा काढा.",
            "त्वरित १०८ रुग्णवाहिकेला फोन करा आणि रुग्णाला जवळच्या ग्रामीण/जिल्हा रुग्णालयात न्या जेथे अँटी-व्हेनम उपलब्ध असते."
        ),
        stepsHindi = listOf(
            "मरीज को शांत रखें और बिल्कुल हिलने न दें (हलचल से जहर तेजी से फैलता है)।",
            "काटे गए अंग को दिल के स्तर से नीचे स्थिर रखें।",
            "काटे गए स्थान से अंगूठी, घड़ी, कड़े और तंग कपड़े तुरंत उतारें।",
            "तुरंत 108 पर कॉल करें और मरीज को उस नजदीकी अस्पताल ले जाएं जहां एंटी-वेनम उपलब्ध हो।"
        ),
        stepsEnglish = listOf(
            "Keep the patient calm and completely still (movement spreads venom faster).",
            "Immobilize the bitten limb below heart level using a splint or cloth.",
            "Remove tight rings, watches, and restrictive clothing immediately.",
            "Rush immediately to the nearest Community Health Centre (CHC) or District Hospital with Anti-Snake Venom (ASV). Call 108."
        ),
        dontsMarathi = listOf(
            "चावलेल्या जागेवर ब्लेडने कापू नका किंवा विष तोंडाने चोखू नका!",
            "दोरी किंवा रबरने रक्ताभिसरण पूर्ण बंद होईल अशी घट्ट गाठ (Tourniquet) मारू नका.",
            "कोणतीही वनस्पती, शेण किंवा घरगुती लेप जखमेवर लावू नका."
        ),
        dontsHindi = listOf(
            "घाव पर चीरा न लगाएं और मुंह से जहर चूसने की कोशिश न करें!",
            "बहुत कसकर रस्सी या कपड़ा (Tourniquet) न बांधें।",
            "घाव पर कोई जड़ी-बूटी, गोबर या मिट्टी न लगाएं।"
        ),
        dontsEnglish = listOf(
            "DO NOT cut the bite area or attempt to suck out the venom!",
            "DO NOT apply a tight tourniquet that cuts off arterial blood flow.",
            "DO NOT apply ice, herbs, cow dung, or traditional concoctions."
        )
    ),
    FirstAidItem(
        id = "heat_stroke",
        titleMarathi = "उष्माघात व डिहायड्रेशन (पाणी कमी होणे)",
        titleHindi = "लू लगना और निर्जलीकरण (Dehydration)",
        titleEnglish = "Heat Stroke & Severe Dehydration",
        iconCategory = "sun",
        urgencyLevel = TriageLevel.DOCTOR_CONSULT,
        stepsMarathi = listOf(
            "व्यक्तीला ताबडतोब सावलीत किंवा थंड, हवेशीर जागी आणा.",
            "घट्ट कपडे सैल करा. कपाळावर, मानेवर व काखेत थंड पाण्याच्या पट्ट्या ठेवा.",
            "घरगुती ओआरएस (ORS) तयार करा: १ लिटर स्वच्छ पाण्यात ६ चमचे साखर + १/२ चमचा मीठ मिसळून थोडे थोडे पाजा.",
            "नारळ पाणी, ताक किंवा लिंबू पाणी प्यायला द्या.",
            "तापमान १०३°F च्या वर असल्यास व बेशुद्ध पडल्यास त्वरित डॉक्टरांकडे न्या."
        ),
        stepsHindi = listOf(
            "मरीज को तुरंत छायादार और ठंडी, हवादार जगह पर लिटाएं।",
            "तंग कपड़े ढीले करें और सिर, गर्दन और माथे पर गीली ठंडी पट्टियां रखें।",
            "घरेलू ओआरएस (ORS) बनाएं: 1 लीटर साफ पानी में 6 चम्मच चीनी + आधा चम्मच नमक मिलाकर धीरे-धीरे पिलाएं।",
            "नारियल पानी, छाछ या नींबू पानी पिलाएं।",
            "बेहोशी या तेज बुखार होने पर तुरंत डॉक्टर के पास ले जाएं।"
        ),
        stepsEnglish = listOf(
            "Move the person to a cool, shaded, well-ventilated area immediately.",
            "Loosen tight clothes and apply cool wet cloths on forehead, neck, and armpits.",
            "Prepare Homemade ORS: In 1 liter clean drinking water, mix 6 level teaspoons sugar + 1/2 teaspoon salt. Give small sips.",
            "Offer fresh coconut water, diluted buttermilk, or lemon water.",
            "If body temperature is over 103°F or patient is confused, seek emergency medical care."
        ),
        dontsMarathi = listOf(
            "व्यक्ती बेशुद्ध असल्यास तोंडात बळजबरीने पाणी किंवा औषध घालू नका.",
            "अत्यंत थंड बर्फाच्या पाण्यात व्यक्तीला बुडवू नका."
        ),
        dontsHindi = listOf(
            "बेहोश मरीज के मुंह में जबरन पानी या दवा न डालें।",
            "बहुत ज्यादा बर्फीले पानी से न नहलाएं।"
        ),
        dontsEnglish = listOf(
            "DO NOT force liquids into an unconscious or vomiting person.",
            "DO NOT use extreme ice baths which can cause shivering."
        )
    ),
    FirstAidItem(
        id = "bleeding_wounds",
        titleMarathi = "जखम व तीव्र रक्तस्त्राव",
        titleHindi = "गंभीर घाव और रक्तस्राव",
        titleEnglish = "Severe Bleeding & Wounds",
        iconCategory = "blood",
        urgencyLevel = TriageLevel.DOCTOR_CONSULT,
        stepsMarathi = listOf(
            "स्वच्छ कापड किंवा पट्टीने जखमेवर थेट दाब (Direct Pressure) द्या.",
            "दाब कमीत कमी ५ ते १० मिनिटे सतत चालू ठेवा, सारखे उघडून पाहू नका.",
            "शक्य असल्यास जखम झालेला अवयव हृदयाच्या पातळीपेक्षा वर उचला.",
            "रक्तस्त्राव थांबल्यावर स्वच्छ पाण्याने धुवा आणि निर्जंतुक पट्टी बांधा.",
            "गेल्या ५ वर्षात धनुर्वात (Tetanus - TT) चे इंजेक्शन नसेल तर २४ तासांत टोचून घ्या."
        ),
        stepsHindi = listOf(
            "साफ कपड़े या पट्टी से घाव पर सीधा दबाव (Direct Pressure) बनाएं।",
            "दबाव को 5-10 मिनट तक लगातार बनाए रखें, बार-बार पट्टी हटाकर न देखें।",
            "यदि संभव हो तो चोट वाले अंग को हृदय से ऊपर उठाएं।",
            "खून रुकने पर साफ पानी से धोएं और स्वच्छ पट्टी बांधें।",
            "टिटनेस (Tetanus - TT) का टीका अवश्य लगवाएं यदि 5 साल से नहीं लगा है।"
        ),
        stepsEnglish = listOf(
            "Apply firm, direct pressure on the wound with a clean cloth or sterile gauze.",
            "Maintain continuous pressure for at least 5-10 minutes without lifting.",
            "Elevate the wounded limb above heart level if no fracture is suspected.",
            "Once bleeding is controlled, gently rinse with clean water and apply clean dressing.",
            "Receive a Tetanus Toxoid (TT) booster injection at the local PHC within 24 hours."
        ),
        dontsMarathi = listOf(
            "जखमेमध्ये खोल रुतलेली लोखंडी वस्तू किंवा काच स्वतः उपसण्याचा प्रयत्न करू नका.",
            "जखमेवर हळद, मिरची, माती किंवा तंबाखू लावू नका!"
        ),
        dontsHindi = listOf(
            "घाव में धंसी हुई कोई वस्तु (लोहा/कांच) खुद निकालने की कोशिश न करें।",
            "घाव पर हल्दी, मिर्च, गोबर या मिट्टी बिल्कुल न लगाएं।"
        ),
        dontsEnglish = listOf(
            "DO NOT remove deeply embedded objects (glass/nails) yourself.",
            "DO NOT apply cow dung, tobacco, or unsterilized powders."
        )
    ),
    FirstAidItem(
        id = "burns",
        titleMarathi = "भाजणे (आग किंवा गरम पाणी/तेल)",
        titleHindi = "जलना (आग या गर्म पानी/तेल)",
        titleEnglish = "Burns & Scalds",
        iconCategory = "fire",
        urgencyLevel = TriageLevel.DOCTOR_CONSULT,
        stepsMarathi = listOf(
            "भाजलेल्या भागावर त्वरित १० ते १५ मिनिटे नळाचे साधे थंड पाणी ओता.",
            "पाणी ओतल्याने उष्णता त्वचेच्या खोलवर जात नाही आणि वेदना कमी होतात.",
            "स्वच्छ, सुक्या आणि निर्जंतुक कापडाने जखम हलकेच झाका.",
            "मोठे फोड आले असल्यास किंवा चेहरा, हात, गुप्तांग भाजल्यास त्वरित रुग्णालयात जा."
        ),
        stepsHindi = listOf(
            "जले हुए स्थान पर तुरंत 10 से 15 मिनट तक नल का सामान्य ठंडा पानी डालें।",
            "पानी डालने से जलन अंदर नहीं फैलती और दर्द कम होता है।",
            "साफ और सूखे सूती कपड़े से हल्के से ढकें।",
            "बड़े छाले होने पर या चेहरा/हाथ जलने पर तुरंत अस्पताल जाएं।"
        ),
        stepsEnglish = listOf(
            "Immediately cool the burn with gentle, cool running tap water for 10-15 minutes.",
            "This cools the tissue and stops thermal damage from progressing deeper.",
            "Cover loosely with a clean, sterile cloth or plastic wrap.",
            "If burn is larger than palm size or involves face/joints, visit hospital immediately."
        ),
        dontsMarathi = listOf(
            "बर्फ, टूथपेस्ट, तेल किंवा शाई लावू नका!",
            "आलेले फोड (Blisters) सुईने फोडू नका, यामुळे जंतुसंसर्ग होतो."
        ),
        dontsHindi = listOf(
            "बर्फ, टूथपेस्ट, तेल या स्याही न लगाएं!",
            "पड़े हुए छालों को न फोड़ें, इससे संक्रमण का खतरा होता है।"
        ),
        dontsEnglish = listOf(
            "DO NOT apply ice, toothpaste, butter, or ink.",
            "DO NOT burst or pop blisters as it causes severe infection."
        )
    ),
    FirstAidItem(
        id = "choking_cpr",
        titleMarathi = "घशात अडकणे (Choking) व सीपीआर",
        titleHindi = "गले में कुछ अटकना (Choking) और सीपीआर",
        titleEnglish = "Choking & Basic CPR",
        iconCategory = "heart",
        urgencyLevel = TriageLevel.URGENT,
        stepsMarathi = listOf(
            "व्यक्ती खोकू शकत असेल तर जोरात खोकण्यास सांगा.",
            "व्यक्ती बोलू वा श्वास घेऊ शकत नसेल तर पाठीवर दोन्ही खांद्यांच्या मध्ये हाताच्या पंज्याने ५ वेळा जोरात थाप द्या (Back Blows).",
            "नाभीच्या वर मूठ ठेवून पोटावर मागून छातीकडे जोराने ५ वेळा दाबा (Heimlich Maneuver).",
            "व्यक्ती बेशुद्ध झाल्यास जमिनीवर झोपवून छातीच्या मध्यभागी दोन्ही हातांनी जोराने आणि वेगाने (प्रति मिनिट १०० ते १२० वेळा) पंप करा."
        ),
        stepsHindi = listOf(
            "यदि व्यक्ति खांस सकता है, तो उसे जोर से खांसने के लिए कहें।",
            "यदि सांस रुक गई है, तो मरीज को आगे झुकाकर पीठ के बीच में 5 बार हथेली से तेज थपकी दें।",
            "नाभि के ऊपर मुट्ठी रखकर पेट को पीछे और ऊपर की तरफ 5 बार दबाएं (Heimlich Maneuver)।",
            "यदि बेहोश हो जाए, तो तुरंत छाती के बीच दोनों हाथों से तेज गति से सीपीआर (CPR) पंपिंग शुरू करें।"
        ),
        stepsEnglish = listOf(
            "Encourage coughing if the person can still make sounds.",
            "If unable to breathe: Give 5 firm back blows between shoulder blades with palm.",
            "Perform abdominal thrusts (Heimlich Maneuver): Stand behind, fist above navel, pull inward and upward.",
            "If unconscious and not breathing: Start chest compressions hard and fast at center of chest (100-120 per min). Call 108 immediately."
        ),
        dontsMarathi = listOf(
            "अडकलेली वस्तू दिसत नसेल तर अंधारात बोट तोंडात घालून तपासू नका (वस्तू अधिक आत जाऊ शकते).",
            "बेशुद्ध व्यक्तीला पाणी पाजण्याचा प्रयत्न करू नका."
        ),
        dontsHindi = listOf(
            "अंधी उंगली मुंह में न डालें, इससे वस्तु और अंदर धंस सकती है।",
            "बेहोश व्यक्ति को पानी न पिलाएं।"
        ),
        dontsEnglish = listOf(
            "DO NOT perform blind finger sweeps in the mouth.",
            "DO NOT delay calling 108 emergency service."
        )
    ),
    FirstAidItem(
        id = "maternal_warning",
        titleMarathi = "गरोदर माता धोक्याची लक्षणे",
        titleHindi = "गर्भवती महिला खतरे के लक्षण",
        titleEnglish = "Maternal & Pregnancy Danger Signs",
        iconCategory = "mother",
        urgencyLevel = TriageLevel.URGENT,
        stepsMarathi = listOf(
            "खालीलपैकी एकही लक्षण आढळल्यास त्वरित १०२ किंवा १०८ ला फोन करा:",
            "१. योनीतून रक्तस्त्राव (Bleeding) होणे.",
            "२. तीव्र डोकेदुखी, डोळ्यांसमोर अंधारी येणे किंवा चक्कर.",
            "३. हात, पाय आणि चेहऱ्यावर अचानक सूज येणे.",
            "४. पोटावर तीव्र आणि सतत कळा येणे किंवा बाळाची हालचाल बंद होणे.",
            "५. तीव्र ताप किंवा झटके (Fits/Convulsions) येणे."
        ),
        stepsHindi = listOf(
            "इनमें से कोई भी लक्षण दिखने पर तुरंत 102 या 108 पर संपर्क करें:",
            "1. योनि से रक्तस्राव (Bleeding) होना।",
            "2. तेज सिरदर्द, आंखों के आगे अंधेरा छाना या चक्कर आना।",
            "3. हाथ, पैर और चेहरे पर अचानक सूजन आना।",
            "4. पेट में असहनीय दर्द या शिशु की हलचल बंद होना।",
            "5. तेज बुखार या दौरे/झटके (Convulsions) आना।"
        ),
        stepsEnglish = listOf(
            "Call 102 (Janani Shishu Suraksha) or 108 immediately if any of these occur:",
            "1. Vaginal bleeding or fluid leakage at any stage of pregnancy.",
            "2. Severe continuous headache, blurred vision, or severe dizziness (High BP).",
            "3. Sudden swelling of face, hands, and feet.",
            "4. Severe abdominal cramps or sudden decrease/stopping of baby kicks.",
            "5. High fever or convulsions/seizures."
        ),
        dontsMarathi = listOf(
            "घरीच प्रसूती करण्याचा किंवा दुखणे अंगावर काढण्याचा धोका पत्करू नका.",
            "डॉक्टरांच्या सल्ल्याशिवाय कोणतीही गोळी किंवा काढा पिऊ नका."
        ),
        dontsHindi = listOf(
            "घर पर डिलीवरी कराने का जोखिम कभी न लें।",
            "बिना डॉक्टर की सलाह के कोई देसी काढ़ा या गोली न लें।"
        ),
        dontsEnglish = listOf(
            "DO NOT attempt home deliveries in emergency situations.",
            "DO NOT give unprescribed herbal medicines."
        )
    )
)
