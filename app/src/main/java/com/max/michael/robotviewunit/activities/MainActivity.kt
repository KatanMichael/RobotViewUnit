package com.max.michael.robotviewunit.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.max.michael.robotviewunit.R
import com.max.michael.robotviewunit.models.MotorRequest
import com.max.michael.robotviewunit.models.Request
import com.max.michael.robotviewunit.models.RobotRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.*


class MainActivity : AppCompatActivity()
{

    val list = ArrayList<RobotRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<MotorRequest>()


        connect_btn.setOnClickListener()
        {

            val ipAdress = ip_ET.text.toString()
            val gson = Gson()

            val request = Request(UUID.randomUUID().toString(), list,list.size)

            val jsonFile = gson.toJson(request)

            Log.d("JsonFile",jsonFile)

            GlobalScope.launch(Dispatchers.Default) {
                sendJsonToRobot(ipAdress,jsonFile)
            }
        }

        addMotorBtn.setOnClickListener()
        {
            val angle = motorAngle_ET.text.toString()

            if(angle != "")
            {
                var delayAmount : String = "0"
                if(delay_ET.text.toString() == "")
                {
                    delayAmount = "0"
                }else
                {
                    delayAmount = delay_ET.text.toString()
                }

                list.add(MotorRequest(getRandomID(),"A","100",angle,delayAmount))
                refreshSequenceTv("Motor $angle")
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

        val objectOutputStream = ObjectOutputStream(socket.getOutputStream())

        val stringTest: String = "123"

        objectOutputStream.writeObject(jsonFile)



        objectOutputStream.flush()
        objectOutputStream.close()

        socket.close()


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
