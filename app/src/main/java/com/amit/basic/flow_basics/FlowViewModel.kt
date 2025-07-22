package com.amit.basic.flow_basics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FlowViewModel: ViewModel() {

    // ************* Basic Flow Collector *************

    // create flow
    val countDownFlow = flow <Int>{
        var count = 10
        emit(count)
        while(count> 0){
            delay(1000L)
            count --
            emit(count)
        }
    }

    init {
        collectAsFlow()
    }

    // collect flow
    private fun collectAsFlow(){
        viewModelScope.launch {
            countDownFlow.collect {
                print("Current time: $it")
            }

//            countDownFlow.collectLatest {
//                delay(1500L)
//                print("Current time: $it")
//            }
            // In this code if delayed and than new stream come then it will cancel current block and start new block for incoming stream
            // in this case only 0 will be printed as all will be cancelled
        }

    }

    // ************* Basic Flow Operator *************
    private fun filterFlow(){
        viewModelScope.launch {
            countDownFlow.filter {
                it % 2 == 0
                // only return values
            }.map {
                it*it
                // square the values
            }.collect {
                print("Even Values: $it")
            }
        }
    }


    // ************* State Flow *************
    private val _stateFlow = MutableStateFlow<Int>(0)
    val state = _stateFlow.asStateFlow()

    //update state
    fun updateState(){
        _stateFlow.value += 1

        // get in ui
//        val state = viewModel.stateFlow.collectAsState(0)
    }


    //************* Shared Flow *************
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    // emit sharedFlow
    fun emitSharedFlowSquareNumber(number: Int){
        viewModelScope.launch {
            _sharedFlow.emit(number*number)
        }
    }
    // collect sharedFlow
    fun collectSharedFlowEvent(){

        viewModelScope.launch {
            _sharedFlow.collect{
                delay(2000L)
                print("First Collector: $it")
            }
        }

        viewModelScope.launch {
            _sharedFlow.collect{
                delay(3000L)
                print("Second Collector: $it")
            }
        }

        // Always define collector before sending an event
        emitSharedFlowSquareNumber(4)

        // on ui side, it can be collected in LaunchedEffect as it is a coroutine scope
    }


    // ************* State flow operation *************
    private val _userState = MutableStateFlow<User?>(null)

    private val _postState = MutableStateFlow<List<Post>>(emptyList())

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState = _profileState.asStateFlow()

    fun combineFlowState(){
        _userState.combine(_postState){ user, post ->
            _profileState.value = _profileState.value?.copy(
                user = user,
                postList = post
            )?: _profileState.value
        }.launchIn(viewModelScope)
    }


}