package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.CustomFacilityDao
import com.example.data.local.dao.FamilyMemberDao
import com.example.data.local.dao.MedicineReminderDao
import com.example.data.local.dao.TriageLogDao
import com.example.data.local.entities.CustomFacilityEntity
import com.example.data.local.entities.FamilyMemberEntity
import com.example.data.local.entities.MedicineReminderEntity
import com.example.data.local.entities.TriageLogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        FamilyMemberEntity::class,
        TriageLogEntity::class,
        MedicineReminderEntity::class,
        CustomFacilityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppHealthDatabase : RoomDatabase() {
    abstract fun familyMemberDao(): FamilyMemberDao
    abstract fun triageLogDao(): TriageLogDao
    abstract fun medicineReminderDao(): MedicineReminderDao
    abstract fun customFacilityDao(): CustomFacilityDao

    companion object {
        @Volatile
        private var INSTANCE: AppHealthDatabase? = null

        fun getInstance(context: Context): AppHealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppHealthDatabase::class.java,
                    "jeevan_health_db"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed initial demo data
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.familyMemberDao().insertMember(
                                FamilyMemberEntity(
                                    fullName = "रामराव पाटील (Ramrao Patil)",
                                    relation = "Self",
                                    age = 48,
                                    gender = "Male",
                                    bloodGroup = "O+",
                                    abhaNumber = "14-2938-1029-4821",
                                    chronicConditions = "रक्तदाब (Hypertension / BP)",
                                    allergies = "पेनिसिलिन (Penicillin)",
                                    emergencyPhone = "9822010800"
                                )
                            )
                            database.medicineReminderDao().insertMedicine(
                                MedicineReminderEntity(
                                    memberName = "रामराव पाटील",
                                    medicineName = "Amlodipine 5mg (बीपी गोळी)",
                                    dosage = "१ गोळी (1 Tablet)",
                                    mealTiming = "AFTER_FOOD",
                                    morning = true,
                                    afternoon = false,
                                    night = false,
                                    takenToday = true
                                )
                            )
                            database.medicineReminderDao().insertMedicine(
                                MedicineReminderEntity(
                                    memberName = "रामराव पाटील",
                                    medicineName = "Paracetamol 500mg",
                                    dosage = "१ गोळी तापासाठी",
                                    mealTiming = "AFTER_FOOD",
                                    morning = true,
                                    afternoon = false,
                                    night = true,
                                    takenToday = false
                                )
                            )
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
