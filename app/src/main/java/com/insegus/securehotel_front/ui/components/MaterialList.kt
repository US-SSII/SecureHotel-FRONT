package com.insegus.securehotel_front.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.insegus.securehotel_front.data.model.Material

@Composable
fun MaterialList(materials: List<Material>, onMaterialUpdate: (Material) -> Unit) {
    LazyColumn {
        items(materials) { material ->
            MaterialRow(
                material = material,
                onMaterialUpdate = onMaterialUpdate
            )
        }
    }
}
