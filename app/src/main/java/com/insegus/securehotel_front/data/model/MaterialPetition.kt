package com.insegus.securehotel_front.data.model

data class MaterialPetition(
    val clientId: String,
    val name: String,
    val amount: Int,
    val digitalSignature: String,
    val orderDate: String,
    val isValid: Boolean
)