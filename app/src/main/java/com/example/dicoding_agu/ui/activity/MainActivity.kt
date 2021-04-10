package com.example.dicoding_agu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_agu.R
import com.example.dicoding_agu.adapter.CreatorAdapter
import com.example.dicoding_agu.databinding.ActivityMainBinding
import com.example.dicoding_agu.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CreatorAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CreatorAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.rvCreator.layoutManager = LinearLayoutManager(this)
        binding.rvCreator.adapter = adapter
        binding.searchBar.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.setUser(query)
                    binding.searchBar.clearFocus()
                    showLoading(true)
                    binding.emptySearch.root.visibility = View.GONE
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        mainViewModel.getUser().observe(this, {
            if (it != null) {
                adapter.setData(it)
                if (adapter.itemCount == 0) {
                    binding.emptySearch.root.visibility = View.VISIBLE
                } else binding.emptySearch.root.visibility = View.GONE
                showLoading(false)
            }
        })

        val language = findViewById<ImageView>(R.id.setting)
        language.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}