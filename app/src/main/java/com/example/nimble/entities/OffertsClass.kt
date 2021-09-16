package com.example.nimble.entities

import com.example.nimble.R
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class OffertsClass(
    id: Int,
    RestaurantName: String,
    background: Int,
    status: Int,
    price: Double,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minutes: Int
) : Serializable {
    private var RestaurantName: String
    private var background: Int
    private var price: Double
    private var year: Int
    private var month: Int
    private var day: Int
    private var hour: Int
    private var minutes: Int
    private var id: Int
    private val c = Calendar.getInstance()
    private var status: Int
    init {
        this.RestaurantName = RestaurantName
        this.background = background
        this.price = price
        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minutes = minutes
        this.id = id
        this.status = status
    }

    fun setId(new_id: Int) {
        this.id = new_id
    }

    fun setStatus(new_status: Int) {
        this.status = new_status
    }

    fun getStatus(): Int {
        return this.status
    }

    fun getName(): String {
        return this.RestaurantName
    }

    fun getId(): Int {
        return this.id
    }

    fun setBackground(new_background: Int) {
        this.background = new_background
    }

    fun getBackground(): Int {
        return this.background
    }

    fun getHour(): Int {
        return this.hour
    }

    fun getMinutes(): Int {
        return this.minutes
    }

    fun getYear(): Int {
        return this.year
    }

    fun getMonth(): Int {
        return this.month
    }

    fun getDay(): Int {
        return this.day
    }
}