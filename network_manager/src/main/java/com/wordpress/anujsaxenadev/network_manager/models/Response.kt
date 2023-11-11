package com.wordpress.anujsaxenadev.network_manager.models

import java.io.InputStream

data class Response(
    val response: InputStream?,
    val headers: HashMap<String, String>
)