package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppHealthDatabase
import com.example.data.local.entities.CustomFacilityEntity
import com.example.data.local.entities.FamilyMemberEntity
import com.example.data.local.entities.MedicineReminderEntity
import com.example.data.local.entities.TriageLogEntity
import com.example.data.model.AppLanguage
import com.example.data.model.FacilityItem
import com.example.data.model.FacilityType
import com.example.data.model.PreloadedHealthcareFacilities
import com.example.data.model.TriageResult
import com.example.data.repository.HealthRepository
import com.example.ui.localization.AppStrings
import com.example.ui.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    HOME,
    TRIAGE,
    FIRST_AID,
    FACILITIES,
    RECORDS,
    MEDICINES
}

class JeevanViewModel(
    application: Application,
    private val repository: HealthRepository
) : AndroidViewModel(application) {

    private val ttsManager = TtsManager(application)
    val isSpeaking: StateFlow<Boolean> = ttsManager.isSpeaking

    // Language state
    private val _currentLanguage = MutableStateFlow(AppLanguage.MARATHI)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Tab state
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Emergency SOS Dialog
    private val _showEmergencyDialog = MutableStateFlow(false)
    val showEmergencyDialog: StateFlow<Boolean> = _showEmergencyDialog.asStateFlow()

    // Triage Wizard State
    private val _triageStep = MutableStateFlow(1)
    val triageStep: StateFlow<Int> = _triageStep.asStateFlow()

    private val _selectedSymptoms = MutableStateFlow<Set<String>>(emptySet())
    val selectedSymptoms: StateFlow<Set<String>> = _selectedSymptoms.asStateFlow()

    private val _customSymptomInput = MutableStateFlow("")
    val customSymptomInput: StateFlow<String> = _customSymptomInput.asStateFlow()

    private val _selectedDuration = MutableStateFlow("today")
    val selectedDuration: StateFlow<String> = _selectedDuration.asStateFlow()

    private val _selectedRedFlags = MutableStateFlow<Set<String>>(emptySet())
    val selectedRedFlags: StateFlow<Set<String>> = _selectedRedFlags.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _triageResult = MutableStateFlow<TriageResult?>(null)
    val triageResult: StateFlow<TriageResult?> = _triageResult.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    // Facilities Filter
    private val _selectedFacilityType = MutableStateFlow(FacilityType.ALL)
    val selectedFacilityType: StateFlow<FacilityType> = _selectedFacilityType.asStateFlow()

    private val _facilitySearch = MutableStateFlow("")
    val facilitySearch: StateFlow<String> = _facilitySearch.asStateFlow()

    // Room DB Streams
    val familyMembers: StateFlow<List<FamilyMemberEntity>> = repository.allFamilyMembers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val triageLogs: StateFlow<List<TriageLogEntity>> = repository.allTriageLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val medicines: StateFlow<List<MedicineReminderEntity>> = repository.allMedicines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customFacilities: StateFlow<List<CustomFacilityEntity>> = repository.customFacilities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Combined facilities
    val filteredFacilities: StateFlow<List<FacilityItem>> = combine(
        _selectedFacilityType,
        _facilitySearch,
        customFacilities,
        _currentLanguage
    ) { type, query, custom, lang ->
        val convertedCustom = custom.map { c ->
            FacilityItem(
                id = "custom_${c.id}",
                nameMarathi = c.name,
                nameHindi = c.name,
                nameEnglish = c.name,
                type = FacilityType.PHC,
                addressMarathi = c.address,
                addressHindi = c.address,
                addressEnglish = c.address,
                phone = c.phone,
                distanceKm = c.distanceKm,
                is24x7 = false,
                servicesMarathi = listOf("स्थानिक डॉक्टर / दवाखाना"),
                servicesHindi = listOf("स्थानीय डॉक्टर / क्लीनिक"),
                servicesEnglish = listOf("Local Clinic / Practitioner"),
                latitude = 18.5204,
                longitude = 73.8567
            )
        }
        val all = PreloadedHealthcareFacilities + convertedCustom
        all.filter { item ->
            val matchesType = (type == FacilityType.ALL) || (item.type == type)
            val name = item.getName(lang)
            val addr = item.getAddress(lang)
            val matchesQuery = query.isBlank() || name.contains(query, ignoreCase = true) || addr.contains(query, ignoreCase = true)
            matchesType && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreloadedHealthcareFacilities)

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun openEmergencyDialog() {
        _showEmergencyDialog.value = true
    }

    fun closeEmergencyDialog() {
        _showEmergencyDialog.value = false
    }

    // Triage Methods
    fun toggleSymptom(id: String) {
        val current = _selectedSymptoms.value.toMutableSet()
        if (current.contains(id)) current.remove(id) else current.add(id)
        _selectedSymptoms.value = current
    }

    fun setCustomSymptomInput(text: String) {
        _customSymptomInput.value = text
    }

    fun appendVoiceTranscript(transcript: String) {
        val existing = _customSymptomInput.value
        _customSymptomInput.value = if (existing.isBlank()) transcript else "$existing $transcript"
    }

    fun setDuration(id: String) {
        _selectedDuration.value = id
    }

    fun toggleRedFlag(id: String) {
        val current = _selectedRedFlags.value.toMutableSet()
        if (current.contains(id)) current.remove(id) else current.add(id)
        _selectedRedFlags.value = current
    }

    fun nextTriageStep() {
        if (_triageStep.value < 3) {
            _triageStep.value += 1
        } else if (_triageStep.value == 3) {
            submitTriageAssessment()
        }
    }

    fun previousTriageStep() {
        if (_triageStep.value > 1) {
            _triageStep.value -= 1
        }
    }

    fun resetTriage() {
        _triageStep.value = 1
        _selectedSymptoms.value = emptySet()
        _customSymptomInput.value = ""
        _selectedDuration.value = "today"
        _selectedRedFlags.value = emptySet()
        _triageResult.value = null
        stopAudio()
    }

    fun startVoiceTriageFlow() {
        resetTriage()
        _currentTab.value = AppTab.TRIAGE
    }

    fun submitTriageAssessment() {
        viewModelScope.launch {
            _isAnalyzing.value = true
            try {
                val result = repository.assessTriage(
                    symptomIds = _selectedSymptoms.value,
                    customSymptomsText = _customSymptomInput.value,
                    durationId = _selectedDuration.value,
                    selectedRedFlags = _selectedRedFlags.value,
                    lang = _currentLanguage.value
                )
                _triageResult.value = result
                _triageStep.value = 4
                // Auto read aloud spoken summary for accessibility
                if (result.spokenSummary.isNotBlank()) {
                    speak(result.spokenSummary)
                }
            } catch (e: Exception) {
                _statusMessage.value = "Assessment error: ${e.message}"
            } finally {
                _isAnalyzing.value = false
            }
        }
    }

    fun saveCurrentTriageToRecord(memberName: String = "Self") {
        val result = _triageResult.value ?: return
        viewModelScope.launch {
            repository.insertTriageLog(
                TriageLogEntity(
                    memberName = memberName,
                    dateMillis = System.currentTimeMillis(),
                    symptoms = _selectedSymptoms.value.joinToString(", ") + if (_customSymptomInput.value.isNotBlank()) " (${_customSymptomInput.value})" else "",
                    severity = result.level.name,
                    duration = _selectedDuration.value,
                    triageLevel = result.level.name,
                    aiGuidance = result.summary,
                    homeCareSteps = result.actionSteps.joinToString("; "),
                    recommendedFacility = result.recommendedFacility,
                    isVoiceRecorded = false
                )
            )
            _statusMessage.value = AppStrings.savedSuccessfully(_currentLanguage.value)
        }
    }

    // Audio TTS
    fun speak(text: String) {
        ttsManager.speak(text, _currentLanguage.value)
    }

    fun stopAudio() {
        ttsManager.stop()
    }

    // Facilities Filter
    fun setFacilityType(type: FacilityType) {
        _selectedFacilityType.value = type
    }

    fun setFacilitySearch(query: String) {
        _facilitySearch.value = query
    }

    fun addCustomFacility(name: String, phone: String, address: String) {
        viewModelScope.launch {
            repository.insertCustomFacility(
                CustomFacilityEntity(
                    name = name,
                    type = "Clinic",
                    phone = phone,
                    address = address,
                    distanceKm = 1.0
                )
            )
        }
    }

    // Medicines
    fun toggleMedicineTaken(medicine: MedicineReminderEntity) {
        viewModelScope.launch {
            repository.updateMedicine(medicine.copy(takenToday = !medicine.takenToday))
        }
    }

    fun addMedicine(
        name: String,
        dosage: String,
        mealTiming: String,
        morning: Boolean,
        afternoon: Boolean,
        night: Boolean
    ) {
        viewModelScope.launch {
            repository.insertMedicine(
                MedicineReminderEntity(
                    memberName = "Self",
                    medicineName = name,
                    dosage = dosage,
                    mealTiming = mealTiming,
                    morning = morning,
                    afternoon = afternoon,
                    night = night,
                    takenToday = false,
                    isActive = true
                )
            )
        }
    }

    fun deleteMedicine(medicine: MedicineReminderEntity) {
        viewModelScope.launch {
            repository.deleteMedicine(medicine)
        }
    }

    // Family Members
    fun addFamilyMember(
        name: String,
        relation: String,
        age: Int,
        gender: String,
        bloodGroup: String,
        abhaNumber: String,
        chronicConditions: String,
        emergencyPhone: String
    ) {
        viewModelScope.launch {
            repository.insertFamilyMember(
                FamilyMemberEntity(
                    fullName = name,
                    relation = relation,
                    age = age,
                    gender = gender,
                    bloodGroup = bloodGroup,
                    abhaNumber = abhaNumber,
                    chronicConditions = chronicConditions,
                    emergencyPhone = emergencyPhone
                )
            )
        }
    }

    fun deleteFamilyMember(member: FamilyMemberEntity) {
        viewModelScope.launch {
            repository.deleteFamilyMember(member)
        }
    }

    fun deleteTriageLog(log: TriageLogEntity) {
        viewModelScope.launch {
            repository.deleteTriageLog(log)
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}

class JeevanViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JeevanViewModel::class.java)) {
            val db = AppHealthDatabase.getInstance(application)
            val repo = HealthRepository(db)
            return JeevanViewModel(application, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
