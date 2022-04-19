package com.wolking.makeupstore.presentation.ui.makeup.favorite

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
import com.wolking.makeupstore.databinding.FragmentFavoriteBinding
import com.wolking.makeupstore.presentation.ui.makeup.favorite.adapter.ItemProductFavoriteAdapter
import com.wolking.makeupstore.presentation.ui.makeup.favorite.adapter.ItemProductFavoriteAdapterListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), ItemProductFavoriteAdapterListener {
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
        with(binding.rvProductsFavorites) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ItemProductFavoriteAdapter(context, this@FavoriteFragment)
        }
    }

    private fun registerObservers() {
        favoriteViewModel.getFavoriteProducts()
        favoriteViewModel.products.observe(viewLifecycleOwner) {
            binding.emptyList.root.isVisible = it.isEmpty()
            (binding.rvProductsFavorites.adapter as ItemProductFavoriteAdapter)
                .updateItemsList(it)
        }
    }

    override fun itemSelected(product: Product) {
        val bundle = bundleOf(
            "product" to product.toString(),
        )
        findNavController().navigate(R.id.action_to_detail, bundle)
    }

    override fun itemFavorite(product: Product, position: Int) {
        favoriteViewModel.removeFavoriteProduct(product)
        (binding.rvProductsFavorites.adapter as ItemProductFavoriteAdapter)
            .removeItem(position)
    }
}