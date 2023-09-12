package ru.simpleplanner.lab1

import android.util.Log
import co.yml.charts.common.model.Point
import kotlin.math.*

class ReturnValues(){
    val pointsX1 = ArrayList<Point>()
    val pointsX2 = ArrayList<Point>()
    val pointsX3 = ArrayList<Point>()
    val pointsX4 = ArrayList<Point>()
    val pointsX5 = ArrayList<Point>()
    var _x4 = 0.0
}

fun program(h: Double) : ReturnValues {
    val g = 9.81
    val p = 100000
    val a = 0.6
    val m = 2000
    val u = 10
    val Cx = 0.03
    val Cy = 0.005
    val m1 = 0.07
    val m2 = 0.01
    val T = 12.0
    var _x1 = 1700.0
    var _x2 = 0.08
    var _x3 = 0.0
    var _x4 = 100.0
    var _x5 = 0.8
    var _X1: Double = 0.0
    var _X2: Double = 0.0
    var _X3: Double = 0.0
    var _X4: Double = 0.0
    var _X5: Double = 0.0

    val returnValues = ReturnValues()

    var t = 0.0
    var t_prev = 0.0

    returnValues.pointsX1.add(Point(t.toFloat(), _x1.toFloat()))
    returnValues.pointsX2.add(Point(t.toFloat(), _x2.toFloat()))
    returnValues.pointsX3.add(Point(t.toFloat(), _x3.toFloat()))
    returnValues.pointsX4.add(Point(t.toFloat(), _x4.toFloat()))
    returnValues.pointsX5.add(Point(t.toFloat(), _x5.toFloat()))

    do {
        _X1 = _x1 + (-g * sin(_x2) + (p - a * Cx * _x1 * _x1) / (m - u * t)) * h
        _X2 = _x2 + (-g + (p * sin(_x5 - _x2) + a * Cy * _x1 * _x1) / (m - u * t)) / _x1 * h
        _X3 = _x3 + (m1 * a * (_x2 - _x5) * _x1 * _x1 - m2 * a * _x1 * _x1 * _x3) / (m - u * t) * h
        _X4 = _x4 + _x1 * sin(_x2) * h
        _X5 = _x5 + _x3 * h

        _x1 = _X1
        _x2 = _X2
        _x3 = _X3
        _x4 = _X4
        _x5 = _X5

        t += h
        if(t - t_prev >= 0.01) {
            returnValues.pointsX1.add(Point(t.toFloat(), _x1.toFloat()))
            returnValues.pointsX2.add(Point(t.toFloat(), _x2.toFloat()))
            returnValues.pointsX3.add(Point(t.toFloat(), _x3.toFloat()))
            returnValues.pointsX4.add(Point(t.toFloat(), _x4.toFloat()))
            returnValues.pointsX5.add(Point(t.toFloat(), _x5.toFloat()))
            t_prev = t
        }
    } while (t <= T)
    returnValues._x4 = _x4
    return returnValues
}

fun programCalculationOnlyResult(h: Double) : Double {
    val g = 9.81
    val p = 100000
    val a = 0.6
    val m = 2000
    val u = 10
    val Cx = 0.03
    val Cy = 0.005
    val m1 = 0.07
    val m2 = 0.01
    val T = 12.0
    var _x1 = 1700.0
    var _x2 = 0.08
    var _x3 = 0.0
    var _x4 = 100.0
    var _x5 = 0.8
    var _X1: Double = 0.0
    var _X2: Double = 0.0
    var _X3: Double = 0.0
    var _X4: Double = 0.0
    var _X5: Double = 0.0

    var t = 0.0

    while(t <= T){
        _X1 = _x1 + (-g * sin(_x2) + (p - a * Cx * _x1 * _x1) / (m - u * t)) * h
        _X2 = _x2 + (-g + (p * sin(_x5 - _x2) + a * Cy * _x1 * _x1) / (m - u * t)) / _x1 * h
        _X3 = _x3 + (m1 * a * (_x2 - _x5) * _x1 * _x1 - m2 * a * _x1 * _x1 * _x3) / (m - u * t) * h
        _X4 = _x4 + _x1 * sin(_x2) * h
        _X5 = _x5 + _x3 * h

        _x1 = _X1
        _x2 = _X2
        _x3 = _X3
        _x4 = _X4
        _x5 = _X5

        t += h
    }
    return _x4
}