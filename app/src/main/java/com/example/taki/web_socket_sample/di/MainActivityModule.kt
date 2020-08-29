package com.example.taki.web_socket_sample.di

import androidx.fragment.app.FragmentActivity
import com.example.taki.web_socket_sample.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("unused")
abstract class MainActivityModule {

    @Binds
    abstract fun providesMainActivity(mainActivity: MainActivity): FragmentActivity

    @Module
    abstract class MainActivityBuilder {
        @ContributesAndroidInjector(modules = [MainActivityModule::class])
        abstract fun contributesMainActivity(): MainActivity
    }
}