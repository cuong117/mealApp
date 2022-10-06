package com.example.themeal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.themeal.util.Dialog

typealias Inflate<T> = (LayoutInflater) -> T

abstract class BaseFragment<T : ViewBinding>(private val inflate: Inflate<T>) : Fragment() {

    protected val loadingDialog by lazy {
        context?.let {
            Dialog(AlertDialog.Builder(it))
        }
    }
    private var _binding: T? = null
    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
