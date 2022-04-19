package com.wolking.makeupstore.presentation.ui.makeup.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wolking.makeupstore.R
import com.wolking.makeupstore.databinding.FragmentDetailProductBinding
import com.wolking.makeupstore.domain.product.model.Product
import com.wolking.makeupstore.presentation.ui.makeup.utils.extensions.fromJson
import com.wolking.makeupstore.presentation.ui.makeup.utils.extensions.toMoneyReal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailProductViewModel: DetailProductViewModel by viewModels()

    private var _binding: FragmentDetailProductBinding? = null

    private val binding get() = _binding!!

    private var product: Product? = null

    private var productIsFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        registerObservers()
    }

    private fun setupView() {
        product = arguments?.getString("product")?.fromJson(Product::class.java)
        product?.imageLink?.let {
            Glide.with(requireContext())
                .load(it)
                .apply(RequestOptions().centerInside())
                .fallback(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.gray)))
                .placeholder(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.gray)))
                .into(binding.imageView)
        }
        val name = "${product?.name} - ${product?.brand}"
        binding.tvName.text = name
        binding.tvCategory.text = product?.category
        binding.tvTypeProduct.text = product?.productType?.name
        binding.ratingBar.rating = product?.rating?.toFloat() ?: 0F
        binding.tvRating.text = product?.rating?.toString()
        binding.tvDescription.text = product?.description
        binding.tvPrice.text = product?.price?.toMoneyReal()

        product?.let { product ->
            binding.ivFavorite.setOnClickListener {
                productIsFavorite = !productIsFavorite
                setFavoriteImageView(productIsFavorite)
                detailProductViewModel.favoriteProduct(product, productIsFavorite)
            }
        }
    }

    private fun setFavoriteImageView(isFavorite: Boolean) {
        val drawable =
            if (isFavorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                drawable
            )
        )
    }

    private fun registerObservers() {
        detailProductViewModel.checkProductIsFavorite(product?.id ?: 0)
        detailProductViewModel.productIsFavorite.observe(viewLifecycleOwner) {
            productIsFavorite = it
            setFavoriteImageView(productIsFavorite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}