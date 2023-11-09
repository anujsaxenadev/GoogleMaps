package com.wordpress.anujsaxenadev.file_manager

import com.wordpress.anujsaxenadev.file_manager.impl.FileName
import java.io.File
import java.io.InputStream

interface FileManager {
    suspend fun fileExists(fileName: FileName): Result<Boolean>

    suspend fun saveDataAndReturnStreamReference(fileName: FileName, response: InputStream): Result<InputStream?>

    suspend fun createNewFile(fileName: FileName): Result<File>
}