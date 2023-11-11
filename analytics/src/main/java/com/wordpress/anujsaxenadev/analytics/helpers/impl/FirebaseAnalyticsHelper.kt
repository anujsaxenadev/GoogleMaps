package com.wordpress.anujsaxenadev.analytics.helpers.impl

import com.wordpress.anujsaxenadev.analytics.helpers.AnalyticsHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Logging to Some Non Fatals of Firebase.
 */
@Singleton
internal class FirebaseAnalyticsHelper @Inject constructor() : AnalyticsHelper {
    override fun log(exception: Throwable) {
        // Log Exception to Firebase
    }
}