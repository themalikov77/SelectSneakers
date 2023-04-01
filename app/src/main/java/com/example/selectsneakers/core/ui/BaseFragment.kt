package com.example.selectsneakers.core.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.selectsneakers.core.extension.makeToast
import com.example.selectsneakers.utils.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isAdded){
            checkInternet()
            setUpRequest()
            setUpSubscriber()
            initAdapters()
            initView()
            initObserver()
            initListeners()
        }else{
            makeToast(requireContext(),"Fragment not added")
        }
    }

    protected open fun initListeners() {}
    protected open fun setUpSubscriber() {}
    protected open fun setUpRequest() {}
    protected open fun initAdapters() {}

    protected open fun initView() {}

    protected open fun initObserver() {}

    protected open fun checkInternet() {}

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        state: ((UIState<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit
    ){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                this@collectUIState.collect{
                    state?.invoke(it)
                    when(it){
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            Log.e("ololo",it.message)
                        }
                        is UIState.Loading -> {}
                        is UIState.Success -> {
                            onSuccess(it.data)
                        }
                    }
                }
            }
        }
    }
}