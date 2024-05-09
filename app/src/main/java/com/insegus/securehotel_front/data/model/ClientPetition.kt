package com.insegus.securehotel_front.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientPetition(
    val clientId: String,
    val nameMaterial: String,
    val amount: Int,
    val digitalSignature: String,
    val publicKey: String,
    val orderDate: String
)