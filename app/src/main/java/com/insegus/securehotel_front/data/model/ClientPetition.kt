package com.insegus.securehotel_front.data.model

data class ClientPetition(
    val clientId: String,
    val nameMaterial: String,
    val amount: Int,
    val digitalSignature: String,
    val orderDate: String
)