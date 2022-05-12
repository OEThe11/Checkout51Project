package com.blackspadetechnologies.checkout51project

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackspadetechnologies.checkout51project.info.Offer
import com.blackspadetechnologies.checkout51project.repository.C51Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class C51ViewModel : ViewModel() {
    private val repository = C51Repository()

    private val _c51StateData = MutableStateFlow<List<Offer>>(emptyList())
    val c51StateData = _c51StateData.asStateFlow()


    fun fetchCheckout(context: Context) = viewModelScope.launch {
        val checkout = repository.getAllOffers(context)
        _c51StateData.value = checkout
    }

}