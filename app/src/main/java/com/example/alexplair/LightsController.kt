package com.example.alexplair

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
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
        var myCallBack = MyCallBack

        try {
            mqtt.Connect()
            var swCamMica:SwitchMaterial = findViewById(R.id.camMicaSw)
            swCamMica.isChecked = false
            var sbCamMica:SeekBar = findViewById(R.id.camMicaSeekBar)
            sbCamMica.progress = 30
            swCamMica.setOnClickListener(View.OnClickListener {
                //sendMessageWithDetails("swCamMica_${swCamMica.isChecked}_${sbCamMica.progress}","camMica_Lights")
                sendMessageWithDetails("${swCamMica.isChecked}","toCameraMica")
            })
            var swCamMedie:SwitchMaterial = findViewById(R.id.camMedieSw)
            swCamMedie.isChecked = false
            var sbCamMedie:SeekBar = findViewById(R.id.camMedieSeekBar)
            sbCamMedie.progress=30
            var myListener = MyListener
            sbCamMedie.setOnSeekBarChangeListener(myListener)
            swCamMedie.setOnClickListener(View.OnClickListener { sendMessageWithDetails("swCamMedie_${swCamMedie.isChecked}_${sbCamMedie.progress}","camMedie_Lights ")})

            var timer = Timer()
            timer.schedule( timerTask {
                runOnUiThread(Runnable() {
                    if(myCallBack.arrived) {
                        myCallBack.arrived = false
                        updateTxt("Temp: ${myCallBack.myhome.temperature.toString()} \u2103" ,false)
                    }

                })



                                     },
                1000,
                5000)

        }
        catch (ex:Exception) {
            Log.e(ContentValues.TAG, "mqtt onCreate err, ${ex.toString()}")
        }
    }

    private fun sendMessageWithDetails(message:String,topic:String) {
        //var message =view.toString()
        var tv:TextView = findViewById(R.id.textView)
        var text = message
        var sent = false
        try {
            sent = mqtt.publishMessage(message,topic)
            tv.setText( "Sent: $sent, $message - $topic")
        }
        catch (ex:Exception) {
            text = ex.toString()
            tv.setText( "$text, Sent:$sent")
        }


    }

    fun button1clicked(view: LightsController) {
        var tv:TextView = findViewById(R.id.textView)
        tv.setText( mqtt.Connect().toString())
        //mqtt.publishMessage("onClick1","debug")

    }


    fun updateTxt(text:String,append:Boolean = false) {
        var tv:TextView = findViewById(R.id.camMicaTemp)
        var displayText = text
        if (append)
            displayText = "${tv.text} \n $text"
        tv.setText( displayText)
        //mqtt.publishMessage("test","android")

        //i++
    }
}

object MyListener : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.d("DEBUG","mqtt change bar")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.d("DEBUG","mqtt change bar start tracking")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.d("DEBUG","mqtt change bar stop tracking")

    }
}


