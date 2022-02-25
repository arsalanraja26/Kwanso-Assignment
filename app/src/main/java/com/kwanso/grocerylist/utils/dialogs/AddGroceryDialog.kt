package com.kwanso.grocerylist.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.kwanso.grocerylist.databinding.DialogGroceryItemBinding
import com.kwanso.grocerylist.datasources.entity.Grocery
import com.kwanso.grocerylist.viewmodel.GroceryHomeViewModel
import com.kwanso.grocerylist.viewmodel.GroceryListViewModel

class AddGroceryDialog : DialogFragment() {

    private lateinit var groceryHomeViewModel: GroceryHomeViewModel
    private var binding: DialogGroceryItemBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setCancelable(false)
        binding = DialogGroceryItemBinding.inflate(inflater, container, false)
        groceryHomeViewModel = ViewModelProvider(requireParentFragment()).get(GroceryHomeViewModel::class.java)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding!!.okbtn.setOnClickListener {

            val itemName = binding!!.dialogNameEt.text.toString()


            if (itemName.isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter Item Name", Toast.LENGTH_SHORT).show()
            }
            else {
                groceryHomeViewModel.insertGrocery(requireContext(), Grocery(itemName,false))
                Toast.makeText(requireContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show()
                this.dismiss()
            }

        }

        binding!!.cancelbtn.setOnClickListener {
            //dismiss dialog
            this.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.30).toInt()
        dialog!!.window?.setLayout(width, height)
    }

}