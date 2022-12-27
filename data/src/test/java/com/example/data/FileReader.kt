package com.example.data

import java.io.File

object FileReader {

    fun readFile(fileName: String): String {
        val classLoader = FileReader::class.java.classLoader
        val file = File(classLoader?.getResource(fileName)?.file)
        return file.bufferedReader()
            .use { reader -> reader.readText() }
    }
}
