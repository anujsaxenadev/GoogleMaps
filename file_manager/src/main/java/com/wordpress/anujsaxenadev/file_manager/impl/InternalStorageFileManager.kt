package com.wordpress.anujsaxenadev.file_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.common.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.file_manager.FileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InternalStorageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
): FileManager {

    override suspend fun fileExists(fileName: FileName): Result<Boolean> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInteralFileInstance(fileName).fold({
                it.exists()
            }, {
                throw it
            })
        }
    }

    private suspend fun getInteralFileInstance(fileName: FileName): Result<File> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            File(context.cacheDir, fileName)
        }
    }

    override suspend fun saveDataAndReturnStreamReference(
        fileName: FileName,
        response: InputStream
    ): Result<InputStream> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInteralFileInstance(fileName).fold({ outputFile ->
                copyFilesGetFileReference(
                    inputStream = response,
                    outputFile = outputFile
                ).fold({
                    it
                },{
                    throw it
                })
            },{
                throw it
            })
        }
    }

    override suspend fun createNewFile(fileName: FileName): Result<File>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInteralFileInstance(fileName).fold({
                it.createNewFile()
                it
            }, {
                throw it
            })
        }
    }

    private suspend fun copyFilesGetFileReference(inputStream: InputStream, outputFile: File): Result<FileInputStream>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            FileOutputStream(outputFile).use { stream ->
                // Keeping Buffer Size for 1024 for Stream
                val buffer = ByteArray(size = 1024)
                var bytesRead: Int
                inputStream.use { responseStream ->
                    while (responseStream.read(buffer).also { bytesRead = it } != -1) {
                        stream.write(buffer, 0, bytesRead)
                    }
                }
            }
            FileInputStream(outputFile.absolutePath)
        }
    }
}