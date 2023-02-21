package com.example.data

import org.junit.Test
import java.io.File

class GoogleSheetsDataSourceUnitTest {
    private val stream = File("C:/Users/corde/AndroidStudioProjects/NAMeTA/app/src/main/res/raw/credentials_sheets.json").inputStream()
    private val dataSource = GoogleSheetsDataSource(stream)
    private val sheets = dataSource.createSheetsService()
    private val spreadsheetId = "1vFxAt97DXEJwIEZC3yleHeWR7VCHdOze14B9IbTu-vk"
    private val range = "Medication Plan!A1"
    private val ranges = listOf("Medication Plan!A1", "Medication Plan!B1")

    @Test
    fun test_readSingleRange() {
        val result = dataSource.readSingleRange(
            sheets,
            spreadsheetId,
            range,
        )
        println(result)
    }

    @Test
    fun test_readMultipleRanges() {
        val result = dataSource.readMultipleRanges(
            sheets,
            spreadsheetId,
            ranges
        )
        println(result)
    }

    @Test
    fun test_writeToSingleRange() {
        dataSource.writeToSingleRange(
            sheets,
            spreadsheetId,
            "Medication Plan!A2",
            listOf(listOf("lemon")),
            "USER_ENTERED"
        )
        val result = dataSource.readSingleRange(
            sheets,
            spreadsheetId,
            "Medication Plan!A2",
        )
        println(result)
    }

    @Test
    fun test_writeToMultipleRanges() {
        dataSource.writeToMultipleRanges(
            sheets,
            spreadsheetId,
            data = mutableListOf(),
            "Medication Plan!A3:A4",
            listOf(listOf("orange, grape")),
            "USER_ENTERED")
        val result = dataSource.readMultipleRanges(
            sheets,
            spreadsheetId,
            listOf("Medication Plan!A3", "Medication Plan!A4")
        )
        println(result)
    }

    @Test
    fun test_appendValue() {
        dataSource.appendValue(
            sheets,
            spreadsheetId,
            "Medication Plan!A5",
            listOf(listOf("apple", "banana")),
            "USER_ENTERED"
        )
        val result = dataSource.readMultipleRanges(
            sheets,
            spreadsheetId,
            listOf("Medication Plan!A3", "Medication Plan!A4")
        )
        println(result)
    }
}