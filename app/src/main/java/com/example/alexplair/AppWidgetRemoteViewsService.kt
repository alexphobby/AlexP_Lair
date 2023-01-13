package com.example.alexplair

import android.content.Intent
import android.widget.RemoteViewsService

class AppWidgetRemoteViewsService :RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent?): AppWidgetRemoteViewsFactory {
        return AppWidgetRemoteViewsFactory(applicationContext)
    }
}
