package com.wordpress.anujsaxenadev.network_manager.extensions

import com.wordpress.anujsaxenadev.network_manager.impl.Method
import com.wordpress.anujsaxenadev.network_manager.model.RequestMethods

internal fun Method.getMethod(): RequestMethods {
    return when(this.lowercase()){
        RequestMethods.Get.name -> RequestMethods.Get
        RequestMethods.Post.name -> RequestMethods.Post
        RequestMethods.Put.name -> RequestMethods.Put
        RequestMethods.Delete.name -> RequestMethods.Delete
        else -> RequestMethods.Get
    }
}