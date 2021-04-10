package com.example.dicoding_agu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding_agu.model.DetailCreator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    val userDetail = MutableLiveData<DetailCreator>()

    fun setUser(username: String) {
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val respondObjects = JSONObject(result)
                val userItem = DetailCreator()
                userItem.username = respondObjects.getString("login")
                userItem.avatar = respondObjects.getString("avatar_url")
                userItem.name = respondObjects.getString("name")
                userItem.company = respondObjects.getString("company")
                userItem.location = respondObjects.getString("location")
                userItem.url = respondObjects.getString("blog")
                userDetail.postValue(userItem)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUser(): LiveData<DetailCreator> {
        return userDetail
    }
}