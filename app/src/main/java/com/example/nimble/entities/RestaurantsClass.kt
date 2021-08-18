package com.example.nimble.entities

import android.view.Menu
import android.view.View
import java.io.Serializable

class RestaurantsClass (
    title: String?, distance: Double?, review:Int?,
    grade:Double?,
    icon:Int?,
    background: Int?,
    categories: Array<MenuClass>
) :Serializable
{
    private var categories:Array<MenuClass>
    private var title: String
    private var distance: Double
    private var review = 0
    private var grade = 0.0
    private var icon:Int
    private var background:Int
    init {
        this.title = title!!
        this.distance = distance!!
        this.review = review!!
        this.grade = grade!!
        this.icon = icon!!
        this.background=background!!
        this.categories=categories!!
    }
    fun getCategories():ArrayList<String>
    {
        var aList=ArrayList<String>()
        var index=0
        while(index<categories.size)
        {
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

    fun getIcon(): Int
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
