package com.wordpress.anujsaxenadev.file_manager

import com.wordpress.anujsaxenadev.file_manager.impl.FileName
import java.io.File

interface FileManager {
    suspend fun fileExists(fileName: FileName): Boolean

    suspend fun getFileInstance(fileName: FileName): File
}