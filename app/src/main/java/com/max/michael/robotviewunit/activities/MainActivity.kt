package com.max.michael.robotviewunit.activities

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.TextView
import com.google.gson.Gson
import com.max.michael.robotviewunit.R
import com.max.michael.robotviewunit.R.id.async
import com.max.michael.robotviewunit.models.DelayRequest
import com.max.michael.robotviewunit.models.MotorRequest
import com.max.michael.robotviewunit.models.Request
import com.max.michael.robotviewunit.models.RobotRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ObjectOutputStream
import java.net.Socket
import kotlinx.coroutines.*
import java.io.IOException
import java.io.ObjectInputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    val list = ArrayList<RobotRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<RobotRequest>()


        connect_btn.setOnClickListener()
        {

            val ipAdress = ip_ET.text.toString()
            val gson = Gson()

            val request = Request(UUID.randomUUID().toString(), list,list.size)

            val jsonFile = gson.toJson(request)

            GlobalScope.launch(Dispatchers.Default) {
                sendJsonToRobot(ipAdress,jsonFile)
            }
        }

        addMotorBtn.setOnClickListener()
        {
            val angle = motorAngle_ET.text.toString()

            if(angle != "")
            {
                list.add(MotorRequest(getRandomID(),"A","100",angle))
                refreshSequenceTv("Motor $angle")
                motorAngle_ET.text.clear()
            }
        }

        addDelayBtn.setOnClickListener()
        {
            val amount = delay_ET.text.toString()

            if(amount != "")
            {
                list.add(DelayRequest(getRandomID(),Integer.parseInt(amount)))
                refreshSequenceTv("Delay $amount")
                motorAngle_ET.text.clear()
            }
        }

    }//onCreate

    private fun refreshSequenceTv(type: String)
    {
        var text = sequenceTv.text.toString()

        text+="$type ->"
        sequenceTv.text = text
    }

    //Transfer To Controller
    fun sendJsonToRobot(ipAddress: String, jsonFile: String)
    {
        val socket = Socket(ipAddress,12345)

        val inputString = ObjectInputStream(socket.getInputStream())
        val objectOutputStream = ObjectOutputStream(socket.getOutputStream())
        objectOutputStream.writeObject(jsonFile)




        GlobalScope.launch(Dispatchers.Main) {
            sequenceTv.text = socket.toString()
        }
        //For Changing UI From The Network Thread

//        val readObject = inputString.readObject() as String
//        GlobalScope.launch(Dispatchers.Main) {
//            inputTv.text = readObject
//        }
    }
}

fun getRandomID(): String{
    return UUID.randomUUID().toString()
}
