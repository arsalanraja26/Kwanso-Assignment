package com.kwanso.grocerylist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwanso.grocerylist.databinding.RowDetailGroceryBinding
import com.kwanso.grocerylist.datasources.entity.GroceryItem
import com.kwanso.grocerylist.utils.DateUtils
import com.kwanso.grocerylist.utils.listeners.GroceryDetailItemListener

class GroceryDetailAdapter (var groceryDetailItemsList:MutableList<GroceryItem>,
                            private var groceryDetailItemClicked: GroceryDetailItemListener
) : RecyclerView.Adapter<GroceryDetailAdapter.GroceryDetailViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryDetailViewHolder {
        return GroceryDetailViewHolder(RowDetailGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = groceryDetailItemsList.size

    override fun onBindViewHolder(holder: GroceryDetailViewHolder, position: Int) {
        holder.bind(groceryDetailItemsList[position],groceryDetailItemClicked)
    }

    fun removeAt(position: Int) : GroceryItem {
        val obj=groceryDetailItemsList.removeAt(position)
        notifyItemRemoved(position)
        return obj
    }


    class GroceryDetailViewHolder(rowBinding: RowDetailGroceryBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {

        private val binding = rowBinding

        @SuppressLint("SetTextI18n")
        fun bind(groceryDetailObj: GroceryItem, groceryDetailItemClicked: GroceryDetailItemListener) {

            binding.itemName.text = groceryDetailObj.itemName
            binding.itemAmount.text = groceryDetailObj.itemPrice.toString()+ " $"
            binding.createdAt.text = DateUtils.convertLongToTime(groceryDetailObj.createdAt)

            binding.root.setOnClickListener {
                groceryDetailItemClicked.onGroceryDetailItemClicked(groceryDetailObj)
            }
        }
    }


}