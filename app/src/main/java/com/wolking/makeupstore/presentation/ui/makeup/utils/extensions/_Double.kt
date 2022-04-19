package com.wolking.makeupstore.presentation.ui.makeup.utils.extensions

import java.text.NumberFormat
import java.util.*

fun Double.toMoneyReal(): String {
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
}