package com.amit.basic.classes_examples

abstract class Sensor {
    abstract val name: String
    abstract fun onValue(onNewValue: (Int) -> Unit)
}

class HeartSensor : Sensor(){
    override val name: String
        get() = "Heart Tracker"

    override fun onValue(onNewValue: (Int) -> Unit) {
        onNewValue(2)
    }

}
open class LightSensor : Sensor(){
    override val name: String
        get() = "Light"

    override fun onValue(onNewValue: (Int) -> Unit) {
        onNewValue(2)
    }
}

class CustomLightSensor: LightSensor(){
//this class is not forced to implement abstract methods
}