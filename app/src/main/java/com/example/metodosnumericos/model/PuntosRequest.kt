package com.example.metodosnumericos.model

data class PuntosRequest(
    val puntos_x: List<Double>,
    val puntos_y: List<Double>
)

data class Punto(
    val x: Double,
    val y: Double
)

data class Paso(
    val titulo: String?,
    val descripcion: String?,
    val formula_general: String?,
    val resultado: String?
)

data class ResultadoResponse(
    val metodo: String,
    val polinomio: String,
    val latex: String,
    val grado: Int,
    val grafica: List<Punto>,
    val puntos_originales: List<Punto>,
    val pasos: List<Paso>
)

data class HistorialItem(
    val metodo: String,
    val polinomio: String
)