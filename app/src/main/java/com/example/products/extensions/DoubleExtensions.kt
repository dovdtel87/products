package com.example.products.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.formatPriceAsEuro(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EU"))
    return numberFormat.format(this)
}
