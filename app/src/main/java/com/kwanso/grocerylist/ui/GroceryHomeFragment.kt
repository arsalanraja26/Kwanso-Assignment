package com.kwanso.grocerylist.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwanso.grocerylist.R
import com.kwanso.grocerylist.adapters.GroceryListAdapter
import com.kwanso.grocerylist.databinding.FragmentGroceryHomeBinding
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.datasources.entity.GroceryWithItems
import com.kwanso.grocerylist.utils.SwipeToDeleteUtil
import com.kwanso.grocerylist.utils.dialogs.AddGroceryDialog
import com.kwanso.grocerylist.utils.listeners.GroceryListItemListener
import com.kwanso.grocerylist.viewmodel.GroceryHomeViewModel

class GroceryHomeFragment : Fragment(), GroceryListItemListener {

    private var binding: FragmentGroceryHomeBinding? = null
    private lateinit var groceryHomeViewModel: GroceryHomeViewModel
    private lateinit var groceryListAdapter : GroceryListAdapter
    lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroceryHomeBinding.inflate(inflater, container, false)
        groceryHomeViewModel = ViewModelProvider(this).get(GroceryHomeViewModel::class.java)
        mContext=this@GroceryHomeFragment.requireActivity()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBindings()

        groceryHomeViewModel.getAllPendingGroceries(mContext)?.observe(viewLifecycleOwner, {
            groceryListAdapter.groceryItemsList = it
            groceryListAdapter.notifyDataSetChanged()
        })

        binding!!.itemAddFab.setOnClickListener {
            AddGroceryDialog().show(childFragmentManager, "MyCustomFragment")
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initBindings() {

        groceryListAdapter = GroceryListAdapter(mutableListOf(),this)
        val linearLayoutManager = LinearLayoutManager(mContext)
        binding!!.homeGroceryRecylerView.apply {
            adapter = groceryListAdapter
            layoutManager = linearLayoutManager
        }

        val swipeHandler = object : SwipeToDeleteUtil(mContext) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding!!.homeGroceryRecylerView.adapter as GroceryListAdapter
                val groceryObj=adapter.removeAt(viewHolder.adapterPosition)

                groceryHomeViewModel.deleteGrocery(mContext, groceryObj.grocery.groceryId)
                Toast.makeText(mContext, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding!!.homeGroceryRecylerView)
    }


    override fun onGroceryListClicked(groceryWithItems: GroceryWithItems) {
        val bundle = bundleOf("GroceryListItem_ID" to groceryWithItems.grocery.groceryId.toString(),
            "ComingFrom" to "Home"
            )
        Navigation.findNavController(binding!!.root).navigate(R.id.action_homeGroceryFragment_to_createListFragment,bundle)
    }

    override fun onGroceryListLongClicked(groceryWithItems: GroceryWithItems) {
        showUpdateConfirmation(groceryWithItems.grocery)
    }

    private fun showUpdateConfirmation(grocery: Grocery){

        val dialogBuilder = AlertDialog.Builder(mContext)

        dialogBuilder.setMessage("Do you want to change the status ?")
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, id ->

                dialog.dismiss()

                try {
                    grocery.let {
                        it.groceryStatus = !it.groceryStatus
                        groceryHomeViewModel.updateGrocery(mContext, it)
                    }
                }
                finally {
                    Toast.makeText(mContext, "Updated Successfully", Toast.LENGTH_SHORT).show()
                }

            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Grocery Status")
        alert.show()
    }


}