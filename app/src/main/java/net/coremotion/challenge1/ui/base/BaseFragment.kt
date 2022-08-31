package net.coremotion.challenge1.ui.base

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (inflater: LayoutInflater,container: ViewGroup?,attach: Boolean) -> T

abstract class BaseFragment<VD : ViewBinding>(private val inflate: Inflate<VD>) : Fragment() {

    private var _binding: VD? = null
    protected val binding: VD get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun start(
    )
}