package com.example.selectsneakers.ui.home

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.extension.initHorizontalAdapter
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.Product
import com.example.selectsneakers.databinding.FragmentHomeBinding
import com.example.selectsneakers.ui.home.adapter.BrandsAdapter
import com.example.selectsneakers.ui.home.adapter.RecommendAdapter
import com.example.selectsneakers.ui.productcard.adapters.SimilarShoesAdapter

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val adapterRecommend = RecommendAdapter()
    private val adapterTrend = SimilarShoesAdapter(this::onProductClick)
    private val adapterBrands = BrandsAdapter()
    private val listImage = arrayListOf<String>()
    var isLoading = false
    var isLastPage = false
    var currentPage = 1
    var isStart = false
    private var totalCount: Int = 1
    private var pageToken: String? = null

    companion object {
        const val KEY_FOR_PRODUCT = "PRODUCT"
    }

    override fun initAdapters() {
        super.initAdapters()
        with(binding) {
            recyclerDiscount.initHorizontalAdapter()
            recyclerDiscount.adapter = adapterRecommend

            recyclerTrend.layoutManager =  GridLayoutManager(context, 2)
            recyclerTrend.adapter = adapterTrend
            setUpPagination()

            recyclerBrands.initHorizontalAdapter()
            recyclerBrands.adapter = adapterBrands
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.getShoesList().observe(viewLifecycleOwner) {
            adapterRecommend.addRecommend(it)
        }
        viewModel.getBrands().observe(viewLifecycleOwner) {
            adapterBrands.addBrands(it)
        }
    }

    override fun setUpSubscriber() {
        super.setUpSubscriber()
        viewModel.getProductsState.collectUIState(
            state = {

            },
            onSuccess = {
                //adapterTrend.addSimilarPage(it.results)
                Log.e("ololo","page: ${it.next}")
                pageToken = it.next
                totalCount = it.count
                adapterTrend.addSimilarPage(it.results)
            }
        )
    }

    private fun setUpPagination(){
        binding.recyclerTrend.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                if (!isLoading &&adapterTrend.itemCount < totalCount){
                    if (lastItem == adapterTrend.itemCount -1 ){
                        currentPage++
                        viewModel.getProducts(currentPage)
                    }
                }
            }
        })
    }


    override fun setUpRequest() {
        super.setUpRequest()
        if (!isStart){
            viewModel.getProducts(currentPage)
            isStart = true
        }
    }

    private fun onProductClick(id: Int) {
        Log.e("ololo", "by: $id")
        // listImage.add(it.results[0].images[0].image)
        findNavController().navigate(R.id.productcartFragment, bundleOf(KEY_FOR_PRODUCT to id))
    }

}