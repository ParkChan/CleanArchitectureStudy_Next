package com.chan.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding>(
    private val inflater: (LayoutInflater) -> B
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    protected open fun bindViewModel() = Unit
    protected open fun setupObserve() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)
        bindViewModel()
        setupObserve()
    }

}
