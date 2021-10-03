package com.example.nimble.entities

import com.example.nimble.R
import java.io.Serializable

class ProductClass(
    nameOfProduct: String,
    priceOfProduct: Double,
    quantity: Double,
    photo: Int,
    categoryOfProduct: String,
    id: Int
) : Serializable {
    private var nameOfProduct: String
    private var priceOfProduct: Double
    private var quantity: Double
    private var photo = R.drawable.ic_launcher_background
    private var categoryOfProduct: String
    private var howManyAdded: Int
    private var id: Int

    init {
        this.nameOfProduct = nameOfProduct!!
        this.priceOfProduct = priceOfProduct!!
        this.quantity = quantity!!
        this.photo = photo!!
        this.categoryOfProduct = categoryOfProduct
        this.howManyAdded = 0
        this.id = id
    }

    fun getId(): Int {
        return this.id
    }

    fun setId(new_id: Int) {
        this.id = new_id
    }

    fun getCategoryOfProduct(): String {
        return this.categoryOfProduct
    }

    fun getTitle(): String {
        return this.nameOfProduct
    }

    fun setTitle(new_name: String) {
        this.nameOfProduct = new_name
    }

    fun getPriceOfProduct(): Double {
        return this.priceOfProduct
    }

    fun setPriceOfProduct(new_price: Double) {
        this.priceOfProduct = new_price
    }

    fun getQuantity(): Double {
        return this.quantity
    }

    fun setQuantity(new_quantity:Double) {
        this.quantity = new_quantity
    }

    fun getPhoto(): Int {
        return this.photo
    }

    fun setPhoto(new_photo: Int) {
        this.photo = new_photo
    }

    fun getHowManyAdded(): Int {
        return this.howManyAdded
    }

    fun lowerHowManyAdded() {
        this.howManyAdded -= 1
    }

    fun riseHowManyAdded() {
        this.howManyAdded += 1
    }
}