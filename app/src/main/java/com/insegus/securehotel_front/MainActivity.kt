package com.insegus.securehotel_front

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.insegus.securehotel_front.data.model.ClientPetition
import com.insegus.securehotel_front.data.model.Material
import com.insegus.securehotel_front.ui.components.MaterialList
import com.insegus.securehotel_front.ui.components.PetitionButton
import com.insegus.securehotel_front.ui.components.Title
import com.insegus.securehotel_front.ui.theme.SecureHotelFRONTTheme
import com.insegus.securehotel_front.utils.Client
import com.insegus.securehotel_front.utils.ConnectTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.DateFormat
import java.util.Date
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val client = Client("10.0.2.2",12345)
            //val connectTask = ConnectTask(context, client)

            SecureHotelFRONTTheme {
                // A surface container using the 'background' color from the theme
                val materials by remember { mutableStateOf(
                    mutableListOf(
                        Material("Camas"),
                        Material("Sillas"),
                        Material("Mesas"),
                        Material("Sillones"),
                    )
                ) }
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        // Realiza la conexión en un hilo de fondo (IO) utilizando Coroutines
                        client.connect(context)
                        client.sendMessage("pito")
                    }
                }
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

                            val selectedMaterials = materials.filter { it.isSelected }
                            val currentDateTime = DateFormat.getDateTimeInstance().format(Date())
                            val clientPetitions = selectedMaterials.map { material ->
                                ClientPetition(
                                    clientId = it,
                                    nameMaterial = material.name,
                                    amount = material.amount.toInt(),
                                    digitalSignature = "YourDigitalSignatureHere",
                                    orderDate = currentDateTime
                                )
                            }

                            Log.d("Petitions", clientPetitions.toString())
                        }
                    )

                }
            }
        }
    }
}