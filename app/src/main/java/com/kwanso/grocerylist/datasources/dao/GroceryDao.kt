package com.kwanso.grocerylist.datasources.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.datasources.entity.GroceryItem
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems

@Dao
interface GroceryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrocery(grocery: Grocery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceryItem(grocery: GroceryItem)

    @Transaction
    @Query("SELECT * FROM Grocery WHERE groceryId = :groceryId")
    fun getGroceryWithItems(groceryId: Long): LiveData<GroceryWithItems>


    @Transaction
    @Query("SELECT * FROM Grocery  WHERE Grocery.groceryStatus=0")
    fun getAllPending() : LiveData<MutableList<GroceryWithItems>>

    @Transaction
    @Query("SELECT * FROM Grocery  WHERE Grocery.groceryStatus=1")
    fun getAllCompleted() : LiveData<MutableList<GroceryWithItems>>

    @Transaction
    @Query("DELETE FROM Grocery WHERE groceryId = :groceryId")
    fun deleteGroceryWithItems(groceryId: Long)

    @Delete
    fun deleteGroceryItem(item: GroceryItem)

    @Update
    fun updateGrocery(item: Grocery)

    @Update
    fun updateGroceryItem(item: GroceryItem)

}