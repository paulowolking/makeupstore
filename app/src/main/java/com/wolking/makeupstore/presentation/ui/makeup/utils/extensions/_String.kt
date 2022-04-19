package com.wolking.makeupstore.presentation.ui.makeup.utils.extensions

import com.google.gson.Gson

fun <T> String.fromJson(clazz: Class<T>): T? {
    return try {
        Gson().fromJson<T>(this, clazz)
    } catch (exc: java.lang.Exception) {
        exc.printStackTrace()
        null
    }
}
