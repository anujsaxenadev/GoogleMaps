package com.wordpress.anujsaxenadev.file_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.file_manager.FileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    ): Result<InputStream?> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            try {
                getFileInstance(fileName).fold({file ->
                    FileOutputStream(file).use {stream ->
                        val buffer = ByteArray(1024)
                        var bytesRead: Int
                        while (response.read(buffer).also { bytesRead = it } != -1){
                            stream.write(buffer, 0 , bytesRead)
                        }
                    }
                    response.close()
                    FileInputStream(file.absolutePath)
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
}


suspend fun <T> runCatchingWithDispatcher(dispatcher: CoroutineDispatcher, block: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        runCatching{ block() }
    }
}