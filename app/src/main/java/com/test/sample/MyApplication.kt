package com.test.sample


import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 * Date 2019/5/30 6:38 PM
 *
 * @author tangxin
 */
class MyApplication : Application() {

    companion object {

        lateinit var application: Application

        var viewModelStore = ViewModelStore()

        fun of(): ViewModelProvider {
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            return ViewModelProvider(viewModelStore, factory)
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}
