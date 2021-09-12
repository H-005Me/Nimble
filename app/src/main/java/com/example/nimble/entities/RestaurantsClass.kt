package com.example.nimble.entities

import android.view.Menu
import android.view.View
import android.widget.Toast
import com.example.nimble.mainmenu.MainMenu
import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.*

class RestaurantsClass(
    title: String?, review: Int?,
    grade: Double?,
    icon: Int?,
    background: Int?,
    latitude: Double?,
    longitude: Double?,
    categories: Array<MenuClass>
) :Serializable {
    private var categories: Array<MenuClass>
    private var title: String
    private var distance: Double
    private var review = 0
    private var grade = 0.0
    private var icon: Int
    private var background: Int
    private var latitude: Double
    private var longitude: Double
    private var currentLatitude: Double
    private var currentLongitude: Double
    private var street: String
    private var pageBackground: Int
    private var offertsArray: ArrayList<OffertsClass>

    init {
        this.title = title!!
        this.latitude = latitude!!
        this.longitude = longitude!!
        this.currentLatitude = 0.0
        this.currentLongitude = 0.0
        this.distance = BigDecimal(
            distance1(
                currentLatitude,
                currentLongitude,
                latitude,
                longitude
            )
        ).setScale(1, RoundingMode.HALF_EVEN).toDouble()
        this.review = review!!
        this.grade = grade!!
        this.icon = icon!!
        this.background = background!!
        this.categories = categories
        this.street = "None"
        this.pageBackground = this.background
        this.offertsArray = ArrayList<OffertsClass>()
    }

    fun reDistance() {
        this.distance = BigDecimal(
            distance1(
                currentLatitude,
                currentLongitude,
                latitude,
                longitude
            )
        ).setScale(1, RoundingMode.HALF_EVEN).toDouble()
    }

    fun getCategories(): ArrayList<String> {
        var aList = ArrayList<String>()
        var index = 0
        while (index < categories.size) {
            aList.add(categories[index].getName())
            index++
        }
        return aList
    }

    fun getTitle(): String {
        return title
    }
    fun getBackground():Int{
        return background
    }

    fun setTitle(name: String) {
        title = name!!
    }

    fun getIcon(): Int {
        return icon
    }

    fun getDistance(): Double {
        return distance
    }

    //
    fun distance1(startLat: Double, startLong: Double, destLat: Double, endLong: Double): Double {
        var haversine: Double
        var distance: Double
        var dLat: Double
        var dLon: Double
        val DEG_RAD = 0.01745329251994
        val R_EARTH = 6367.45
        dLat = (destLat - startLat) * DEG_RAD
        dLon = (endLong - startLong) * DEG_RAD;

        haversine = Math.sin(dLat * 0.5) * Math.sin(dLat * 0.5) +

                Math.sin(dLon * 0.5) * Math.sin(dLon * 0.5) *

                Math.cos(startLat * DEG_RAD) *

                Math.cos(destLat * DEG_RAD)
        distance = Math.asin(Math.sqrt(haversine)) * R_EARTH * 2.0
        return distance
    }

    //
    fun getReview(): Int {
        return review
    }

    fun getGrade(): Double {
        return grade
    }

    fun setDistance(distance: Double?) {
        this.distance = distance!!
    }

    fun setReview(review: Int?) {
        this.review = review!!
    }

    fun setGrade(grade: Double?) {
        this.grade = grade!!
    }

    fun getCurrentLatitude(): Double {
        return this.currentLatitude
    }

    fun getCurrentLongitude(): Double {
        return this.currentLongitude
    }

    fun setCurrentLatitude(new_lat: Double) {
        this.currentLatitude = new_lat
    }

    fun setCurrentLongitude(new_long: Double) {
        this.currentLongitude = new_long
    }

    fun setStreet(new_street: String) {
        this.street = new_street
    }

    fun getStreet(): String {
        return this.street
    }

    fun setPageBackground(new_page_background: Int) {
        this.pageBackground = new_page_background
    }

    fun getPageBackground(): Int {
        return this.pageBackground
    }

    fun deleteOffer(index: Int) {
        this.offertsArray.removeAt(index)
    }
}
