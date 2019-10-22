package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App: Application() {
    companion object{
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        PreferencesRepository.getAppTheme().also {
//            AppCompatDelegate.setDefaultNightMode(it)
//        }
    }
}