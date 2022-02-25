package com.kwanso.grocerylist.utils.listeners

import com.kwanso.grocerylist.datasources.entity.GroceryWithItems

interface GroceryListItemListener {

    fun onGroceryListClicked(groceryWithItems: GroceryWithItems)

    fun onGroceryListLongClicked(groceryWithItems: GroceryWithItems)

}