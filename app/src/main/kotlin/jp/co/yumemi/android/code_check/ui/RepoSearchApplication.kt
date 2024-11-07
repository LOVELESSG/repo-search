package jp.co.yumemi.android.code_check.ui

import android.app.Application
import jp.co.yumemi.android.code_check.Graph

class RepoSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}