package com.amit.basic.classes_examples

/*
* these are used to hold a single permitive value
* Usage: val email = Email("amit@gmail.com")
* These are memory efficient than a data class for a single value
* */
@JvmInline
value class Email(val email: String) {
    init {
        if(!email.contains("@")){
            throw IllegalArgumentException("Invalid Email")
        }
    }
}