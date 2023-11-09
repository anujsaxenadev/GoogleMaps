package com.wordpress.anujsaxenadev.file_manager

import com.wordpress.anujsaxenadev.file_manager.impl.FileName
import java.io.File
import java.io.InputStream

interface FileManager {

    /**
     * @param fileName - Name of the Resource that we need to check.
     *
     * @return [Result] of true if file exists, false if file does not exist, [Throwable] if there is some error happened while checking.
     */
    suspend fun fileExists(fileName: FileName): Result<Boolean>

    /**
     * @param fileName - Name of the Resource that will be stored in a file.
     * @param response - [InputStream] for the input resource that we need to save.
     *
     * @return [Result] of [InputStream] stream to the created file or [Throwable] if there is some error happened while copy process.
     */
    suspend fun saveDataAndReturnStreamReference(fileName: FileName, response: InputStream): Result<InputStream>

    /**
     * @param fileName - Name of the Resource.
     *
     * @return [Result] of [File] file reference to the created file or [Throwable] if there is some error happened while creation.
     */
    suspend fun createNewFile(fileName: FileName): Result<File>
}