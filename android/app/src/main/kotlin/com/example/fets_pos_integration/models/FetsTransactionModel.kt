package com.example.fets_pos_integration.models

import androidx.annotation.Keep

@Keep
data class FetsTransactionModel(
    val amount : String,
    val reference : String = ""
)
