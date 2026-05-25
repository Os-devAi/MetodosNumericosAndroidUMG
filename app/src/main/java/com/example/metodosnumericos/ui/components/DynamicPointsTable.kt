package com.example.metodosnumericos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.metodosnumericos.model.Punto

@Composable
fun DynamicPointsTable(
    puntos: List<Punto>,
    onXChange: (Int, String) -> Unit,
    onYChange: (Int, String) -> Unit,
    onAdd: () -> Unit,
    onDelete: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Cabecera
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Valor X",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Valor F(x)",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        puntos.forEachIndexed { index, punto ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                OutlinedTextField(
                    value = if (punto.x == 0.0) "" else punto.x.toString(),
                    onValueChange = { onXChange(index, it) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("0.0") }
                )

                OutlinedTextField(
                    value = if (punto.y == 0.0) "" else punto.y.toString(),
                    onValueChange = { onYChange(index, it) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("0.0") }
                )

                IconButton(
                    onClick = { onDelete(index) },
                    enabled = puntos.size > 2
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = if (puntos.size > 2) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onAdd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Agregar Punto")
        }
    }
}
