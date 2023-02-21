package com.example.data

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.*
import java.io.File
import java.io.InputStream
import java.util.*

// https://docs.google.com/spreadsheets/d/1UISEJbBe7UGYYiPw1-3VvFJF1avHzlUv9N92WbRKSyM/edit#gid=0

const val APPLICATION_NAME = "google-sheets"

class GoogleSheetsDataSource constructor(credentialsStream : InputStream) {
    private val credentials : GoogleCredential
            = GoogleCredential.fromStream(credentialsStream)

    fun createSheetsService(): Sheets {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = GsonFactory.getDefaultInstance()
        return Sheets.Builder(httpTransport, jsonFactory, credentials.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS)))
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    fun readSingleRange(
        service: Sheets,
        spreadsheetId: String,
        range: String
    ) : ValueRange {
        return service.spreadsheets().values().get(spreadsheetId, range).execute();
    }

    fun readMultipleRanges(
        service: Sheets,
        spreadsheetId: String,
        ranges: List<String>
    ) : BatchGetValuesResponse {
        return service.spreadsheets().values().batchGet(spreadsheetId).setRanges(ranges).execute();
    }

    fun writeToSingleRange(
        service: Sheets,
        spreadsheetId: String,
        range: String,
        values: List<List<Any>>,
        // valueInputOption = "USER_ENTERED"
        valueInputOption: String
    ) {
        val body = ValueRange()
            .setValues(values)
        val result: UpdateValuesResponse =
            service.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption(valueInputOption)
                .execute()
    }

    fun writeToMultipleRanges(
        service: Sheets,
        spreadsheetId: String,
        data: MutableList<ValueRange>,
        range: String,
        values: List<List<Any>>,
        valueInputOption: String
    ) {
        data.add(
            ValueRange()
                .setRange(range)
                .setValues(values)
        )
        val body = BatchUpdateValuesRequest()
            .setValueInputOption(valueInputOption)
            .setData(data)
        val result = service.spreadsheets().values().batchUpdate(spreadsheetId, body).execute()
    }

    fun appendValue(
        service: Sheets,
        spreadsheetId: String,
        range: String,
        values: List<List<Any>>,
        valueInputOption: String
    ) {
        val body = ValueRange()
            .setValues(values)
        val result: AppendValuesResponse =
            service.spreadsheets().values().append(spreadsheetId, range, body)
                .setValueInputOption(valueInputOption)
                .execute()

    }
}