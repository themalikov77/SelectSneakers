package com.example.selectsneakers.ui.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.selectsneakers.R
import com.example.selectsneakers.core.extension.makeToast
import com.example.selectsneakers.databinding.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var launcher2: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    var name: String = "Имя и Фамилие"
    var img: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {

                    if (result.data?.data != null) {
                        //setProfileImage(imageUri)
                        img = result.data?.data.toString()
                        addToRealTimeData(img.toString(),"Image")
                        Log.e("ololo","hello${imageUri.toString()}")
                    }
                }
            }

        launcher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    bitmap = result.data?.extras?.get("data") as Bitmap
                   // setPhoto(bitmap)
                    img = bitmap.toString()
                    addToRealTimeData(img.toString(),"Image")
                    Log.e("ololo","goot:${bitmap.toString()}")
                }
            }

        getData("Image")
        getData("Name")
        initViews()
        initListeners()
        initListener()

//        pref = Pref(requireContext())
//        val savedUri = pref.getImageUri()
//        if (!savedUri.isNullOrEmpty()) {
//            imageUri = Uri.parse(savedUri)
//          //  setProfileImage(Uri.parse(savedUri))
//        }
        Log.e("ololo","ol:${imageUri.toString()}")
    }

    private fun getData(child: String){
        db.child(mAuth.uid.toString()).child("Profile").child(child)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isAdded&&activity!=null){
                        if (snapshot.exists()){
                            if (child == "Image"){
                                imageUri = snapshot.value.toString().toUri()
                                Glide.with(binding.imgProfile).load(imageUri).into(binding.imgProfile)
                            }else if (child =="Name"){
                                name = snapshot.value.toString()
                                binding.tvName.text = name
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun removeFromProfile(){
        db.child(mAuth.uid.toString()).child("Profile").child("Image").removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    makeToast(requireContext(), "Successfuly deleted")
                } else {
                    makeToast(requireContext(), "Error of deleting")
                }
            }
    }

    private fun addToRealTimeData(data: String,child:String){
        with(binding){
            db.child(mAuth.uid.toString()).child("Profile").child(child).setValue(data)
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

    private fun initViews() {
        with(binding) {
            cardNotifications.textForCard.text =
                getString(R.string.notification)
            cardMyData.textForCard.text = getString(R.string.my_data)
            cardCards.textForCard.text = getString(R.string.cards)
            cardHelp.textForCard.text = getString(R.string.help)
            cardQuestion.textForCard.text =
                getString(R.string.questions_answers)
            cardNotifications.imgNext.visibility = View.GONE
            cardNotifications.cardContainer.isClickable = false
            cardNotifications.switchNotifications.visibility = View.VISIBLE
            Glide.with(cardMyOrders.imgForCard)
                .load(R.drawable.my_orders)
                .into(cardMyOrders.imgForCard)
            Glide.with(cardMyData.imgForCard)
                .load(R.drawable.pencil)
                .into(cardMyData.imgForCard)
            Glide.with(cardNotifications.imgForCard)
                .load(R.drawable.notification)
                .into(cardNotifications.imgForCard)
            Glide.with(cardCards.imgForCard)
                .load(R.drawable.cards).into(cardCards.imgForCard)
            Glide.with(cardHelp.imgForCard)
                .load(R.drawable.help).into(cardHelp.imgForCard)
            Glide.with(cardQuestion.imgForCard)
                .load(R.drawable.question)
                .into(cardQuestion.imgForCard)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    private fun initListeners() {
        with(binding) {
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            tvName.text = sharedPreferences.getString("name", getString(R.string.name))

            btnSetName.setOnClickListener {
                val dialogBinding = BottomSheetSetNameBinding.inflate(layoutInflater)
                val dialog = BottomSheetDialog(requireContext())
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                dialog.setContentView(dialogBinding.root)
                dialog.show()

                dialogBinding.editName.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        dialogBinding.editName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blue
                            )
                        )
                    } else {
                        dialogBinding.editName.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                    }
                }
                dialogBinding.editSurname.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        dialogBinding.editSurname.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blue
                            )
                        )
                    } else {
                        dialogBinding.editSurname.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                    }
                }
                dialogBinding.btnSave.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                dialogBinding.btnSave.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )

                val textWatcher = object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        val name =
                            "${dialogBinding.editName.text.toString()} ${dialogBinding.editSurname.text.toString()}"

                        if (name.isNotBlank()) {
                            dialogBinding.btnSave.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.blue
                                )
                            )
                            dialogBinding.btnSave.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                        } else {
                            dialogBinding.btnSave.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            dialogBinding.btnSave.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                }
                dialogBinding.editName.addTextChangedListener(textWatcher)
                val tvName = binding.tvName
                dialogBinding.btnSave.setOnClickListener {
                     name =
                        "${dialogBinding.editName.text.toString()} ${dialogBinding.editSurname.text.toString()}"
                    if (name.isNotBlank()) {
                        addToRealTimeData(name,"Name")
                        Log.e("ololo","afhah:  $name")
                        dialog.dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.field_name_surname),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showCustomToast() {
        val customToastBinding = CustomToastBinding.inflate(layoutInflater)
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = customToastBinding.root
        (toast.view as LinearLayout).setBackgroundResource(R.drawable.toast_bg)
        customToastBinding.customToastText.setTextColor(resources.getColor(R.color.black))
        customToastBinding.customToastText.text = getString(R.string.photo_is_delete)
        toast.show()
    }

    @SuppressLint("InflateParams")
    private fun initListener() {
        with(binding) {
            imgProfile.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val bottomSheetBinding = BottomSheetProfileBinding.inflate(layoutInflater)
                dialog.setContentView(bottomSheetBinding.root)
                dialog.show()
                bottomSheetBinding.btnSelectPhoto.setOnClickListener {
                    openSelectPhotoBottomSheet()
                    dialog.dismiss()
                }
                bottomSheetBinding.btnDeletePhoto.setOnClickListener {
                    dialog.dismiss()
                    val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog, null)
                    val dialog1 = Dialog(requireContext())
                    dialog1.setContentView(dialogBinding)
                    dialog1.setCancelable(true)
                    dialog1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog1.show()
                    val deleteDialog = dialog1.findViewById<TextView>(R.id.btnDelete)
                    val cancelDialog = dialog1.findViewById<TextView>(R.id.btnCancel)
                    deleteDialog.setOnClickListener {
                        showCustomToast()
                        binding.imgProfile.setImageDrawable(null)
                        removeFromProfile()
                        imageUri = null
                        dialog1.dismiss()
                    }
                    cancelDialog.setOnClickListener {
                        dialog1.dismiss()
                    }
                }
            }
        }
    }

    private fun openSelectPhotoBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetSelectPhotoBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
        bottomSheetBinding.btnGallery.setOnClickListener {
            openGallery()
            dialog.dismiss()
        }
        bottomSheetBinding.btnCamera.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }
    }

//    private fun setProfileImage(uri: Uri?) {
//        uri?.let {
//            Glide.with(this)
//                .load(uri)
//                .circleCrop()
//                .into(binding.imgProfile)
//
//            pref.saveImageUri(uri)
//            imageUri = uri
//            binding.imgProfile.invalidate()
//        } ?: run {
//            binding.imgProfile.setImageResource(R.drawable.profile_img)
//            pref.saveImageUri(null)
//            imageUri = null
//            binding.imgProfile.invalidate()
//        }
//    }
//
//    private fun setPhoto(bitmap: Bitmap) {
//        binding.imgProfile.setImageResource(R.drawable.profile_img)
//        binding.imgProfile.setImageBitmap(bitmap)
//    }
}