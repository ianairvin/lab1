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
    val switch = mutableStateOf(0)
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

    val listRelativeError : MutableState<MutableList<Point>> by lazy {
        mutableStateOf(mutableListOf())
    }

    fun calculation(){
        if(h.value != "") {
            returnValues = program(h.value.toDouble())
            listX1.value = returnValues.pointsX1
            listX2.value = returnValues.pointsX2
            listX3.value = returnValues.pointsX3
            listX4.value = returnValues.pointsX4
            listX5.value = returnValues.pointsX5
            answer.value = String.format("%.3f", returnValues._x4).replace(",", ".")
            calculationError()
        }
    }

    private fun calculationError(){
        switch.value = 2
        val temp = programCalculationOnlyResult(h.value.toDouble() / 2)
        answerH2.value = String.format("%.3f", temp).replace(",", ".")
        h2.value = (h.value.toDouble() / 2).toString()
        relativeError.value =
            String.format("%.3f", abs((answerH2.value.toDouble() - answer.value.toDouble()) / answerH2.value.toDouble() ) * 100.0) + "%"
    }

    fun autoCalculation(){
        var step = 1.0
        var relativeError: Double
        do {
            val hResult = programCalculationOnlyResult(step)
            val h2Result = programCalculationOnlyResult(step / 2)
            relativeError = abs((h2Result - hResult) / h2Result) * 100
            step /= 2
        } while (relativeError > 1)
        h.value = (step * 2).toString()
        switch.value = 2
        calculation()
    }

    fun graphRelativeErrorAndStep(){
        var step = 1.0
        var relativeError: Double = 5.0
        while (relativeError > 1) {
            val hResult = programCalculationOnlyResult(step)
            val h2Result = programCalculationOnlyResult(step / 2)
            relativeError = abs((h2Result - hResult) / h2Result) * 100
            listRelativeError.value.add(Point(step.toFloat() * 10, relativeError.toFloat()))
            step /= 2
        }
        switch.value = 1
        listRelativeError.value.sortBy { it.x }
    }
}