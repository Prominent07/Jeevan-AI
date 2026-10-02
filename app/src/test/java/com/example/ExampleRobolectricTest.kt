package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AppLanguage
import com.example.data.model.TriageLevel
import com.example.data.remote.OfflineTriageEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("JeevanAI", appName)
    }

    @Test
    fun testOfflineTriageEngineUrgentRedFlag() {
        val result = OfflineTriageEngine.evaluate(
            symptomIds = setOf("snake_bite"),
            customSymptomsText = "",
            durationId = "today",
            selectedRedFlags = emptySet(),
            lang = AppLanguage.MARATHI
        )
        assertEquals(TriageLevel.URGENT, result.level)
        assertNotNull(result.title)
    }

    @Test
    fun testOfflineTriageEngineRoutineCare() {
        val result = OfflineTriageEngine.evaluate(
            symptomIds = setOf("cough"),
            customSymptomsText = "mild cold",
            durationId = "today",
            selectedRedFlags = emptySet(),
            lang = AppLanguage.ENGLISH
        )
        assertEquals(TriageLevel.ROUTINE, result.level)
    }
}
