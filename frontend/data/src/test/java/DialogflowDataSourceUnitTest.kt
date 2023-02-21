package com.example.data

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class DialogflowDataSourceUnitTest {
    @Test
    fun simpleIntent_hasAnswer() {
        val stream = File("C:/Users/corde/AndroidStudioProjects/NAMeTA/app/src/main/res/raw/credentials_dialogflow.json").inputStream()
        val dataSource = DialogflowDataSource(stream)
        val sessionId = "my-unittest-session"
        val languageCode = "pt-BR"
        val result = dataSource.detectIntentTexts("Olá!", sessionId, languageCode)
        assertEquals("Olá!", result)
    }
}