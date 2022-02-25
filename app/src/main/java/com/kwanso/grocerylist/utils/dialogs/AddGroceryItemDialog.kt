package com.kwanso.grocerylist.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.kwanso.grocerylist.databinding.DialogGroceryDetailLayoutBinding
import com.kwanso.grocerylist.datasources.entity.GroceryItem

import com.kwanso.grocerylist.viewmodel.GroceryDetailViewModel

class AddGroceryItemDialog : DialogFragment() {

    private lateinit var groceryDetailViewModel: GroceryDetailViewModel
    private var binding: DialogGroceryDetailLayoutBinding? = null

    companion object {

        fun newInstance(parentId: String): AddGroceryItemDialog {
            val dialog = AddGroceryItemDialog()
            val args = Bundle()
            args.putString("parentId", parentId)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        dialog?.setCancelable(false)

        binding = DialogGroceryDetailLayoutBinding.inflate(inflater, container, false)
        groceryDetailViewModel = ViewModelProvider(requireParentFragment()).get(GroceryDetailViewModel::class.java)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentId = arguments?.getString("parentId","")

        binding!!.okDialogbtn.setOnClickListener {

            val itemName = binding!!.dialogNameEt.text.toString()
            val itemAmount=binding!!.dialogAmountEt.text.toString()


            if (itemName.isEmpty() || itemAmount.isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter Item Details", Toast.LENGTH_SHORT).show()
            }
            else {
                val groceryItem = GroceryItem(itemName, itemAmount.toInt(), parentId!!.toLong())
                groceryDetailViewModel.insertData(requireContext(), groceryItem)
                Toast.makeText(requireContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show()
                this.dismiss()
            }

        }

        binding!!.cancelDialogbtn.setOnClickListener {
            this.dismiss()
        }


    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, height)
    }



}