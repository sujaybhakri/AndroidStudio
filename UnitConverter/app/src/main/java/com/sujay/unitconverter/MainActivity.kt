package com.sujay.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sujay.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Millimeters") }
    var outputUnit by remember { mutableStateOf("Millimeters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var iConversionFactor = remember { mutableStateOf(0.001) }
    var oConversionFactor= remember{mutableStateOf(0.001)}

    fun convertUnits(){
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble*iConversionFactor.value*100.0/oConversionFactor.value).roundToInt()/100.0
        outputValue=result.toString()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //ui elements will be stacked on top of each other
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = { inputValue = it },label={Text("Enter Value")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            //ui elements will be stacked on top of each other
            Box {
                Button(onClick = {iExpanded=true}) {
                    Text("$inputUnit")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded=false}) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {iExpanded=false; inputUnit="Millimeters"; iConversionFactor.value = 0.001;convertUnits()})
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {iExpanded=false; inputUnit="Centimeters"; iConversionFactor.value = 0.01;convertUnits() })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {iExpanded=false; inputUnit="Meters"; iConversionFactor.value = 1.0;convertUnits()})
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {iExpanded=false; inputUnit="Feet"; iConversionFactor.value = 0.00328084;convertUnits() })
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpanded=true}) {
                    Text("$outputUnit")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded=false }) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {oExpanded=false; outputUnit="Millimeters"; oConversionFactor.value = 0.001;convertUnits() })
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {oExpanded=false; outputUnit="Centimeters"; oConversionFactor.value = 0.01;convertUnits() })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {oExpanded=false; outputUnit="Meters"; oConversionFactor.value = 1.0;convertUnits() })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {oExpanded=false; outputUnit="Feet"; oConversionFactor.value = 0.00328084;convertUnits() })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Result:$outputValue $outputUnit", style = MaterialTheme.typography.headlineMedium)
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}


