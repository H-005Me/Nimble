package com.example.nimble.entities

import java.io.Serializable

class TablesClass(numberOfPeople: Int, id: Int, status: Int) : Serializable {
    private var id: Int
    private var numberOfPeople: Int

    /**
     * TODO what do status values mean 0 - free, 1 - reserved, 2 - expired (?)
     */
    private var status: Int

    init {
        this.id = id
        this.numberOfPeople = numberOfPeople
        this.status = status
    }

    fun getId(): Int {
        return this.id
    }

    fun getNumberOfPeople(): Int {
        return this.numberOfPeople
    }

    fun getStatus(): Int {
        return this.status
    }

    fun setStatus(isAvailable: Int) {
        this.status = isAvailable
    }
}