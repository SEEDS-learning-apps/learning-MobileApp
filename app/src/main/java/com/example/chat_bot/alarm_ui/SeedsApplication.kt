package com.example.chat_bot.seeds

import android.app.Application
import com.example.chat_bot.alarm_data.database.AlarmDatabase
import com.crushtech.timelyapp.data.repository.AlarmRepository
import com.example.chat_bot.alarm_ui.AlarmViewModelFactory
import com.example.chat_bot.utils.Language
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

open class SeedsApplication() : Language(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@SeedsApplication))
        bind() from singleton { AlarmDatabase(instance()) }
        bind() from singleton { AlarmRepository(instance()) }
        bind() from provider {
            AlarmViewModelFactory(instance())
        }
        bind<AlarmViewModelFactory>(tag = "AlarmFragment") with provider {
            AlarmViewModelFactory(instance())
        }
    }
}
