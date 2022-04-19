package com.wolking.makeupstore.presentation.ui.makeup.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


val <T> LiveData<T>.asMutable: MutableLiveData<T>?
    get() = this as? MutableLiveData<T>