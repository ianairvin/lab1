package ru.simpleplanner.lab1

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import kotlin.math.abs

class ViewModel : ViewModel (
) {
    var h = mutableStateOf("")
    var h2 = mutableStateOf("")
    var answer = mutableStateOf("")
    var answerH2 = mutableStateOf("")
    var relativeError = mutableStateOf("")
    var switchForVisibleText = mutableStateOf(false)
    lateinit var returnValues : ReturnValues
    val listX1 : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    val listX2 : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    val listX3 : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    val listX4 : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    val listX5 : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    fun calculation(){
        switchForVisibleText.value = true
        returnValues = program(h.value.toDouble())
        listX1.value = returnValues.pointsX1
        listX2.value = returnValues.pointsX2
        listX3.value = returnValues.pointsX3
        listX4.value = returnValues.pointsX4
        listX5.value = returnValues.pointsX5
        answer.value = returnValues._x4.toString()
        calculationError()
    }

    private fun calculationError(){
        val temp = programCalculationOnlyResult(h.value.toDouble() / 2)
        answerH2.value = temp.toString()
        h2.value = (h.value.toDouble() / 2).toString()
        relativeError.value = (abs((temp - answer.value.toDouble()) / temp) * 100).toString() + "%"
    }

    fun autoCalculation(){
        switchForVisibleText.value = true
        var tempH = 1.0
        var relativeErrorTemp = 5.0
        while(relativeErrorTemp > 1){
            val hResult = programCalculationOnlyResult(tempH)
            val h2Result = programCalculationOnlyResult(tempH / 2)
            relativeErrorTemp = abs((h2Result - hResult) / h2Result) * 100
            tempH /= 2
        }
        h.value = tempH.toString()
        calculation()
    }
}