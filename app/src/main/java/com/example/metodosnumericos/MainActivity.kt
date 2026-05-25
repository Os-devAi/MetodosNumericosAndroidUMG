package com.example.metodosnumericos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.metodosnumericos.navigation.NavController
import com.example.metodosnumericos.ui.screen.MainScreen
import com.example.metodosnumericos.ui.theme.MetodosNumericosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetodosNumericosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavController(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}