package com.serex.appstorehb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product (
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var price: Float,
        var location: String,
        var img: String = ""
)