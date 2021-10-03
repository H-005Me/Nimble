package com.example.nimble.entities

import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode

class RestaurantsClass(
    title: String?, review: Int?,
    grade: Double?,
    icon: Int?,
    background: Int?,
    latitude: Double?,
    longitude: Double?,
    categories: Array<String>
) :Serializable {
    private var categories: Array<String>
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
    private var pageBackground: String
    private var offertsArray: ArrayList<OffertsClass>
    private var locationMap: Int
    private var priceRange: String
    private var restaurantType: String
    private var payMethod: String
    private var id: Int
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
        this.street = "none"
        this.pageBackground = "none"
        this.offertsArray = ArrayList<OffertsClass>()
        this.locationMap = 0
        // aici merge in GeneralRestaurant
        this.priceRange = "Normal"
        this.restaurantType = "International"
        this.payMethod = "Card/Cash"
        this.id = 0
    }

    fun setForGeneralRestaurant(new_price_range: String, new_type: String, new_method: String) {
        this.priceRange = new_price_range
        this.restaurantType = new_type
        this.payMethod = new_method
    }

    fun getRestaurantType(): String {
        return this.restaurantType
    }
    fun setId(new_id:Int){
        this.id=new_id
    }
    fun getId():Int{
        return this.id
    }
    fun setRestaurantType(new_type:String){
        this.restaurantType=new_type
    }

    fun getPayMethod(): String {
        return this.payMethod
    }

    fun setPayMethod(new_method: String) {
        this.payMethod = new_method
    }

    fun getPriceRange(): String {
        return this.priceRange
    }

    fun setPriceRange(new_price_range: String) {
        this.priceRange = new_price_range
    }

    fun setLocationMap(new_map_location: Int) {
        this.locationMap = new_map_location
    }

    fun getLocationMap(): Int {
        return this.locationMap
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

    fun getCategories(): Array<String> {
        return categories
    }

    fun getTitle(): String {
        return title
    }

    fun getBackground(): Int {
        return background
    }

    fun setBackground(bg: Int) {
        this.background = bg
    }

    fun setTitle(name: String) {
        title = name!!
    }

    fun getIcon(): Int {
        return icon
    }
    fun setIcon (icon: Int) {
        this.icon = icon
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

    fun setPageBackground(new_page_background: String) {
        this.pageBackground = new_page_background
    }

    fun getPageBackground(): String {
        return this.pageBackground
    }

    fun deleteOffer(index: Int) {
        this.offertsArray.removeAt(index)
    }
}
