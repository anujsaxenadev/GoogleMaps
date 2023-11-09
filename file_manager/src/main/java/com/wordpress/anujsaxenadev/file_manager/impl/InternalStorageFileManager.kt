package com.wordpress.anujsaxenadev.file_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.file_manager.FileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
): FileManager {

    override suspend fun fileExists(fileName: FileName): Boolean {
        return withContext(Dispatchers.IO + CoroutineExceptionHandler{_, _ ->
            // TODO: Call Logger
        }){
            File(context.cacheDir, fileName).exists()
        }
    }

    override suspend fun getFileInstance(fileName: FileName): File {
        return withContext(Dispatchers.IO + CoroutineExceptionHandler{_, _ ->
            // TODO: Call Logger
        }){
            File(context.cacheDir, fileName)
        }
    }
}