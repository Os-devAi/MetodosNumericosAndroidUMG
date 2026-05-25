package com.example.metodosnumericos.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metodosnumericos.api.RetrofitInstance
import com.example.metodosnumericos.model.HistorialItem
import com.example.metodosnumericos.model.Punto
import com.example.metodosnumericos.model.PuntosRequest
import com.example.metodosnumericos.model.ResultadoResponse
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class MainViewModel : ViewModel() {

    // =====================================================
    // STATES
    // =====================================================

    var resultado by mutableStateOf<ResultadoResponse?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    // =====================================================
    // UI STATES
    // =====================================================

    var metodoSeleccionado by mutableStateOf("lagrange")

    var historial by mutableStateOf<List<HistorialItem>>(
        emptyList()
    )
        private set

    var puntos by mutableStateOf<List<Punto>>(
        listOf(
            Punto(1.0, 2.0),
            Punto(2.0, 4.0),
            Punto(3.0, 8.0)
        )
    )
        private set

    // =====================================================
    // EVALUACION
    // =====================================================

    var xEvaluar by mutableStateOf("")

    var yEvaluado by mutableStateOf<Double?>(null)
        private set

    var puntosGrafica by mutableStateOf<List<Punto>>(emptyList())
        private set

    // =====================================================
    // DROPDOWN
    // =====================================================

    fun onMetodoChange(
        metodo: String
    ) {

        metodoSeleccionado = metodo
    }

    // =====================================================
    // TABLA DINAMICA
    // =====================================================

    fun agregarFila() {

        puntos =
            puntos + Punto(0.0, 0.0)
    }

    fun eliminarFila(index: Int) {

        if (puntos.size <= 2) return

        puntos =
            puntos.toMutableList().also {
                it.removeAt(index)
            }
    }

    fun actualizarX(
        index: Int,
        value: String
    ) {

        val lista =
            puntos.toMutableList()

        lista[index] =
            lista[index].copy(
                x = value.toDoubleOrNull() ?: 0.0
            )

        puntos = lista
    }

    fun actualizarY(
        index: Int,
        value: String
    ) {

        val lista =
            puntos.toMutableList()

        lista[index] =
            lista[index].copy(
                y = value.toDoubleOrNull() ?: 0.0
            )

        puntos = lista
    }

    // =====================================================
    // LIMPIAR
    // =====================================================

    fun reiniciar() {
        puntos = listOf(
            Punto(1.0, 2.0),
            Punto(2.0, 4.0),
            Punto(3.0, 8.0)
        )
        resultado = null
        error = ""
        xEvaluar = ""
        yEvaluado = null
        puntosGrafica = emptyList()
    }

    fun limpiarError() {

        error = ""
    }

    fun limpiarResultado() {

        resultado = null
    }

    private fun generarPuntosGrafica(polinomio: String) {
        try {
            val expr = polinomio.replace("**", "^").replace(" ", "")
            val compiledExpr = ExpressionBuilder(expr).variable("x").build()

            val minX = puntos.minOf { it.x }
            val maxX = puntos.maxOf { it.x }
            val range = maxX - minX
            val step = if (range == 0.0) 0.1 else range / 50.0

            val nuevosPuntos = mutableListOf<Punto>()
            var currentX = minX - (range * 0.1) // Un poco antes del inicio
            val endX = maxX + (range * 0.1) // Un poco después del final

            while (currentX <= endX) {
                val y = compiledExpr.setVariable("x", currentX).evaluate()
                nuevosPuntos.add(Punto(currentX, y))
                currentX += step
            }
            puntosGrafica = nuevosPuntos
        } catch (e: Exception) {
            puntosGrafica = emptyList()
        }
    }

    // =====================================================
    // EVALUAR POLINOMIO
    // =====================================================

    fun evaluarPolinomio() {

        try {

            val x =
                xEvaluar.toDouble()

            val exprOriginal =
                resultado?.polinomio ?: return

            // =============================================
            // CONVERTIR SINTAXIS SYMPY -> EXP4J
            // =============================================

            val expr =
                exprOriginal
                    .replace("**", "^")
                    .replace(" ", "")

            val resultadoEval =
                ExpressionBuilder(expr)
                    .variable("x")
                    .build()
                    .setVariable("x", x)
                    .evaluate()

            yEvaluado = resultadoEval

        } catch (e: Exception) {

            yEvaluado = null

            error =
                "No se pudo evaluar el polinomio"
        }
    }

    // =====================================================
    // API
    // =====================================================

    fun resolver() {

        viewModelScope.launch {

            try {

                loading = true
                error = ""
                resultado = null
                yEvaluado = null
                puntosGrafica = emptyList()

                // =========================================
                // VALIDACIONES
                // =========================================

                if (puntos.size < 2) {

                    error =
                        "Debe ingresar al menos 2 puntos"

                    loading = false
                    return@launch
                }

                val listaX =
                    puntos.map { it.x }

                val listaY =
                    puntos.map { it.y }

                if (listaX.distinct().size != listaX.size) {

                    error =
                        "Existen valores X duplicados"

                    loading = false
                    return@launch
                }

                // =========================================
                // REQUEST
                // =========================================

                val response =
                    RetrofitInstance.api.resolverMetodo(
                        metodoSeleccionado,
                        PuntosRequest(
                            puntos_x = listaX,
                            puntos_y = listaY
                        )
                    )

                // =========================================
                // RESPONSE
                // =========================================

                if (response.isSuccessful) {

                    resultado =
                        response.body()

                    resultado?.let {

                        historial =
                            historial + HistorialItem(
                                metodo = it.metodo,
                                polinomio = it.polinomio
                            )
                        
                        generarPuntosGrafica(it.polinomio)
                    }

                } else {

                    error =
                        "Error ${response.code()}"
                }

            } catch (e: Exception) {

                error =
                    e.message ?: "Error desconocido"

            } finally {

                loading = false
            }
        }
    }
}