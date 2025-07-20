package com.amit.basic.classes_examples

/*
1. These are kind of enums and used to bundle values
2. it has child class which inherits from sealed class.
3. We can get instance of child class.
4. in enum, we have to define properties of child on compile time but in sealed class,
properties can be defined on runtime and each child class can have different property.
*/
sealed class NetworkResult {
    data class Success(val data: String): NetworkResult()
    data class Error(val throwable: Throwable): NetworkResult()
    data object Empty: NetworkResult()
}