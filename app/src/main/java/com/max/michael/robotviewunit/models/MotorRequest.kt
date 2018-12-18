package com.max.michael.robotviewunit.models

class MotorRequest(val id:String, val port: String, val speed:String, val angle: String) : RobotRequest()
{
    init {
        type = "Motor"
    }
}