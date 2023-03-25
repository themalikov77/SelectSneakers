package com.example.selectsneakers.ui.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.extension.initHorizontalAdapter
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentHomeBinding
import com.example.selectsneakers.ui.home.adapter.RecommendAdapter
import com.example.selectsneakers.ui.productcard.adapters.SimilarShoesAdapter

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val adapterRecommend = RecommendAdapter()
    private val adapterTrend = SimilarShoesAdapter(this::onProductClick)

    companion object{
        const val KEY_FOR_PRODUCT ="PRODUCT"
    }
    override fun initView() {
        super.initView()

    }

    override fun initAdapters() {
        super.initAdapters()
        with(binding){
            recyclerDiscount.adapter = adapterRecommend
            recyclerDiscount.initHorizontalAdapter()
            recyclerTrend.layoutManager = GridLayoutManager(context,2)
            recyclerTrend.adapter = adapterTrend
        }
    }

    override fun initListeners() {
        super.initListeners()
        with(binding){
            tvTrend.setOnClickListener {
                findNavController().navigate(R.id.productcartFragment)
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
            viewModel.getShoesList().observe(viewLifecycleOwner){
                adapterRecommend.addRecommend(it)
                //adapterTrend.addSimilarShoes(it)
            }
    }

    override fun setUpSubscriber() {
        super.setUpSubscriber()
        viewModel.getProductsState.collectUIState(
            state = {

            },
            onSuccess = {
                adapterTrend.addSimilarShoes(it.results)
            }
        )
    }

    override fun setUpRequest() {
        super.setUpRequest()
        viewModel.getProducts()
    }

    private fun onProductClick(id:Int){
        findNavController().navigate(R.id.productcartFragment, bundleOf(KEY_FOR_PRODUCT to id))
    }

}