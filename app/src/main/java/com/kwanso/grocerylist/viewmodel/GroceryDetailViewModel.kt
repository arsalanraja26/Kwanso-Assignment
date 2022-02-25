package com.kwanso.grocerylist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kwanso.grocerylist.datasources.entity.GroceryItem
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import com.kwanso.grocerylist.datasources.repository.LocalGroceryDetailRepo


class GroceryDetailViewModel : ViewModel() {

    private var groceryItemsList: LiveData<GroceryWithItems>? = null

    fun insertData(context: Context, item: GroceryItem) {
        LocalGroceryDetailRepo.insertGroceryItem(context, item)
    }

    fun getAllGroceryItems(context: Context, parentId: Long) : LiveData<GroceryWithItems>? {
        groceryItemsList=  LocalGroceryDetailRepo.getAllGroceryItem(context, parentId)
        return groceryItemsList
    }

    fun deleteGroceryItem(context: Context, groceryItem : GroceryItem) {
        LocalGroceryDetailRepo.deleteGroceryItem(context,groceryItem)

    }

}