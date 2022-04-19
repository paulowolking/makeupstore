package com.wolking.makeupstore.presentation.ui.makeup.utils.extensions

import com.google.gson.Gson

val Any.json : String
    get() {
        return Gson().toJson(this)
    }
