package com.kwanso.grocerylist.datasources.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kwanso.grocerylist.datasources.database.GroceryDatabase
import com.kwanso.grocerylist.datasources.entity.GroceryItem
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalGroceryDetailRepo {

    companion object {

        private var groceryDatabase: GroceryDatabase? = null


        private fun initializeDB(context: Context) : GroceryDatabase {
            return GroceryDatabase.getDatBaseClient(context)
        }

        fun getAllGroceryItem(context: Context,parentId:Long):LiveData<GroceryWithItems>{

            groceryDatabase = initializeDB(context)

            return groceryDatabase!!.getGroceryDao().getGroceryWithItems(parentId)
        }

        fun insertGroceryItem(context: Context, groceryItem : GroceryItem) {

            groceryDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                groceryDatabase!!.getGroceryDao().insertGroceryItem(groceryItem)

            }

        }

        fun updateGroceryItem(context: Context, groceryItem : GroceryItem)  {

            groceryDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                groceryDatabase!!.getGroceryDao().updateGroceryItem(groceryItem)

            }

        }

        fun deleteGroceryItem(context: Context, groceryItem : GroceryItem) {

            groceryDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                groceryDatabase!!.getGroceryDao().deleteGroceryItem(groceryItem)

            }

        }


    }

}