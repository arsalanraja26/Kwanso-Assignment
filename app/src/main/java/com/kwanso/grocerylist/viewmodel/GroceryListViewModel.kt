package com.kwanso.grocerylist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import com.kwanso.grocerylist.datasources.repository.LocalGroceryListRepo

class GroceryListViewModel : ViewModel() {


    private var liveDataGrocery: LiveData<MutableList<GroceryWithItems>>? = null

    fun getAllCompletedGroceries(context: Context) : LiveData<MutableList<GroceryWithItems>>? {
        liveDataGrocery = LocalGroceryListRepo.getCompletedGroceryList(context)
        return liveDataGrocery
    }

    fun updateGrocery(context: Context,grocery: Grocery) {
        LocalGroceryListRepo.updateGrocery(context,grocery)
    }

    fun deleteGrocery(context: Context,groceryId:Long) {
        LocalGroceryListRepo.deleteGrocery(context,groceryId)

    }




}