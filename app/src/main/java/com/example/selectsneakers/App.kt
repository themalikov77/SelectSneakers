package com.example.selectsneakers

import android.app.Application
import com.example.selectsneakers.repository.Repository

class App: Application() {

    val repository: Repository by lazy {
        Repository()
    }
}