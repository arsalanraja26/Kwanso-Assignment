package com.kwanso.grocerylist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kwanso.grocerylist.R
import com.kwanso.grocerylist.databinding.RowGroceryHomeBinding
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import com.kwanso.grocerylist.utils.DateUtils
import com.kwanso.grocerylist.utils.listeners.GroceryListItemListener


class GroceryListAdapter (var groceryItemsList: MutableList<GroceryWithItems>,
                          var groceryListItemClicked: GroceryListItemListener) :
    RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListViewHolder {
        return GroceryListViewHolder(RowGroceryHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = groceryItemsList.size

    override fun onBindViewHolder(holder: GroceryListViewHolder, position: Int) {
        holder.bind(groceryItemsList[position],groceryListItemClicked)
    }

    fun removeAt(position: Int) :GroceryWithItems {
        val obj=groceryItemsList.removeAt(position)
        notifyItemRemoved(position)
        return obj
    }


    class GroceryListViewHolder(rowBinding: RowGroceryHomeBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        @SuppressLint("SetTextI18n")
        fun bind(groceryObj: GroceryWithItems, groceryListItemClicked: GroceryListItemListener) {

            binding.rowGroceryName.text = groceryObj.grocery.groceryName
            binding.itemsAmount.text = groceryObj.groceryItems.sumOf { it.itemPrice }.toString()+ " $"
            binding.itemCount.text = groceryObj.groceryItems.size.toString()
            binding.itemCreatedAt.text = DateUtils.convertLongToTime(groceryObj.grocery.createdAt)

            binding.itemStatus.text = if (groceryObj.grocery.groceryStatus) "Completed" else "Pending"
            binding.root.background.setTint(
                ContextCompat.getColor(binding.root.context,
                if (groceryObj.grocery.groceryStatus) R.color.lightGray else R.color.white))

            binding.root.setOnClickListener {
                groceryListItemClicked.onGroceryListClicked(groceryObj)
            }
            binding.root.setOnLongClickListener{
                groceryListItemClicked.onGroceryListLongClicked(groceryObj)
                true
            }
        }
    }
}