package com.example.metodosnumericos.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun LatexView(
    latex: String,
    modifier: Modifier = Modifier
) {

    val cleanLatex =
        latex
            .replace("−", "-")
            .replace("\n", " ")
            .replace("\r", " ")

    AndroidView(

        modifier = modifier,

        factory = { context ->

            WebView(context).apply {

                webViewClient = WebViewClient()

                settings.javaScriptEnabled = true

                loadDataWithBaseURL(

                    null,

                    """
                    <!DOCTYPE html>
                    <html>
                    <head>

                    <meta charset="utf-8">

                    <script>
                      MathJax = {
                        tex: {
                          inlineMath: [['$', '$']],
                          displayMath: [['$$', '$$']]
                        }
                      };
                    </script>

                    <script
                      src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
                    </script>

                    <style>

                      body {

                        margin: 0;
                        padding: 12px;

                        font-size: 120%;

                        color: #111827;

                        background: transparent;
                      }

                    </style>

                    </head>

                    <body>

                    $$ $cleanLatex $$

                    </body>
                    </html>
                    """.trimIndent(),

                    "text/html",

                    "utf-8",

                    null
                )
            }
        }
    )
}