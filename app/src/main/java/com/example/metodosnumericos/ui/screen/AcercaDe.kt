package com.example.metodosnumericos.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metodosnumericos.R

@Composable
fun AcercaDe(
    modifier: Modifier
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.umg),
                contentDescription = "Logo UMG",
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            Text(
                "Ingeniería en Sistemas de Información y Ciencias de la Comunicación",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                "Sede Salamá, Baja Verapaz - 2026",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text("Métodos Numéricos", fontSize = 24.sp)
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier.height(250.dp)
                )

                Text("Integrantes: ", fontSize = 24.sp)
                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text("YESENIA MARINA CATALÁN REYES")
                Text("RICARDO GABRIEL LÓPEZ FRANCO")
                Text("OSBALDO ESEQUIEL MARTÍNEZ DE LOS SANTOS")

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}