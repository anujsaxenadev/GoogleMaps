package com.wordpress.anujsaxenadev.analytics.impl

import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.analytics.BuildConfig
import com.wordpress.anujsaxenadev.analytics.helpers.impl.FirebaseAnalyticsHelper
import com.wordpress.anujsaxenadev.analytics.helpers.impl.LogcatAnalyticsHelper
import javax.inject.Inject

internal class AnalyticsManagerImpl @Inject constructor(
    private val logcatAnalyticsHelper: LogcatAnalyticsHelper,
    private val firebaseAnalyticsHelper: FirebaseAnalyticsHelper
): AnalyticsManager {
    override fun log(exception: Throwable){
        if(BuildConfig.BUILD_TYPE.lowercase() == "debug"){
            logcatAnalyticsHelper.log(exception)
        }
        else{
            firebaseAnalyticsHelper.log(exception)
        }
    }
}