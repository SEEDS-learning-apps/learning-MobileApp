package com.example.chat_bot.seeds

import com.example.chat_bot.Activities.Notification.Activity.Notification_data.database.NotificationDatabase
import com.crushtech.timelyapp.data.repository.NotificationRepository
import com.example.chat_bot.Activities.Notification.Activity.Notification_UI.NotificationViewModelFactory
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
        bind() from singleton { NotificationDatabase(instance()) }
        bind() from singleton { NotificationRepository(instance()) }
        bind() from provider {
            NotificationViewModelFactory(instance())
        }
        bind<NotificationViewModelFactory>(tag = "NotificationFragment") with provider {
            NotificationViewModelFactory(instance())
        }
    }
}
