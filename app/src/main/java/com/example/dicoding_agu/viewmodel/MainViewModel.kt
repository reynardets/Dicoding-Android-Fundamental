package com.example.dicoding_agu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding_agu.model.Creator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<Creator>>()

    fun setUser(username: String) {
        val listItem = ArrayList<Creator>()
        val url = "https://api.github.com/search/users?q=$username"
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
                val respondList = respondObjects.getJSONArray("items")
                for (i in 0 until respondList.length()) {
                    val userTemp = respondList.getJSONObject(i)
                    val userItem = Creator()
                    userItem.username = userTemp.getString("login")
                    userItem.avatar = userTemp.getString("avatar_url")
                    listItem.add(userItem)
                }
                listUser.postValue(listItem)
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

    fun getUser(): LiveData<ArrayList<Creator>> {
        return listUser
    }
}


