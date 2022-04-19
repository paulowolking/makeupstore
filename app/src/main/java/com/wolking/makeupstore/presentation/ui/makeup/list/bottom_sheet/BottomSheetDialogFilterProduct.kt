package com.wolking.makeupstore.presentation.ui.makeup.list.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wolking.makeupstore.databinding.BottomSheetFilterProductsBinding
import com.wolking.makeupstore.presentation.ui.makeup.list.listerner.ItemFilterProductListener

class BottomSheetDialogFilterProduct(
    private val listenerFilter: ItemFilterProductListener
) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFilterProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterProductsBinding.inflate(inflater, container, false)

        binding.buttonFilter.setOnClickListener {
            if (binding.switchBiggest.isChecked) {
                listenerFilter.filterBiggerPrice()
            }

            if (binding.switchLowest.isChecked) {
                listenerFilter.filterLowestPrice()
            }

            if (binding.switchCrescent.isChecked) {
                listenerFilter.filterNameCrescent()
            }

            if (binding.switchDescending.isChecked) {
                listenerFilter.filterNameDecreasing()
            }
            dismiss()
        }

        binding.buttonClear.setOnClickListener {
            binding.switchBiggest.isChecked = false
            binding.switchLowest.isChecked = false
            binding.switchCrescent.isChecked = false
            binding.switchDescending.isChecked = false
            listenerFilter.clearFilter()
            dismiss()
        }
        return binding.root
    }
}