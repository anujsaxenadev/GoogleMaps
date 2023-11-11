package com.wordpress.anujsaxenadev.resource_manager.impl

import android.content.Context
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import com.wordpress.anujsaxenadev.resource_manager.ResourceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AndroidInternalStorageResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
): ResourceManager {

    override suspend fun resourceExists(resourceName: ResourceName): Result<Boolean> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInternalFileInstance(resourceName).fold({
                it.exists()
            }, {
                throw it
            })
        }
    }

    private suspend fun getInternalFileInstance(resourceName: ResourceName): Result<File> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            File(context.cacheDir, resourceName)
        }
    }

    override suspend fun saveResourceAndGetReference(
        resourceName: ResourceName,
        response: InputStream
    ): Result<InputStream> {
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInternalFileInstance(resourceName).fold({ outputFile ->
                outputFile.createNewFile()
                copyFilesGetFileReference(
                    resourceName = resourceName,
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

    override suspend fun getResourceReference(resourceName: ResourceName): Result<InputStream>{
        return runCatchingWithDispatcher(Dispatchers.IO){
            getInternalFileInstance(resourceName).fold({
                FileInputStream(it.absolutePath)
            }, {
                throw it
            })
        }
    }

    private suspend fun copyFilesGetFileReference(
        resourceName: ResourceName,
        inputStream: InputStream,
        outputFile: File): Result<InputStream>{
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
            getResourceReference(resourceName).fold({
                it
            }, {
                throw it
            })
        }
    }
}