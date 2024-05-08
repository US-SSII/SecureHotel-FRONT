package com.insegus.securehotel_front.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PetitionButton(onConfirm: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        PetitionPopup(
            onDismiss = { showDialog = false },
            onConfirm = {
                onConfirm(it)
                showDialog = false
            }
        )
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Hacer Pedido")
        }
    }
}


