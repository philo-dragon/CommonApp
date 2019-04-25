package com.pfl.common.di

import android.app.Application
import dagger.Component

@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    val application: Application
}
