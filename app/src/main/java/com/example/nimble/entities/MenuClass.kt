package com.example.nimble.entities

import java.io.Serializable

class MenuClass(Categories:String, theList: Array<ProductClass>):Serializable {
    private  var Categories:String
    private var theList:Array<ProductClass>
    init {
        this.Categories=Categories
        this.theList=theList
    }
    fun getName():String
    {
        return this.Categories
    }
    fun setName(new_name:String)
    {this.Categories=new_name
    }
    fun getList():Array<ProductClass>
    {
        return this.theList
    }
    fun getElem(elem:Int):ProductClass
    {
        return this.theList[elem]
    }
}