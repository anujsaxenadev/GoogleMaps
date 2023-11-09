package com.wordpress.anujsaxenadev.file_manager

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> runCatchingWithDispatcher(dispatcher: CoroutineDispatcher, block: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        runCatching{ block() }
    }
}