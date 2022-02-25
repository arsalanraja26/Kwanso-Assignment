package com.kwanso.grocerylist.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwanso.grocerylist.adapters.GroceryDetailAdapter
import com.kwanso.grocerylist.databinding.FragmentDetailGroceryBinding
import com.kwanso.grocerylist.datasources.entity.GroceryItem
import com.kwanso.grocerylist.utils.dialogs.AddGroceryItemDialog
import com.kwanso.grocerylist.utils.SwipeToDeleteUtil
import com.kwanso.grocerylist.utils.listeners.GroceryDetailItemListener
import com.kwanso.grocerylist.viewmodel.GroceryDetailViewModel


class GroceryDetailFragment : Fragment(),GroceryDetailItemListener {

    private var binding: FragmentDetailGroceryBinding? = null
    private lateinit var groceryDetailViewModel: GroceryDetailViewModel
    private lateinit var groceryDetailAdapter : GroceryDetailAdapter
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailGroceryBinding.inflate(inflater, container, false)
        groceryDetailViewModel = ViewModelProvider(this).get(GroceryDetailViewModel::class.java)
        mContext=this@GroceryDetailFragment.requireActivity()

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentId= requireArguments().getString("GroceryListItem_ID","")
        val comingFrom= requireArguments().getString("ComingFrom","")


        initBindings()

        if(!comingFrom.equals("Home")){
            binding!!.groceryDetailFab.visibility=View.GONE
        }


        groceryDetailViewModel.getAllGroceryItems(mContext, parentId.toLong()
        )?.observe(viewLifecycleOwner, {
            groceryDetailAdapter.groceryDetailItemsList = it.groceryItems
            groceryDetailAdapter.notifyDataSetChanged()
        })

        binding!!.groceryDetailFab.setOnClickListener {
            val fragment = AddGroceryItemDialog.newInstance(parentId)
            fragment.show(childFragmentManager, "MyCustomDetailFragment")
        }

    }

    private fun initBindings() {

        groceryDetailAdapter = GroceryDetailAdapter(mutableListOf(),this)
        val linearLayoutManager = LinearLayoutManager(mContext)
        binding!!.detailGroceryRecylerView.apply {
            adapter = groceryDetailAdapter
            layoutManager = linearLayoutManager
        }

        val swipeHandler = object : SwipeToDeleteUtil(mContext) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding!!.detailGroceryRecylerView.adapter as GroceryDetailAdapter
                val groceryObj=adapter.removeAt(viewHolder.adapterPosition)

                groceryDetailViewModel.deleteGroceryItem(mContext, groceryObj)
                Toast.makeText(mContext, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding!!.detailGroceryRecylerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onGroceryDetailItemClicked(item: GroceryItem) {

    }


}