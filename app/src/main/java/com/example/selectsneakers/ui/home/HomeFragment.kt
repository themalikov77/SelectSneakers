package com.example.selectsneakers.ui.home

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
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
import com.example.selectsneakers.utils.UIState

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val adapterRecommend = RecommendAdapter()
    private val adapterTrend = SimilarShoesAdapter(this::onProductClick)
    private val adapterBrands = BrandsAdapter()
    private val listImage = arrayListOf<Product>()
    var isLoading = false
    var doYouSeeShow = false
    var currentPage = 1
    var isStart = false
    private var totalCount: Int = 1
    private var pageToken: String? = null

    companion object {
        const val KEY_FOR_PRODUCT = "PRODUCT"
        const val KEY_FOR_PRODUCT_IMAGES = "PRODUCT_IMAGES"
    }


    override fun initAdapters() {
        super.initAdapters()
        with(binding) {
            recyclerDiscount.initHorizontalAdapter()
            recyclerDiscount.adapter = adapterRecommend


            recyclerTrend.layoutManager = GridLayoutManager(context, 2)
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
                if (!isLoading) {
                    binding.progressBar.isVisible = it is UIState.Loading
                    binding.bottomProgress.isVisible = false
                } else {
                    binding.progressBar.isVisible = false
                    binding.bottomProgress.isVisible = it is UIState.Loading
                }
                //binding.progress.progress.isVisible = it is UIState.Loading
                // binding.imgProgress.isVisible = it is UIState.Loading
                isLoading = true
            },
            onSuccess = {


                listImage.addAll(it.results)

                // Log.e("ololo", "page: $listImage")
                pageToken = it.next
                totalCount = it.count
                adapterTrend.addSimilarPage(it.results)
            }
        )
    }

    private fun setUpPagination() {
        binding.recyclerTrend.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                if (adapterTrend.itemCount < totalCount) {
                    if (lastItem == adapterTrend.itemCount - 1) {
                        currentPage++
                        viewModel.getProducts(currentPage)
                    }
                }
            }
        })
    }

    override fun setUpRequest() {
        super.setUpRequest()
        if (!isStart) {
            viewModel.getProducts(currentPage)
            isStart = true
        }
    }

    private fun onProductClick(id: Int) {
        val images = arrayListOf<String>()
        val bundle = Bundle()
        listImage[id - 1].images.forEach {
            images.add(it.image)
        }
        findNavController().navigate(
            R.id.productcartFragment, bundleOf(
                KEY_FOR_PRODUCT to id,
                KEY_FOR_PRODUCT_IMAGES to images
            )
        )
    }

}