package com.example.alexplair

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class AppWidgetRemoteViewsFactory (val context: Context): RemoteViewsService.RemoteViewsFactory {
    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName,R.layout.app_widget1)
        rv.setTextViewText(R.id.appwidget_text,"caca")
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

//    override fun getViewAt(position:Int):RemoteViews {
//        val rv = RemoteViews(context.packageName,R.layout.app_widget1)
//        rv.setTextViewText(R.id.appwidget_text, "new caca")
//        return rv
//
//    }

}