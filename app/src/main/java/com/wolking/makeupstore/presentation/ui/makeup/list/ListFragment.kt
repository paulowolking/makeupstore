package com.wolking.makeupstore.presentation.ui.makeup.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.wolking.makeupstore.R
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.databinding.FragmentListProductsBinding
import com.wolking.makeupstore.presentation.ui.makeup.list.adapter.ItemProductAdapter
import com.wolking.makeupstore.presentation.ui.makeup.list.listerner.ItemProductAdapterListener
import com.wolking.makeupstore.presentation.ui.makeup.list.bottom_sheet.BottomSheetDialogFilterProduct
import com.wolking.makeupstore.presentation.ui.makeup.list.enums.TypeFilterProduct
import com.wolking.makeupstore.presentation.ui.makeup.list.listerner.ItemFilterProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), ItemProductAdapterListener, ItemFilterProductListener {
    private val listViewModel: ListViewModel by viewModels()

    private var _binding: FragmentListProductsBinding? = null

    private val binding get() = _binding!!

    private var bottomSheetDialogFilterProduct: BottomSheetDialogFilterProduct? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        registerObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        binding.fabFilter.isVisible = false
        bottomSheetDialogFilterProduct = BottomSheetDialogFilterProduct(this@ListFragment)
        with(binding.rvProducts) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ItemProductAdapter(context, this@ListFragment)
        }

        binding.fabFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_to_favorite_product)
        }

        binding.fabFilter.setOnClickListener {
            bottomSheetDialogFilterProduct?.show(
                childFragmentManager,
                "FilterProducts"
            )
        }
    }

    private fun registerObservers() {
        listViewModel.getProducts()
        listViewModel.products.observe(viewLifecycleOwner) {
            binding.fabFilter.isVisible = it.isNotEmpty()
            binding.shimmer.shimmerLayout.stopShimmer()
            (binding.rvProducts.adapter as ItemProductAdapter)
                .updateItemsList(it)
        }

        listViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmer.shimmerLayout.startShimmer()
            } else {
                binding.shimmer.shimmerLayout.stopShimmer()
                binding.shimmer.shimmerLayout.isVisible = false
            }
        }

        listViewModel.error.observe(viewLifecycleOwner) {
            binding.errorMessage.root.isVisible = it
        }
    }

    override fun itemSelected(product: Product) {
        val bundle = bundleOf(
            "product" to product.toString(),
        )
        findNavController().navigate(R.id.action_to_detail, bundle)
    }

    override fun filterBiggerPrice() {
        listViewModel.setTypeFilterProduct(TypeFilterProduct.BIGGESTPRICE)
    }

    override fun filterLowestPrice() {
        listViewModel.setTypeFilterProduct(TypeFilterProduct.LOWESTPRICE)
    }

    override fun filterNameCrescent() {
        listViewModel.setTypeFilterProduct(TypeFilterProduct.CRESCENT)
    }

    override fun filterNameDecreasing() {
        listViewModel.setTypeFilterProduct(TypeFilterProduct.DECREASING)
    }

    override fun clearFilter() {
        listViewModel.cleanFilters()
    }
}