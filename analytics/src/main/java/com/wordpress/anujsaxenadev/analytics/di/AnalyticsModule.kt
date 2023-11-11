package com.wordpress.anujsaxenadev.analytics.di

import com.wordpress.anujsaxenadev.analytics.AnalyticsManager
import com.wordpress.anujsaxenadev.analytics.helpers.AnalyticsHelper
import com.wordpress.anujsaxenadev.analytics.helpers.impl.FirebaseAnalyticsHelper
import com.wordpress.anujsaxenadev.analytics.helpers.impl.LogcatAnalyticsHelper
import com.wordpress.anujsaxenadev.analytics.impl.AnalyticsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AnalyticsModule {
    @Binds
    abstract fun bindsAnalyticsHelper(logcatAnalyticsHelper: LogcatAnalyticsHelper): AnalyticsHelper

    @Binds
    abstract fun providesAnalyticsHelper(firebaseAnalyticsHelper: FirebaseAnalyticsHelper): AnalyticsHelper

    @Binds
    abstract fun providesAnalyticsManagerHelper(analyticsManagerImpl: AnalyticsManagerImpl): AnalyticsManager

}