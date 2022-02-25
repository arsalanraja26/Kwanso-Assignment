package com.kwanso.grocerylist.utils.listeners

import com.kwanso.grocerylist.datasources.entity.GroceryItem


interface GroceryDetailItemListener {

    fun onGroceryDetailItemClicked(item: GroceryItem)
}