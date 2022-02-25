package com.kwanso.grocerylist.datasources.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity
data class Grocery(
    var groceryName: String,
    var groceryStatus: Boolean,
){
    @PrimaryKey(autoGenerate = true)
    var groceryId: Long=0
    var createdAt: Long = System.currentTimeMillis()
}

@Entity(foreignKeys = [ForeignKey(entity = Grocery::class,
    parentColumns = arrayOf("groceryId"),
    childColumns = arrayOf("fkGroceryId"),
    onDelete = CASCADE,
    onUpdate = CASCADE)]
)
data class GroceryItem(
    var itemName: String,
    var itemPrice: Int,
    val fkGroceryId: Long
)
{
    @PrimaryKey(autoGenerate = true)
    var itemId: Long=0
    var createdAt: Long = System.currentTimeMillis()
}


data class GroceryWithItems(

    @Embedded val grocery: Grocery,

    @Relation(
        parentColumn = "groceryId",
        entityColumn = "fkGroceryId",
    )
    val groceryItems: MutableList<GroceryItem>
)