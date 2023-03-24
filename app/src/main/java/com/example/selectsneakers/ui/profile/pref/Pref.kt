package com.example.selectsneakers.ui.profile.pref

import android.content.Context
import android.net.Uri

class Pref(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveImageUri(uri: Uri?) {
        sharedPreferences.edit().putString(KEY_PROFILE_IMAGE_URI, uri.toString()).apply()
    }

    fun getImageUri(): String? {
        return sharedPreferences.getString(KEY_PROFILE_IMAGE_URI, null)
    }

    companion object {
        private const val PREF_NAME = "shoesstore_pref"
        private const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"
    }
}