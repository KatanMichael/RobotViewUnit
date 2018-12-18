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


        val ipAdress = ip_ET.text.toString()

        connect_btn.setOnClickListener()
        {

            val thread = Thread {
                val socket = Socket(ipAdress, 12345)
                val outPutStream = ObjectOutputStream(socket.getOutputStream())
                val gson = Gson()

                val toJson = gson.toJson(list)

                try
                {
                    outPutStream.writeObject(list)
                }catch (e: IOException)
                {
                    e.printStackTrace()
                }
            }

            thread.start()

        }


    }

    inner class myAsync : AsyncTask<Void?, Void?, Int>() {

        val ipAdress = (ip_ET as TextView).text.toString()

        override fun doInBackground(vararg params: Void?): Int {
            val socket = Socket(ipAdress, 12345)
            val outPutStream = ObjectOutputStream(socket.getOutputStream())
            val gson = Gson()

            val toJson = gson.toJson(list)

            outPutStream.writeObject(list)


            return 1
        }

    }
}
