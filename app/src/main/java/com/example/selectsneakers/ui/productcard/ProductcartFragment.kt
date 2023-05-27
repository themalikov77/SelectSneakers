package com.example.selectsneakers.ui.productcard

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.extension.*
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.Favorite
import com.example.selectsneakers.data.remote.model.Product
import com.example.selectsneakers.databinding.FragmentProductcartBinding
import com.example.selectsneakers.ui.home.HomeFragment
import com.example.selectsneakers.ui.productcard.adapters.ColorShoesAdapter
import com.example.selectsneakers.ui.productcard.adapters.ReviewAdapter
import com.example.selectsneakers.ui.productcard.adapters.ShoesPagerAdapter
import com.example.selectsneakers.ui.productcard.adapters.SimilarShoesAdapter
import com.example.selectsneakers.utils.UIState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProductcartFragment : BaseFragment(R.layout.fragment_productcart) {

    private val binding by viewBinding(FragmentProductcartBinding::bind)
    private var shoesPagerAdapter = ShoesPagerAdapter()
    private val adapterColor = ColorShoesAdapter()
    private val adapterSimilar = SimilarShoesAdapter(this::onProductClick)
    private val adapterReview = ReviewAdapter()
    private val viewModel: ProductCardViewModel by viewModels()
    private var ratingCount = 0
    private val sizeOfShoes = ArrayList<String>()
    private var id = 1
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")
    private var imagesId = 1
    private var isInMyFavorite = false
    private var isInMyCart = false
    var currentPage = 1
    private var isStart = false
    private var totalCount: Int = 1
    private val listImage = arrayListOf<Product>()
    private var imgFavorite: String =
        "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg"


    companion object {
        const val TEXT_SEND = "Отправить"
        const val TEXT_WRITE_REVIEW = "Написать отзыв"
        const val WRITE_NEW_REVIEW = "Написать новый отзыв"
        const val KEY_FOR_PRODUCT_CART2 = "key_PC2"
        const val KEY_FOR_PRODUCT_CART_IMAGES2 = "key_PC_IMG2"
    }


    override fun initView() {
        initRating()
        initTextFabric()
        initReductor()
        editReviewNameCheck(
            editText = binding.editReview,
            editTextContainer = binding.editReviewContainer
        )
    }

    override fun initAdapters() {
        with(binding) {

            shoesPager.adapter = shoesPagerAdapter
            recyclerColor.initHorizontalAdapter()
            recyclerColor.adapter = adapterColor
            recyclerSimilar.initHorizontalAdapter()
            recyclerSimilar.adapter = adapterSimilar

            setUpPagination()
            recyclerRreview.initHorizontalAdapter()
            recyclerRreview.adapter = adapterReview

        }
    }

    private fun setUpPagination() {
        binding.recyclerSimilar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (adapterSimilar.itemCount < totalCount) {
                    if (lastItem == adapterSimilar.itemCount - 1) {
                        currentPage++
                        viewModel.getProducts(currentPage)
                    }
                }
            }
        })
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.getShoesColor().observe(viewLifecycleOwner) {
            adapterColor.addColorList(it)
        }
        viewModel.getReview().observe(viewLifecycleOwner) {
            adapterReview.addReview(it)
        }


    }

    @SuppressLint("SetTextI18n")
    override fun initListeners() {
        with(binding) {
            textReadMore.setOnClickListener {
                findNavController().navigate(R.id.allReviewsFragment)
            }
            ratingBar.setOnRatingBarChangeListener { ratingBar, _, _ ->
                textScore.text = "${ratingBar.rating.toInt()}/5"
                ratingCount = ratingBar.rating.toInt()
            }

            btnBuy.setOnClickListener {
                BuySheetFragment().show(parentFragmentManager, "buySheetTag")
            }

            btnAdd.setOnClickListener {
                if (!isInMyCart) {
                    if (isAdded && activity != null) {
                        addToRealtimeData("Cart")
                    }
                }
            }

            btnIsAdd.setOnClickListener {
                if (isInMyCart) {
                    if (isAdded && activity != null) {
                        removeFromFavorite("Cart")
                    }
                }
            }

            btnFavorite.setOnClickListener {
                if (!isInMyFavorite) {
                    if (isAdded && activity != null) {
                        addToRealtimeData("Favorite")
                    }
                }
            }
            btnIsFavorite.setOnClickListener {
                if (isInMyFavorite) {

                    if (isAdded && activity != null) {
                        removeFromFavorite("Favorite")
                    }

                }
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            reviewClick()
        }
    }

    private fun checkFavorite(child: String) {
        db.child(mAuth.uid.toString()).child(child).child(id.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (child == "Favorite") {
                        isInMyFavorite = snapshot.exists()

                        if (isAdded && activity != null) {

                            binding.btnIsFavorite.isVisible = isInMyFavorite
                            binding.btnFavorite.isVisible = !isInMyFavorite
                        }

                    } else if (child == "Cart") {
                        isInMyCart = snapshot.exists()

                        if (isAdded && activity != null) {
                            binding.btnAdd.isVisible = !isInMyCart
                            binding.btnIsAdd.isVisible = isInMyCart
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    makeToast(requireContext(), error.toString())
                }
            })
    }

    private fun removeFromFavorite(child: String) {
        db.child(mAuth.uid.toString()).child(child).child(id.toString()).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    makeToast(requireContext(), "Successfuly deleted")
                } else {
                    makeToast(requireContext(), "Error of deleting")
                }
            }
    }

    private fun addToRealtimeData(child: String) {
        with(binding) {
            val model = Favorite(
                id = id.toString(),
                img = imgFavorite,
                description = description.text.toString(),
                color = "no coler",
                size = "44",
                price = price.text.toString(),
                name = name.text.toString()
            )
            db.child(mAuth.uid.toString()).child(child).child(model.id.toString())
                .setValue(model)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "not add", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.dropDownMenuBox.boxStrokeColor = Color.BLUE

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, sizeOfShoes)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.inputType
    }

    override fun setUpRequest() {
        receiveId()
        viewModel.getProductDetailList(id)
        viewModel.getImagesById(imagesId)
        if (!isStart) {
            viewModel.getProducts(currentPage)
            isStart = true
        }

    }

    @SuppressLint("SetTextI18n")
    override fun setUpSubscriber() {
        with(binding) {
            viewModel.getProductDetailState.collectUIState(
                state = { state ->
                    binding.progressBar.isVisible = state is UIState.Loading
                },
                onSuccess = {

                    imagesId = it.images[0]
                    price.text = "${it.price.toDouble().toInt()}c"
                    name.text = it.name
                    description.text = it.description
                    autoCompleteTextView.setText(it.size.toString())
                    sizeOfShoes.add(it.size.toString())

                }
            )

            viewModel.getImagesByIdState.collectUIState(
                state = {
                    //binding.progress.progressContainer.isVisible = state is UIState.Loading
                },
                onSuccess = {
                    //shoesPagerAdapter.addShoes(it.)
                }
            )

            viewModel.getProductsState.collectUIState(
                state = {

                },
                onSuccess = {

                    listImage.addAll(it.results)
                    totalCount = it.count
                    adapterSimilar.addSimilarPage(it.results)
                }
            )
        }
        checkFavorite("Cart")
        checkFavorite("Favorite")
    }

    private fun receiveId() {
        arguments?.let {
            id = it.getInt(ProductCartFragment2.KEY_FOR_PRODUCT_CART)
            val images = it.getStringArrayList(ProductCartFragment2.KEY_FOR_PRODUCT_CART_IMAGES)
            imgFavorite = images?.get(0)
                ?: "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg"
            images?.let { it1 -> shoesPagerAdapter.addShoes(it1) }
        }
        arguments?.let {
            if (id == 0) {
                id = it.getInt(HomeFragment.KEY_FOR_PRODUCT)
                val listImage = it.getStringArrayList(HomeFragment.KEY_FOR_PRODUCT_IMAGES)!!
                imgFavorite = listImage[0]
                    ?: "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg"
                shoesPagerAdapter.addShoes(listImage)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun initReductor() {
        val popupMenu = PopupMenu(requireContext(), binding.btnReductReview)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.popup_menu, popupMenu.menu)
        with(binding) {
            btnReductReview.setOnClickListener {
                popupMenu.show()
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog, null)
                        val dialog = Dialog(requireContext())
                        dialog.setContentView(dialogBinding)
                        dialog.setCancelable(true)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                        val deleteDialog = dialog.findViewById<TextView>(R.id.btnDelete)
                        val cancelDialog = dialog.findViewById<TextView>(R.id.btnCancel)
                        reductDeleteCard.visibility = View.GONE
                        deleteDialog.setOnClickListener {
                            makeToast(requireContext(), "delete")
                            dialog.dismiss()
                        }
                        cancelDialog.setOnClickListener {
                            makeToast(requireContext(), "cancel")
                            dialog.dismiss()
                        }
                        true
                    }
                    R.id.reduct -> {
                        //TODO do reduct
                        true
                    }
                    else -> true
                }
            }
        }
    }


    private fun initTextFabric() {
        with(binding) {
            textFabric2.textGrey.text = "Высота обуви: "
            textFabric2.textBlack.text = "низкие"
            textFabric3.textGrey.text = "Материал подошвы: "
            textFabric3.textBlack.text = "ПВХ; фос"
            textFabric4.textGrey.text = "Страна производства: "
            textFabric4.textBlack.text = "Европа"
            textFabric5.textGrey.text = "Бренд: "
            textFabric5.textBlack.text = getString(R.string.Nike)
        }
    }

    private fun initRating() {
        with(binding) {
            ratingLine1.progressReviews.max = 100
            ratingLine1.progressReviews.progress = 50

            //line2
            ratingLine2.progressReviews.max = 100
            ratingLine2.progressReviews.progress = 20
            ratingLine2.star5.star1.visibility = View.INVISIBLE


            //line3
            ratingLine3.progressReviews.max = 100
            ratingLine3.progressReviews.progress = 10
            ratingLine3.star5.star1.visibility = View.INVISIBLE
            ratingLine3.star5.star2.visibility = View.INVISIBLE

            //line4
            ratingLine4.progressReviews.max = 100
            ratingLine4.progressReviews.progress = 5
            ratingLine4.star5.star1.visibility = View.INVISIBLE
            ratingLine4.star5.star2.visibility = View.INVISIBLE
            ratingLine4.star5.star3.visibility = View.INVISIBLE

            //line5
            ratingLine5.progressReviews.max = 100
            ratingLine5.progressReviews.progress = 15
            ratingLine5.star5.star1.visibility = View.INVISIBLE
            ratingLine5.star5.star2.visibility = View.INVISIBLE
            ratingLine5.star5.star3.visibility = View.INVISIBLE
            ratingLine5.star5.star4.visibility = View.INVISIBLE

        }

    }

    private fun onProductClick(id: Int) {

        val images = arrayListOf<String>()
        listImage[id - 1].images.forEach {
            images.add(it.image)
        }
        Log.e("ololo", "hello bro: $images")

        findNavController().navigate(
            R.id.productCartFragment,
            bundleOf(KEY_FOR_PRODUCT_CART2 to id, KEY_FOR_PRODUCT_CART_IMAGES2 to images)
        )
    }

    private fun reviewClick() {
        with(binding) {
            btnReview.setOnClickListener {
                if (btnReview.text == TEXT_SEND) {
                    if (editReview.text?.isEmpty() == true) {
                        editReviewContainer.boxStrokeWidth = 3
                        editReviewContainer.helperText = "Write review"
                        editReview.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_error_edittext
                        )
                        reviewLogic(reviewAnswer = false)
                    } else if (editReview.text?.isNotEmpty() == true) {
                        editReviewContainer.boxStrokeWidth = 0
                        editReviewContainer.helperText = null
                        editReview.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_review_edittext
                        )
                        reviewLogic(reviewAnswer = true)
                    }
                } else {
                    reviewLogic(reviewAnswer = false)
                }
            }
        }
    }


    private fun reviewLogic(reviewAnswer: Boolean) {
        with(binding) {
            if (btnReview.text == TEXT_WRITE_REVIEW) {
                btnReview.text = TEXT_SEND
                containerOfRatingAndReview.visibility = View.VISIBLE
                ratingBar.isEnabled = true
                reviewContainer.visibility = View.VISIBLE
            } else if (btnReview.text == TEXT_SEND) {
                if (reviewAnswer && ratingCount > 0) {
                    btnReview.text = WRITE_NEW_REVIEW
                    editReview.visibility = View.GONE
                    doneReview.visibility = View.VISIBLE
                    reviewContainer.visibility = View.VISIBLE
                    ratingBar.isEnabled = false
                    ratingBar.progressTintList = ColorStateList.valueOf(Color.YELLOW)
                } else {
                    ratingBar.isEnabled = true
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }

            } else if (btnReview.text == WRITE_NEW_REVIEW) {

                btnReview.text = TEXT_SEND
                containerOfRatingAndReview.visibility = View.VISIBLE
                reviewContainer.visibility = View.VISIBLE
                editReview.visibility = View.VISIBLE
                doneReview.visibility = View.GONE
            }
        }
    }

    private fun editReviewNameCheck(
        editText: TextInputEditText,
        editTextContainer: TextInputLayout
    ) {
        editText.doOnTextChanged { text, _, _, _ ->
            if (editText.text?.isNotEmpty() == true) {
                if (text!!.length < 300) {
                    editTextContainer.boxStrokeWidth = 0
                    editTextContainer.helperText = null
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_review_edittext
                    )
                } else {
                    editTextContainer.boxStrokeWidth = 3
                    editTextContainer.helperText = "Max length of simvols"
                    editText.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_error_edittext
                    )
                }
            }

        }
    }


}