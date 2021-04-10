package com.example.dicoding_agu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding_agu.model.Creator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<Creator>>()
    private val listFollowers = MutableLiveData<ArrayList<Creator>>()

    fun setFollowingUser(username: String) {
        val listItem = ArrayList<Creator>()
        val url = "https://api.github.com/users/$username/following"
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
                val respondObjects = JSONArray(result)
                for (i in 0 until respondObjects.length()) {
                    val userTemp = respondObjects.getJSONObject(i)
                    val userItem = Creator()
                    userItem.username = userTemp.getString("login")
                    userItem.avatar = userTemp.getString("avatar_url")
                    listItem.add(userItem)
                }
                listFollowing.postValue(listItem)
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

    fun setFollowersUser(username: String) {
        val listItem = ArrayList<Creator>()
        val url = "https://api.github.com/users/$username/followers"
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
                val respondObjects = JSONArray(result)
                for (i in 0 until respondObjects.length()) {
                    val userTemp = respondObjects.getJSONObject(i)
                    val userItem = Creator()
                    userItem.username = userTemp.getString("login")
                    userItem.avatar = userTemp.getString("avatar_url")
                    listItem.add(userItem)
                }
                listFollowers.postValue(listItem)
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

    fun getFollowingUser(): LiveData<ArrayList<Creator>> {
        return listFollowing
    }

    fun getFollowersUser(): LiveData<ArrayList<Creator>> {
        return listFollowers
    }

}