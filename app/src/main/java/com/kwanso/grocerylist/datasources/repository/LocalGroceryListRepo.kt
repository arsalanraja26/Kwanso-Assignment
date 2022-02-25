package com.kwanso.grocerylist.datasources.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kwanso.grocerylist.datasources.database.GroceryDatabase
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LocalGroceryListRepo {

    companion object {

        private var groceryDatabase: GroceryDatabase? = null

        var groceryList: LiveData<MutableList<GroceryWithItems>>? = null

        private fun initializeDB(context: Context) : GroceryDatabase {
            return GroceryDatabase.getDatBaseClient(context)
        }

        fun insertGrocery(context: Context, item: Grocery) {

            groceryDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                groceryDatabase!!.getGroceryDao().insertGrocery(item)

            }

        }

        fun updateGrocery(context: Context,  item: Grocery) {

            groceryDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                groceryDatabase!!.getGroceryDao().updateGrocery(item)

            }

        }

        fun deleteGrocery(context: Context,groceryId: Long) {

            groceryDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                groceryDatabase!!.getGroceryDao().deleteGroceryWithItems(groceryId)

            }

        }

        fun getAllPendingGroceries(context: Context) :LiveData<MutableList<GroceryWithItems>>? {

            groceryDatabase = initializeDB(context)

            groceryList = groceryDatabase!!.getGroceryDao().getAllPending()

            return groceryList
        }

        fun getCompletedGroceryList(context: Context) : LiveData<MutableList<GroceryWithItems>>? {

            groceryDatabase =initializeDB(context)

            groceryList = groceryDatabase!!.getGroceryDao().getAllCompleted()

            return groceryList
        }


    }
}