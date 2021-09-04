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

    fun getMonth(): Int {
        return this.month
    }

    fun getDay(): Int {
        return this.day
    }

    fun getHour(): Int {
        return this.hour
    }

    fun getMinute(): Int {
        return this.minute
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