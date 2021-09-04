package com.example.nimble.entities

import java.io.Serializable


class OrdersClass(
    name: String,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    table: Int,
    isCompleted: Int
) : Serializable {
    private var name: String
    private var year: Int
    private var month: Int
    private var day: Int
    private var hour: Int
    private var minute: Int
    private var table: Int
    private var isCompleted: Int

    init {
        this.name = name
        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.table = table
        this.isCompleted = isCompleted
    }

    fun getName(): String {
        return this.name
    }

    fun getYear(): Int {
        return this.year
    }

    fun setYear(new_year: Int) {
        this.year = new_year
    }

    fun getMonth(): Int {
        return this.month
    }

    fun setMouth(new_month: Int) {
        this.month = new_month
    }

    fun getDay(): Int {
        return this.day
    }

    fun setDay(new_day: Int) {
        this.day = new_day
    }

    fun getHour(): Int {
        return this.hour
    }

    fun getMinute(): Int {
        return this.minute
    }

    fun setHour(new_hour: Int) {
        this.hour = new_hour
    }

    fun setMinutes(new_minute: Int) {
        this.minute = new_minute
    }

    fun getTable(): Int {
        return this.table
    }

    fun getStatus(): Int {
        return this.isCompleted
    }

    fun setStatus(new_status: Int) {
        this.isCompleted = new_status
    }
}