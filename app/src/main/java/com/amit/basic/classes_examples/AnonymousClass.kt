package com.amit.basic.classes_examples

/*These classes are used to create object that are inherited from a class or abstract class
* we can also create any dataType which is object(which is a type of abstract class)
*
val myObject: object : ABSensor() {
            override val name: String
                get() = ""

            override fun onValue(onNewValue: (Int) -> Unit) {
                onNewValue(5)
            }

        }
* */
abstract class ABSensor {
    abstract val name: String
    abstract fun onValue(onNewValue: (Int) -> Unit)
}

object DataUtil: ABSensor(){
    override val name: String
        get() = "Name"

    override fun onValue(onNewValue: (Int) -> Unit) {

        onNewValue(4)
    }

}