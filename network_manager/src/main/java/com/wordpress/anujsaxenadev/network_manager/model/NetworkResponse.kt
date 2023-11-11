package com.wordpress.anujsaxenadev.network_manager.model

import java.io.InputStream

data class NetworkResponse(
    val response: InputStream?,
    val headers: HashMap<String, String>
)