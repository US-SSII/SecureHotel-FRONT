package com.insegus.securehotel_front.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Title(title: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp, // Ajusta el tamaño de la fuente según sea necesario
                fontWeight = FontWeight.Bold, // Hacer el texto en negrita
                color = Color.Black, // Establecer el color del texto
            ),
            textAlign = TextAlign.Center, // Centrar el texto
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


