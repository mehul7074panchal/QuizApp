package com.mehul.quizapp

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


class MyApplication : Application() {

     private var mInstance: MyApplication? = null
    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0) //.encryptionKey(key)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }


}
