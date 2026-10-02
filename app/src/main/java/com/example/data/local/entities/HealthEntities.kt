package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "family_members")
data class FamilyMemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val relation: String, // Self, Spouse, Mother, Father, Child, Other
    val age: Int,
    val gender: String, // Male, Female, Other
    val bloodGroup: String = "Unknown", // A+, B+, O+, AB+, etc.
    val abhaNumber: String = "", // Ayushman Bharat Health Account ID
    val chronicConditions: String = "", // e.g., BP, Diabetes
    val allergies: String = "",
    val emergencyPhone: String = ""
)

@Entity(tableName = "triage_logs")
data class TriageLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val memberName: String,
    val dateMillis: Long = System.currentTimeMillis(),
    val symptoms: String,
    val severity: String,
    val duration: String,
    val triageLevel: String, // ROUTINE, DOCTOR_CONSULT, URGENT
    val aiGuidance: String,
    val homeCareSteps: String,
    val recommendedFacility: String,
    val isVoiceRecorded: Boolean = false
)

@Entity(tableName = "medicine_reminders")
data class MedicineReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val memberName: String,
    val medicineName: String,
    val dosage: String, // e.g., "1 Tablet", "5 ml"
    val mealTiming: String, // "BEFORE_FOOD", "AFTER_FOOD"
    val morning: Boolean = true,
    val afternoon: Boolean = false,
    val night: Boolean = true,
    val takenToday: Boolean = false,
    val isActive: Boolean = true
)

@Entity(tableName = "custom_facilities")
data class CustomFacilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val phone: String,
    val address: String,
    val distanceKm: Double = 1.0
)
