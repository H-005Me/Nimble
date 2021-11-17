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

/**
 * converts is_reserved from db to status
 * 0 - 0
 * 1 - 2
 */
fun isReservedToStatus (is_reserved: Int): Int
{ return if (is_reserved == 0) 0 else 2 }

/**
 * converts status to is_reserved in db
 * 0 - 0
 * 2 - 1
 */
fun statusToIsReserved (status: Int): Int
{ return if (status == 0) 0 else 1 }