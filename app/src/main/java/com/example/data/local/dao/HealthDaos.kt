package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.CustomFacilityEntity
import com.example.data.local.entities.FamilyMemberEntity
import com.example.data.local.entities.MedicineReminderEntity
import com.example.data.local.entities.TriageLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyMemberDao {
    @Query("SELECT * FROM family_members ORDER BY id ASC")
    fun getAllMembers(): Flow<List<FamilyMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: FamilyMemberEntity): Long

    @Delete
    suspend fun deleteMember(member: FamilyMemberEntity)
}

@Dao
interface TriageLogDao {
    @Query("SELECT * FROM triage_logs ORDER BY dateMillis DESC")
    fun getAllLogs(): Flow<List<TriageLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: TriageLogEntity): Long

    @Delete
    suspend fun deleteLog(log: TriageLogEntity)
}

@Dao
interface MedicineReminderDao {
    @Query("SELECT * FROM medicine_reminders ORDER BY id DESC")
    fun getAllMedicines(): Flow<List<MedicineReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineReminderEntity): Long

    @Update
    suspend fun updateMedicine(medicine: MedicineReminderEntity)

    @Delete
    suspend fun deleteMedicine(medicine: MedicineReminderEntity)

    @Query("UPDATE medicine_reminders SET takenToday = 0")
    suspend fun resetDailyStatus()
}

@Dao
interface CustomFacilityDao {
    @Query("SELECT * FROM custom_facilities ORDER BY id DESC")
    fun getAllFacilities(): Flow<List<CustomFacilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacility(facility: CustomFacilityEntity): Long

    @Delete
    suspend fun deleteFacility(facility: CustomFacilityEntity)
}
