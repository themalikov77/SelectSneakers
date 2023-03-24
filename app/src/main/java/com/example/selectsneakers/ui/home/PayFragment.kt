package com.example.selectsneakers.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selectsneakers.R
import com.example.selectsneakers.databinding.FragmentPayBinding

class PayFragment : Fragment() {
    private lateinit var binding: FragmentPayBinding
    private lateinit var editTextCard: EditText
    private lateinit var editTextValidity: EditText
    private lateinit var editTextCvv: EditText

    private val viewModel: PayViewModel by lazy {
        ViewModelProvider(this)[PayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBinding.inflate(inflater, container, false)
        editTextCard = binding.numberCardEditText
        editTextValidity = binding.validityEditText
        editTextCvv = binding.cvvEditText
        viewModel.getPay(
            editTextCard.toString(),
            editTextValidity.toString(),
            editTextCvv.toString()
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicker()

    }

    @SuppressLint("InflateParams")
    private fun initClicker() {
        binding.acceptBtn.setOnClickListener {
            val cardNumber = editTextCard.text.toString()
            val cvv = editTextCvv.text.toString()
            val validity = editTextValidity.text.toString()
            val errorText = binding.errorTextImageView
            val unCorrectNumberTextImage = binding.uncorrectNumberImageView

            editTextCard.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    errorText.visibility = View.GONE
                    unCorrectNumberTextImage.visibility = View.GONE

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })
            editTextCvv.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    errorText.visibility = View.GONE
                    unCorrectNumberTextImage.visibility = View.GONE
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })
            editTextValidity.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    errorText.visibility = View.GONE
                    unCorrectNumberTextImage.visibility = View.GONE

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })

            var isCardNumberCorrect = true
            var isCvvCorrect = true
            var isValidityCorrect = true

            if (cardNumber.length != 19) {
                isCardNumberCorrect = false
            }

            if (cvv.length != 3) {
                isCvvCorrect = false
            }

            if (validity.length != 5) {
                isValidityCorrect = false
            }

            if (isCardNumberCorrect && isCvvCorrect && isValidityCorrect) {
                errorText.visibility = View.GONE
                unCorrectNumberTextImage.visibility = View.GONE
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog, null)
                val dialog = Dialog(requireContext())
                dialog.setContentView(dialogBinding)
                dialog.setCancelable(true)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                val deleteDialog = dialog.findViewById<TextView>(R.id.saveBtn)
                val cancelDialog = dialog.findViewById<TextView>(R.id.btnCancel)
                deleteDialog.setOnClickListener {
                    findNavController().navigate(R.id.succsessFragment)
                    dialog.dismiss()
                }
                cancelDialog.setOnClickListener {
                    findNavController().navigate(R.id.succsessFragment)
                    dialog.dismiss()
                }

            } else {
                errorText.visibility = View.VISIBLE
                unCorrectNumberTextImage.visibility = View.VISIBLE
                binding.numberCardEditText.setBackgroundResource(R.drawable.error_border)
                binding.cvvEditText.setBackgroundResource(R.drawable.error_border)
                binding.validityEditText.setBackgroundResource(R.drawable.error_border)
            }
        }
    }
}
