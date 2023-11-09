package com.wordpress.anujsaxenadev.file_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.file_manager.FileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
): FileManager {

    override suspend fun fileExists(fileName: FileName): Boolean {
        return withContext(Dispatchers.IO){
            getFileInstance(fileName).exists()
        }
    }

    private suspend fun getFileInstance(fileName: FileName): File {
        return withContext(Dispatchers.IO){
            File(context.cacheDir, fileName)
        }
    }

    override suspend fun saveDataAndReturnStreamReference(
        fileName: FileName,
        response: InputStream
    ): InputStream {
        return withContext(Dispatchers.IO){
            val outputFile = getFileInstance(fileName)

            FileOutputStream(outputFile).use {
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (response.read(buffer).also { bytesRead = it } != -1){
                    it.write(buffer, 0 , bytesRead)
                }
            }
            response.close()
            FileInputStream(outputFile.absolutePath)
        }
    }

    override suspend fun createNewFile(fileName: FileName): File{
        return withContext(Dispatchers.IO){
            val file = File(context.cacheDir, fileName)
            file.createNewFile()
            file
        }
    }
}