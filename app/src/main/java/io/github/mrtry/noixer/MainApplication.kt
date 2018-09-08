package io.github.mrtry.noixer

import android.app.Application
import com.facebook.stetho.Stetho


/**
 * Created by mrtry on 2018/09/08.
 */
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initStetho()
    }

    fun initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        )
    }
}