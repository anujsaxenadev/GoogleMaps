package com.wordpress.anujsaxenadev.file_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.file_manager.FileManager
import com.wordpress.anujsaxenadev.file_manager.runCatchingWithDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
): FileManager {

    companion object{
        private const val BufferSize = 1024
    }

    override suspend fun fileExists(fileName: FileName): Result<Boolean> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getFileInstance(fileName).fold({
                it.exists()
            }, {
                throw it
            })
        }
    }

    private suspend fun getFileInstance(fileName: FileName): Result<File> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            try {
                File(context.cacheDir, fileName)
            }
            catch (e: Throwable){
                throw e
            }
        }
    }

    override suspend fun saveDataAndReturnStreamReference(
        fileName: FileName,
        response: InputStream
    ): Result<InputStream> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            try {
                getFileInstance(fileName).fold({file ->
                    copyFilesGetFileReference(response, file).fold({
                        it
                    },{
                        throw it
                    })
                },{
                    throw it
                })
            }
            catch (e: Throwable){
                throw e
            }
        }
    }

    override suspend fun createNewFile(fileName: FileName): Result<File>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            try {
                val file = File(context.cacheDir, fileName)
                file.createNewFile()
                file
            }
            catch (e: Throwable){
                throw e
            }
        }
    }

    private suspend fun copyFilesGetFileReference(inputStream: InputStream, outputFile: File): Result<FileInputStream>{
        return runCatchingWithDispatcher(Dispatchers.IO) {
            FileOutputStream(outputFile).use { stream ->
                val buffer = ByteArray(BufferSize)
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