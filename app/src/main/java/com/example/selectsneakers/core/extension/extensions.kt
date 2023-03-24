package com.example.selectsneakers.core.extension

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selectsneakers.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun RecyclerView.initHorizontalAdapter(){
    layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
}

fun makeToast(context: Context,text: String){
    Toast.makeText(context,text,Toast.LENGTH_LONG).show()
}


fun TextInputEditText.emailCheck(): Boolean{
 return Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}

fun EditText.nameReviewCheck():Boolean{
    return text?.isNotEmpty()==true
}

 fun nameReviewChecker2(
    editText: TextInputEditText,
    editContainer: TextInputLayout,
    errorHelperText: String,
    requireContext: Context
): Boolean {
    var answer = false

    if (!editText.nameReviewCheck()) {
        editContainer.boxStrokeWidth = 3
        editContainer.helperText = errorHelperText
        editText.background = ContextCompat.getDrawable(
            requireContext,
            R.drawable.bg_error_edittext
        )
        answer = false
    } else if (editText.nameReviewCheck()) {
        editContainer.boxStrokeWidth = 0
        editContainer.helperText = null
        editText.background = ContextCompat.getDrawable(
            requireContext,
            R.drawable.bg_review_edittext
        )
        answer = true
    }
    return answer
}
