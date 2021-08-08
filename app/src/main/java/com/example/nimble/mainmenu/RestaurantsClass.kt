package com.example.mainmenu

class RestaurantsClass(title: String?, distance: Double?, review:Int?,grade:Double?) //(var distance:Double, var name:String, var reviews:Int, var grade:Double)
{
    private var title:String
    private var distance:Double
    private var review=0
    private var grade=0.0
    init {
        this.title = title!!
        this.distance=distance!!
        this.review= review!!
        this.grade=grade!!
    }
    fun getTitle():String
    {
        return title
    }
    fun setTitle(name:String)
{
    title=name!!
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
