package com.wolking.makeupstore.domain.product.model

import com.google.gson.annotations.SerializedName
import com.wolking.makeupstore.presentation.ui.makeup.utils.extensions.json

class Product {
    var id: Int = 0
    var brand: String? = null
    var name: String? = null
    var price: Double = 0.0

    @SerializedName("price_sign")
    var priceSign: String? = null

    @SerializedName("image_link")
    var imageLink: String? = null

    @SerializedName("product_link")
    var productLink: String? = null

    @SerializedName("website_link")
    var webSiteLink: String? = null
    var description: String? = null
    var rating: Double? = null
    var category: String? = null

    @SerializedName("product_type")
    var productType: ProductType? = null

    @SerializedName("tag_list")
    var tagList: List<String>? = null

    @SerializedName("product_colors")
    var productColors: List<ProductColor>? = null

    override fun toString(): String {
        return json
    }
}

class ProductColor(
    @SerializedName("hex_value")
    var hexValue: String?,
    @SerializedName("colour_name")
    var colourName: String?
)

enum class ProductType {
    @SerializedName("blush")
    BLUSH,

    @SerializedName("bronzer")
    BRONZER,

    @SerializedName("eyebrow")
    EYEBROW,

    @SerializedName("eyeliner")
    EYELINER,

    @SerializedName("eyeshadow")
    EYESHADOW,

    @SerializedName("foundation")
    FOUNDATION,

    @SerializedName("lip_liner")
    LIPLINER,

    @SerializedName("lipstick")
    LIPSTICKER,

    @SerializedName("mascara")
    MASCARA,

    @SerializedName("nail_polish")
    NAILPOLISH,
}