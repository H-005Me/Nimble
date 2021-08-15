package com.example.nimble.entities

import com.example.nimble.R
import java.io.Serializable

class ProductClass(nameOfProduct: String,priceOfProduct:Double,quantity:Double,photo:Int):Serializable {
    private var nameOfProduct: String
    private var priceOfProduct: Double
    private var quantity: Double
    private var photo = R.drawable.ic_launcher_background

    init {
        this.nameOfProduct = nameOfProduct!!
        this.priceOfProduct = priceOfProduct!!
        this.quantity = quantity!!
        this.photo = photo!!

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

    fun setPhoto(new_photo:Int)
    {
        this.photo=new_photo
    }
}