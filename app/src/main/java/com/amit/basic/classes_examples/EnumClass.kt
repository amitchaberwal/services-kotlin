package com.amit.basic.classes_examples

/*
EnumClass.entries.forEach {
    print(it.name)
}
*/

enum class EnumClass {
    OK,
    NOT_FOUND,
    SERVER_ERROR,
}

/*
val response = RESPONSE.OK
print(response.toResponseString())
*/

enum class RESPONSE(val code: Int, val msg:String){
    OK(code = 200, msg = "success"),
    NOT_FOUND(code = 400, msg = "not found"),
    SERVER_ERROR(code = 500, msg="internal error");

    fun toResponseString(): String{
        return "$code: $msg"
    }
}