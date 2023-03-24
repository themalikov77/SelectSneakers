package com.example.selectsneakers.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.selectsneakers.App.Companion.repository
import com.example.selectsneakers.model.PaymentModel

class PayViewModel: ViewModel() {
    fun getPay(
        card_number: String,
        cvc_code: String,
        date: String,
    ): LiveData<PaymentModel> {
        return repository.getPay(card_number, cvc_code, date)
    }
}