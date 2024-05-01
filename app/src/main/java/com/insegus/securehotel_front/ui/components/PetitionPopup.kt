package com.insegus.securehotel_front.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PetitionPopup(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var notes by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .background(Color.White, MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas adicionales") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onConfirm(notes) }, modifier = Modifier.align(Alignment.End)) {
                Text("Confirmar")
            }
        }
    }
}


@Composable
@Preview
fun PetitionPopupPreview() {
    PetitionPopup({}, {})
}