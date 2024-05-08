package com.insegus.securehotel_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.insegus.securehotel_front.data.model.Material
import com.insegus.securehotel_front.ui.components.MaterialList
import com.insegus.securehotel_front.ui.components.PetitionButton
import com.insegus.securehotel_front.ui.components.Title
import com.insegus.securehotel_front.ui.theme.SecureHotelFRONTTheme
import com.insegus.securehotel_front.utils.Client

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureHotelFRONTTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                val materials by remember { mutableStateOf(
                    mutableListOf(
                        Material("Camas"),
                        Material("Sillas"),
                        Material("Mesas"),
                        Material("Sillones"),
                    )
                ) }
                val client = Client("10.0.2.2",12345)
                client.connect(context)
                Column(Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Title("Secure Hotel")
                    Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio entre el título y el listado

                    MaterialList(
                        materials = materials,
                        onMaterialUpdate = { material ->
                            val index = materials.indexOfFirst { it.name == material.name }
                            materials[index] = material
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio entre el listado y el botón
                    PetitionButton(
                        onConfirm = {
                            client.sendMessage("Prueba")
                        }
                    )
                }
            }
        }
    }
}