package com.example.nimble.entities

import java.io.Serializable

class CategoriesClass(name: String, photo: Int) : Serializable {
    private var name: String
    private var photo: Int
    private var arrayOfIndices = ArrayList<Int>()

    init {
        this.name = name
        this.photo = photo
    }

    fun getName(): String {
        return this.name
    }

    fun getPhoto(): Int {
        return this.photo
    }

    fun getTheIndices(): ArrayList<Int> {
        return this.arrayOfIndices
    }

    fun addIndices(index: Int) {
        this.arrayOfIndices.add(index)
    }

    fun setIndices(removeDuplicates: ArrayList<Int>) {
        this.arrayOfIndices = removeDuplicates
    }
}