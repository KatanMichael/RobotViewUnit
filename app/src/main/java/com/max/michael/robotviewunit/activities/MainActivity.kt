package com.max.michael.robotviewunit.activities

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.gson.Gson
import com.max.michael.robotviewunit.R
import com.max.michael.robotviewunit.R.id.async
import com.max.michael.robotviewunit.models.DelayRequest
import com.max.michael.robotviewunit.models.MotorRequest
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

        list.add(MotorRequest("1", "2", "200", "40"))
        list.add(DelayRequest("2", 200))
        list.add(MotorRequest("1", "2", "3", "60"))
        list.add(DelayRequest("2", 200))
        list.add(MotorRequest("1", "2", "100", "9000"))



        connect_btn.setOnClickListener()
        {

            val ipAdress = ip_ET.text.toString()
            val gson = Gson()
            val angle = motorAngle_ET.text.toString()
            val request = MotorRequest(UUID.randomUUID().toString(),"A","100",angle)
            val jsonFile = gson.toJson(request)

            GlobalScope.launch(Dispatchers.Default) {
                sendJsonToRobot(ipAdress,jsonFile)
            }
        }


    }

    //Transfer To Controller
    fun sendJsonToRobot(ipAddress: String, jsonFile: String)
    {
        val socket = Socket(ipAddress,1234)

        val inputString = ObjectInputStream(socket.getInputStream())
        val objectOutputStream = ObjectOutputStream(socket.getOutputStream())

        objectOutputStream.writeObject(jsonFile)

        //For Changing UI From The Network Thread

//        val readObject = inputString.readObject() as String
//        GlobalScope.launch(Dispatchers.Main) {
//            inputTv.text = readObject
//        }
    }
}
