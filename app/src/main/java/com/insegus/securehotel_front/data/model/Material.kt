package com.insegus.securehotel_front.data.model

data class Material(
    val name: String,
    var isSelected: Boolean = false,
    var amount: String = "1"
)
