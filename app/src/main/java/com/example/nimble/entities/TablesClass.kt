package com.example.nimble.entities

import java.io.Serializable

class TablesClass(numberOfPeople: Int, id: Int, status: Boolean) : Serializable {
    private var id: Int
    private var numberOfPeople: Int
    private var status: Boolean

    init {
        this.id = id!!
        this.numberOfPeople = numberOfPeople!!
        this.status = status!!
    }

    fun getId(): Int {
        return this.id
    }

    fun getNumberOfPeople(): Int {
        return this.numberOfPeople
    }

    fun getStatus(): Boolean {
        return this.status
    }

    fun setStatus(isAvailable: Boolean) {
        this.status = isAvailable
    }
}