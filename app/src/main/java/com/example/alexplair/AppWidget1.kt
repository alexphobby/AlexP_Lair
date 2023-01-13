package com.example.alexplair

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.TextView

/**
 * Implementation of App Widget functionality.
 */
class AppWidget1 : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.e(ContentValues.TAG, "mqtt onUpdate")
        // There may be multiple widgets active, so update all of them
        appWidgetIds.forEach {
            appWidgetId ->
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(
//                /* context = */ context,
//                /* requestCode = */  0,
//                /* intent = */ Intent(context, FirstFragment::class.java),
//                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.app_widget1
            ).apply {
                setOnClickPendingIntent(R.id.Send, pendingIntent(context,"mqtt pending"))
            }


            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(ContentValues.TAG, "mqtt onReceive,${intent?.action.toString()}")
        Log.e(ContentValues.TAG, "mqtt onReceive,${intent?.flags}")
        if(context != null) {
            val views = RemoteViews(context.packageName, R.layout.app_widget1)
            Log.e(ContentValues.TAG, "mqtt onReceive context not null")

                //var intent:Intent= Intent(context,"MainActivity ")
            views.setOnClickPendingIntent(R.id.Send, pendingIntent(context, "increase"))
            views.setOnClickPendingIntent(R.id.Lights, pendingIntent(context, "switchLights"))
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent(context, "switchLights"))

        }
        var mqtt = MqttUtilities

        var cb = MyCallBack
        var myhome = MyHome

        val views = RemoteViews(context?.packageName, R.layout.app_widget1)
        views.setTextViewText(R.id.appwidget_text, myhome.temperature.toString())
        //AppWidgetManager.getInstance(context).updateAppWidget(ComponentName(context,javaClass) )
        //val appWidgetManager = AppWidgetManager.getInstance(context)
        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds("AppWidget1" as ComponentName),R.id.appwidget_text)
//        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views)

       //# appWidgetManager.updateAppWidget(appWidgetId, views)
       // super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        //setContentView(R.layout.app_widget1)
        Log.e(ContentValues.TAG, "mqtt onEnabled")
        val widgetText = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.app_widget1)
        views.setTextViewText(R.id.appwidget_text, widgetText)

        views.setOnClickPendingIntent(R.id.Send, pendingIntent(context,"increase"))
        views.setOnClickPendingIntent(R.id.Lights, pendingIntent(context,"switchLights"))

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    public fun pendingIntent (context: Context,
                              action : String) : PendingIntent? {
        Log.e(ContentValues.TAG, "mqtt fun pendingintent")
        val intent = Intent(context,javaClass)
        intent.action = action
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getBroadcast(context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }

    internal fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        Log.e(ContentValues.TAG, "mqtt updateAppWidget")
        val widgetText = "TEST1" //..context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.app_widget1)
        views.setTextViewText(R.id.appwidget_text, widgetText)
        views.setRemoteAdapter(R.id.appwidget_text, Intent( context,AppWidgetRemoteViewsService::class.java))
        //views.setOnClickPendingIntent(R.id.Send, pendingIntent(context,"increase"))
        //views.setOnClickPendingIntent(R.id.Lights, pendingIntent(context,"switchLights"))

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}



