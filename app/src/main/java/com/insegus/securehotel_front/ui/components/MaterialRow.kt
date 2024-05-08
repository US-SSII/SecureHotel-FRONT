package com.insegus.securehotel_front.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.insegus.securehotel_front.R
import com.insegus.securehotel_front.data.model.Material

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialRow(
    material: Material,
    onMaterialUpdate: (Material) -> Unit,
) {
    var isSelected by remember { mutableStateOf(material.isSelected) }
    var amount by remember { mutableStateOf(material.amount) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                isSelected = !isSelected
                onMaterialUpdate(material.copy(isSelected = isSelected))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                isSelected = it
                onMaterialUpdate(material.copy(isSelected = it))
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Blue // Cambiar color del checkbox
            )
        )

        // Texto del material
        Text(
            text = material.name,
            maxLines = 1,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                val parsedValue = newValue.toIntOrNull()
                if (parsedValue != null && parsedValue in 1..300) {
                    amount = newValue
                    onMaterialUpdate(material.copy(amount = newValue))
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Acción al hacer clic en el botón "Listo" */ }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.width(100.dp) // Ancho fijo para el campo de entrada
        )
    }
}



