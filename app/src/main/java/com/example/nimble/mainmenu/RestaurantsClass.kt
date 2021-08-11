package com.example.mainmenu

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon

class RestaurantsClass(title: String?, distance: Double?, review:Int?,grade:Double?,icon:Icon?,background:Drawable?) //(var distance:Double, var name:String, var reviews:Int, var grade:Double)
{
    private var title: String
    private var distance: Double
    private var review = 0
    private var grade = 0.0
    private var icon:Icon
    private var background:Drawable
    init {
        this.title = title!!
        this.distance = distance!!
        this.review = review!!
        this.grade = grade!!
        this.icon = icon!!
        this.background=background!!
    }


    fun getTitle(): String {
        return title
    }
    fun getBackground():Drawable{
        return background
    }
    fun setTitle(name: String) {
        title = name!!
    }

    fun getIcon(): Icon
    {
        return icon
    }
    fun getDistance():Double
    {
        return distance
    }
    fun getReview():Int{
        return  review
    }
    fun getGrade():Double{
        return grade
    }

    fun setDistance(distance: Double?)
    {
        this.distance=distance!!
    }
    fun setReview(review: Int?)
    {
        this.review=review!!
    }
    fun setGrade(grade: Double?)
    {
        this.grade=grade!!
    }
}
