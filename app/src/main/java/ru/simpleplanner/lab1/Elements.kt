package ru.simpleplanner.lab1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.axis.Gravity
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlin.math.roundToInt

@Composable
fun Step(
    h: MutableState<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = h.value,
            onValueChange = { h.value = it },
            label = { Text(text = "Введите шаг") },
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )
    }
}

@Composable
fun Answer(
    h: MutableState<String>,
    h2: MutableState<String>,
    answer: MutableState<String>,
    answerH2: MutableState<String>,
    relativeError: MutableState<String>,
    switch: MutableState<Int>,
    calculation: () -> Unit,
    autoCalculation: () -> Unit,
    graphRelativeErrorAndStep: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row() {
            OutlinedButton(
                onClick = calculation,
                content = {
                    Text("Рассчитать")
                }
            )
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedButton(
                onClick = autoCalculation,
                content = {
                    Text("Автомат. рассчет шага")
                }
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = graphRelativeErrorAndStep,
                content = {
                    Text("Зависимость отн.погрешности от шага")
                }
            )
        }
        if(switch.value == 2) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "x4 при шаге h = " + answer.value)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "h/2 = " + h2.value)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "x4 при шаге h/2 = " + answerH2.value)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "δ = " + relativeError.value)
        }
    }
}

@Composable
fun Graphics(
    listX1: MutableList<Point>,
    listX2: MutableList<Point>,
    listX3: MutableList<Point>,
    listX4: MutableList<Point>,
    listX5: MutableList<Point>,
    listErrorAndStep: MutableList<Point>,
    switch: MutableState<Int>
){
    if(switch.value == 1) {
        val listChartDataErrorAndStep = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listErrorAndStep,
                        LineStyle(color = Color.DarkGray, width = 10.0f),
                        IntersectionPoint(color = Color.DarkGray, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x / 10.0)} "
                                xLabel
                            }
                        )
                    )
                ),
            ),
            xAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> (i / 9).toString()
                }.build(),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listErrorAndStep.size)
                .labelAndAxisLinePadding(16.dp)
                .shouldDrawAxisLineTillEnd(flag = false)
                .labelData { i ->
                    val temp = listErrorAndStep.maxOf { it.y } / listErrorAndStep.size
                    String.format("%.2f", (i * temp))
                }.build(),
            gridLines = GridLines(),
            backgroundColor = Color.White
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text("зависимость относительной погрешности (ось Ох) от шага (ось Оу):")
            Spacer(modifier = Modifier.padding(8.dp))
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                lineChartData = listChartDataErrorAndStep
            )
        }
    }

    if(switch.value == 2) {
        val lineChartDataX1 = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listX1,
                        LineStyle(color = Color.Red, width = 10.0f),
                        IntersectionPoint(color = Color.Red, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x)} "
                                val yLabel = "y : ${String.format("%.3f", y)}"
                                "$xLabel $yLabel"
                            }
                        )
                    )
                ),
            ),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listX1.size)
                .labelAndAxisLinePadding(0.dp)
                .labelData { i -> " "}
                .build(),
            xAxisData = AxisData.Builder()
                .steps(12)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> i.toString()}
                .build(),
            gridLines = GridLines(
                enableHorizontalLines = false
            ),
            backgroundColor = Color.White
        )
        val lineChartDataX2 = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listX2,
                        LineStyle(color = Color.Green, width = 10.0f),
                        IntersectionPoint(color = Color.Green, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x)} "
                                val yLabel = "y : ${String.format("%.3f", y)}"
                                "$xLabel $yLabel"
                            }
                        )
                    )
                ),
            ),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listX2.size - 1)
                .labelAndAxisLinePadding(0.dp)
                .labelData { i -> " "}
                .build(),
            xAxisData = AxisData.Builder()
                .steps(12)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> i.toString()}
                .build(),
            gridLines = GridLines(
                enableHorizontalLines = false
            ),
            backgroundColor = Color.White
        )

        val lineChartDataX3 = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listX3,
                        LineStyle(color = Color.Blue, width = 10.0f),
                        IntersectionPoint(color = Color.Blue, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x)} "
                                val yLabel = "y : ${String.format("%.3f", y)}"
                                "$xLabel $yLabel"
                            }
                        )
                    )
                ),
            ),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listX3.size - 1)
                .labelAndAxisLinePadding(0.dp)
                .labelData { i -> " "}
                .build(),
            xAxisData = AxisData.Builder()
                .steps(12)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> i.toString()}
                .build(),
            gridLines = GridLines(
                enableHorizontalLines = false
            ),
            backgroundColor = Color.White
        )

        val lineChartDataX4 = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listX4,
                        LineStyle(color = Color.Yellow, width = 10.0f),
                        IntersectionPoint(color = Color.Yellow, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x)} "
                                val yLabel = "y : ${String.format("%.3f", y)}"
                                "$xLabel $yLabel"
                            }
                        )
                    )
                ),
            ),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listX4.size - 1)
                .labelAndAxisLinePadding(0.dp)
                .labelData { i -> " "}
                .build(),
            xAxisData = AxisData.Builder()
                .steps(12)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> i.toString()}
                .build(),
            gridLines = GridLines(
                enableHorizontalLines = false
            ),
            backgroundColor = Color.White
        )

        val lineChartDataX5 = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = listX5,
                        LineStyle(color = Color.Magenta, width = 10.0f),
                        IntersectionPoint(color = Color.Magenta, radius = 4.dp),
                        SelectionHighlightPoint(radius = 5.dp),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(
                            popUpLabel = { x, y ->
                                val xLabel = "x : ${String.format("%.3f", x)} "
                                val yLabel = "y : ${String.format("%.3f", y)}"
                                "$xLabel $yLabel"
                            }
                        )
                    )
                ),
            ),
            yAxisData = AxisData.Builder()
                .backgroundColor(Color.White)
                .steps(listX5.size - 1)
                .labelAndAxisLinePadding(0.dp)
                .labelData { i -> " "}
                .build(),
            xAxisData = AxisData.Builder()
                .steps(12)
                .backgroundColor(Color.White)
                .labelAndAxisLinePadding(8.dp)
                .labelData { i -> i.toString()}
                .build(),
            gridLines = GridLines(
                enableHorizontalLines = false
            ),
            backgroundColor = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("x1:")
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartDataX1
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text("x2:")
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartDataX2
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text("x3:")
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartDataX3
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text("x4:")
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartDataX4
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text("x5:")
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartDataX5
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}