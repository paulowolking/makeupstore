package com.wolking.makeupstore.presentation.ui.makeup.list.listerner

interface ItemFilterProductListener {
    fun filterBiggerPrice()
    fun filterLowestPrice()
    fun filterNameCrescent()
    fun filterNameDecreasing()
    fun clearFilter()
}