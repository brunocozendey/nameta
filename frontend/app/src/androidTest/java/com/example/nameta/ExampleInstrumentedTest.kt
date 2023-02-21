package com.example.nameta

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import com.example.data.DialogflowDataSource

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)

class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.nameta", appContext.packageName)
    }
}

class DialogflowOnAndroidTest {
    @Test
    fun simpleIntent_hasAnswer() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val credentials = appContext.resources.openRawResource(R.raw.credentials)
            val dataSource = DialogflowDataSource(credentials)
            val sessionId = "my-android-unittest-session"
            val languageCode = "pt-BR"
            val result = dataSource.detectIntentTexts("Olá!", sessionId, languageCode)
            assertEquals("Olá!", result)
        }
}