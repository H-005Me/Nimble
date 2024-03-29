package com.example.nimble.entities

import com.example.nimble.user.user
import java.io.Serializable


class OrdersClass(
    name: String,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    tables: String,
    isCompleted: Int
) : Serializable {
    private var name: String
    private var year: Int
    private var month: Int
    private var day: Int
    private var hour: Int
    private var minute: Int
    private var tables: String
    private var isCompleted: Int
    private var remarks: String
    private var isResponsed: Int

    //the id of the order,not of the user
    private var id: Int
    private var userName: String
    init {
        this.name = name
        this.year = year
        this.id = 0
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.tables = tables
        this.isCompleted = isCompleted
        this.remarks = ""
        this.userName = user.getUserName()
        this.isResponsed = getResponse()
    }

    fun setUserName(new_userName: String) {
        this.userName = new_userName
    }

    fun getUserName(): String {
        return this.userName
    }

    fun getId(): Int {
        return this.id
    }

    fun setId(new_id: Int) {
        this.id = new_id
    }

    fun getName(): String {
        return this.name
    }

    fun setRemarks(new_remarks: String) {
        this.remarks = new_remarks
    }

    fun getRemarks(): String {
        return this.remarks
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

    fun setMonth(new_month: Int) {
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

    fun getTables(): String {
        return this.tables
    }

    fun setTable(new_tables: String) {
        this.tables = new_tables
    }

    fun getStatus(): Int {
        return this.isCompleted
    }

    fun setStatus(new_status: Int) {
        this.isCompleted = new_status
    }

    fun getResponse(): Int {
        if (this.getStatus() == 4)
            return 1
        return 0
    }
}