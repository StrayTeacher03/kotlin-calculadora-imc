package com.example.calculadoradeimc

import android.R.attr.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.ui.theme.CalculadoraDeIMCTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraDeIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculadora(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Calculadora(modifier: Modifier = Modifier) {
    var imc by remember { mutableStateOf(calcularIMC(0.0, 0.0, false)) }
    var valorPesoInput by remember {mutableStateOf("")}
    var valorAlturaInput by remember {mutableStateOf("")}
    var arredondarIMC by remember { mutableStateOf(false)}
    var classe: Boolean = true
    val valorPeso = valorPesoInput.toDoubleOrNull() ?: 0.0
    val valorAltura = valorAlturaInput.toDoubleOrNull() ?: 0.0

    Column(modifier = Modifier.padding(15.dp)) {
        Text(
            text = "Calculadora de IMC",
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
    Column(modifier = Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            text = "peso",

        )
        EditNumberField(label = "Seu Peso:", value = valorPesoInput, onValueChanged = { valorPesoInput = it }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())

        Text(
            text = "altura"
        )
        EditNumberField(label = "Sua Altura::", value = valorAlturaInput, onValueChanged = { valorAlturaInput = it }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done), modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())

        Arredondar(arredondar = arredondarIMC, onArredondarChanged = {arredondarIMC = it}, modifier = Modifier.padding(bottom = 32.dp))
        Button(onClick = {imc = calcularIMC(valorPeso, valorAltura, arredondarIMC)}) {
            Text(
                text ="Calcular"
            )
        }
        Text(
            text = "IMC calculado: $imc",
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = classificar(imc, classe),
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun Arredondar(arredondar: Boolean, onArredondarChanged: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Deseja arredondar seu IMC?"
        )
        Switch(checked = arredondar, onCheckedChange = onArredondarChanged)
    }

}

object peso{
    const val apeso = 18.5
    const val peson = 24.9
    const val speso = 29.9
}

fun calcularIMC(peso: Double, altura: Double, arredondar: Boolean): Double {

    var imc = peso/(altura*altura)
    if (arredondar) {
        imc = ceil(imc)
    }
    return imc
}

fun classificar(imc: Double, classe: Boolean): String = when (classe) {
        (peso.apeso>=imc) -> "Abaixo do peso"
        (peso.peson>=imc) -> "Peso normal"
        (peso.speso>=imc) -> "Sobrepeso"
        (peso.speso<imc) -> "Obesidade"
        else -> "Valor desconhecido"
    }

@Composable
fun EditNumberField(label: String, value: String, onValueChanged: (String) -> Unit, keyboardOptions: KeyboardOptions, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = {Text(label)},
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}


