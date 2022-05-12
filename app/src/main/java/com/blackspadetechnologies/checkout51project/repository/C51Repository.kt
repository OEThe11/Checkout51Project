package com.blackspadetechnologies.checkout51project.repository

import android.content.Context
import com.blackspadetechnologies.checkout51project.R
import com.blackspadetechnologies.checkout51project.info.C51
import com.blackspadetechnologies.checkout51project.info.Offer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay

class C51Repository {


    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()


    suspend fun getAllOffers(context: Context): List<Offer> {
        delay(2_000)

        val json = context.resources.openRawResource(R.raw.offers).bufferedReader().use {
            it.readText()
        }

        val offerList = moshi.adapter(C51::class.java).fromJson(json)
        return offerList!!.offers.map {
            Offer(
                cash_back = it.cash_back,
                image_url = it.image_url,
                name = it.name,
                offer_id = it.offer_id
            )
        }
    }


}