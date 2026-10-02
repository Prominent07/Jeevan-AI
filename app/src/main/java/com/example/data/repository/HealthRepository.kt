package com.example.data.repository

import com.example.data.local.AppHealthDatabase
import com.example.data.local.entities.CustomFacilityEntity
import com.example.data.local.entities.FamilyMemberEntity
import com.example.data.local.entities.MedicineReminderEntity
import com.example.data.local.entities.TriageLogEntity
import com.example.data.model.AppLanguage
import com.example.data.model.TriageResult
import com.example.data.remote.GeminiTriageService
import kotlinx.coroutines.flow.Flow

class HealthRepository(
    private val database: AppHealthDatabase,
    private val geminiService: GeminiTriageService = GeminiTriageService()
) {
    val allFamilyMembers: Flow<List<FamilyMemberEntity>> =
        database.familyMemberDao().getAllMembers()

    suspend fun insertFamilyMember(member: FamilyMemberEntity): Long =
        database.familyMemberDao().insertMember(member)

    suspend fun deleteFamilyMember(member: FamilyMemberEntity) =
        database.familyMemberDao().deleteMember(member)

    val allTriageLogs: Flow<List<TriageLogEntity>> =
        database.triageLogDao().getAllLogs()

    suspend fun insertTriageLog(log: TriageLogEntity): Long =
        database.triageLogDao().insertLog(log)

    suspend fun deleteTriageLog(log: TriageLogEntity) =
        database.triageLogDao().deleteLog(log)

    val allMedicines: Flow<List<MedicineReminderEntity>> =
        database.medicineReminderDao().getAllMedicines()

    suspend fun insertMedicine(medicine: MedicineReminderEntity): Long =
        database.medicineReminderDao().insertMedicine(medicine)

    suspend fun updateMedicine(medicine: MedicineReminderEntity) =
        database.medicineReminderDao().updateMedicine(medicine)

    suspend fun deleteMedicine(medicine: MedicineReminderEntity) =
        database.medicineReminderDao().deleteMedicine(medicine)

    val customFacilities: Flow<List<CustomFacilityEntity>> =
        database.customFacilityDao().getAllFacilities()

    suspend fun insertCustomFacility(facility: CustomFacilityEntity): Long =
        database.customFacilityDao().insertFacility(facility)

    suspend fun deleteCustomFacility(facility: CustomFacilityEntity) =
        database.customFacilityDao().deleteFacility(facility)

    suspend fun assessTriage(
        symptomIds: Set<String>,
        customSymptomsText: String,
        durationId: String,
        selectedRedFlags: Set<String>,
        lang: AppLanguage
    ): TriageResult {
        return geminiService.getTriageAssessment(
            symptomIds = symptomIds,
            customSymptomsText = customSymptomsText,
            durationId = durationId,
            selectedRedFlags = selectedRedFlags,
            lang = lang
        )
    }
}
