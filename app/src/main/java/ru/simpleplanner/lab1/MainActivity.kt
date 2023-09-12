package ru.simpleplanner.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.simpleplanner.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ViewModel by viewModels()
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(32.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Step(viewModel.h)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Answer(
                            viewModel.h,
                            viewModel.h2,
                            viewModel.answer,
                            viewModel.answerH2,
                            viewModel.relativeError,
                            viewModel.switchForVisibleText,
                            { viewModel.calculation() },
                            { viewModel.autoCalculation() }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Graphics(
                            viewModel.listX1.value,
                            viewModel.listX2.value,
                            viewModel.listX3.value,
                            viewModel.listX4.value,
                            viewModel.listX5.value
                        )
                    }
                }
            }
        }
    }
}
