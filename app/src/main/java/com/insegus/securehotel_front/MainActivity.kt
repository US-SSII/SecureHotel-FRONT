package com.insegus.securehotel_front

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.KeyPair
import java.security.PrivateKey
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var keyPair: KeyPair

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val client = Client("10.0.2.2", 12345)
            val coroutineScope = rememberCoroutineScope()

            SecureHotelFRONTTheme {
                // A surface container using the 'background' color from the theme
                val materials by remember {
                    mutableStateOf(
                        mutableListOf(
                            Material("Camas"),
                            Material("Sillas"),
                            Material("Mesas"),
                            Material("Sillones"),
                        )
                    )
                }
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        // Realiza la conexión en un hilo de fondo (IO) utilizando Coroutines
                        client.connect(context)
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
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            val currentDateTime = dateFormat.format(Date())

                            // Verifica si ya se ha generado un par de claves, si no, genera uno nuevo
                            if (!::keyPair.isInitialized) {
                                keyPair = DigitalSignatureHelper.generateKeyPair()
                            }

                            val clientPetitions = selectedMaterials.map { material ->
                                ClientPetition(
                                    clientId = it,
                                    nameMaterial = material.name,
                                    amount = material.amount.toInt(),
                                    digitalSignature = signPetition(currentDateTime.toByteArray(), keyPair.private),
                                    orderDate = currentDateTime
                                )
                            }
                            val clientPetitionsJson = Json.encodeToString(clientPetitions)
                            CoroutineScope(Dispatchers.IO).launch {
                                client.sendMessage(clientPetitionsJson)
                            }
                        }
                    )

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signPetition(data: ByteArray, privateKey: PrivateKey): String {
        val signature = DigitalSignatureHelper.signData(data, privateKey)
        return Base64.getEncoder().encodeToString(signature)
    }
}

