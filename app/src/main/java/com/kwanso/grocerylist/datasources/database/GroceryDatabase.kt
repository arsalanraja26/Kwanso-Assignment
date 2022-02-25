package com.kwanso.grocerylist.datasources.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kwanso.grocerylist.datasources.dao.GroceryDao
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.datasources.entity.GroceryItem

@Database(entities = [Grocery::class, GroceryItem::class], version = 1, exportSchema = false)

abstract class GroceryDatabase : RoomDatabase() {

    abstract fun getGroceryDao() : GroceryDao

    companion object {

        @Volatile
        private var INSTANCE: GroceryDatabase? = null

        fun getDatBaseClient(context: Context) : GroceryDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, GroceryDatabase::class.java, "Grocery_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }


    }

}