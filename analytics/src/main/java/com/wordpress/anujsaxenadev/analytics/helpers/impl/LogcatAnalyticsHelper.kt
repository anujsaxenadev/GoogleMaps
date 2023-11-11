package com.wordpress.anujsaxenadev.analytics.helpers.impl

import android.util.Log
import com.wordpress.anujsaxenadev.analytics.helpers.AnalyticsHelper
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "LogcatAnalyticsHelper"

/**
 * Logs The exceptions to Logcat
 */
@Singleton
internal class LogcatAnalyticsHelper @Inject constructor() : AnalyticsHelper {
    override fun log(exception: Throwable) {
        Log.e(TAG, exception.message, exception)
    }
}