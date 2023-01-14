package com.example.alexplair

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
//import kotlinx.coroutines.DefaultExecutor.schedule
import java.util.*
import kotlin.concurrent.timerTask

class LightsController : AppCompatActivity() {
    var mqtt=MqttUtilities
    var cb = MyCallBack
    var myhome = MyHome
    var i=0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        //mqtt.publishMessage("onCreate","debug")
        setContentView(R.layout.activity_lights_controller)
        try {
            mqtt.Connect()
            var swCamMica:SwitchMaterial = findViewById(R.id.camMicaSw)
            swCamMica.isChecked = false
            var sbCamMica:SeekBar = findViewById(R.id.camMicaSeekBar)
            swCamMica.setOnClickListener(View.OnClickListener { sendMessageWithDetails("swCamMica_${swCamMica.isChecked}_${sbCamMica.progress}","camMica")})
            var swCamMedie:SwitchMaterial = findViewById(R.id.camMedieSw)
            swCamMedie.isChecked = false
            var sbCamMedie:SeekBar = findViewById(R.id.camMedieSeekBar)
            swCamMedie.setOnClickListener(View.OnClickListener { sendMessageWithDetails("swCamMica_${swCamMedie.isChecked}_${sbCamMedie.progress}","camMedie")})

        }
        catch (ex:Exception) {
            Log.e(ContentValues.TAG, "mqtt onCreate err, ${ex.toString()}")
        }
    }

    private fun sendMessageWithDetails(message:String,topic:String) {
        //var message =view.toString()
        mqtt.publishMessage(message,topic)
        var tv:TextView = findViewById(R.id.textView)
        tv.setText( "Update!")
    }

    fun button1clicked(view: LightsController) {
        var tv:TextView = findViewById(R.id.textView)
        tv.setText( mqtt.Connect().toString())
        //mqtt.publishMessage("onClick1","debug")
        var timer = Timer()
        timer.schedule(timerTask { updateTxt() },1000,5000)
    }


    fun schedule(
        time: Date,
        period: Long,
        action: TimerTask
    ) {
        updateTxt()
    }

    fun updateTxt() {
        var tv:TextView = findViewById(R.id.textView)
        tv.setText( i.toString())
        mqtt.publishMessage("test","android")

        i++
    }
}