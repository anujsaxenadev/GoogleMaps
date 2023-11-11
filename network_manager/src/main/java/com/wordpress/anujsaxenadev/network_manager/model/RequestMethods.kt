package com.wordpress.anujsaxenadev.network_manager.model

import com.wordpress.anujsaxenadev.network_manager.impl.Method

internal sealed class RequestMethods(val name: Method){
    data object Get: RequestMethods("get")
    data object Post: RequestMethods("post")
    data object Put: RequestMethods("put")
    data object Delete: RequestMethods("delete")
}
