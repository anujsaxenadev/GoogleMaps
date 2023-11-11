package com.wordpress.anujsaxenadev.googlemaps.core.extensions

import android.net.Uri
import com.wordpress.anujsaxenadev.common.extensions.runCatchingWithDispatcher
import kotlinx.coroutines.Dispatchers

suspend fun Uri.getUniqueIdentifier(): Result<String>{
    return runCatchingWithDispatcher(Dispatchers.IO){
        val builder = StringBuilder()
        val separator = '-'

        builder.apply {
            append(scheme)

            append(separator).append(authority)

            for(segment in pathSegments){
                append(separator).append(segment)
            }

            for(key in queryParameterNames){
                append(separator).append(key).append(separator).append(getQueryParameter(key))
            }

            if(!fragment.isNullOrEmpty()){
                append(separator).append(fragment)
            }

            for(nestedUri in pathSegments){
                append(separator).append(nestedUri)
            }
        }
        builder.toString()
    }
}