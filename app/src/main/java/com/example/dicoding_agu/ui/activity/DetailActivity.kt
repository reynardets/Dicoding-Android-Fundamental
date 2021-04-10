package com.example.dicoding_agu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dicoding_agu.R
import com.example.dicoding_agu.databinding.ActivityDetailBinding
import com.example.dicoding_agu.model.Creator
import com.example.dicoding_agu.viewmodel.DetailViewModel
import com.example.dicoding_agu.viewmodel.SectionPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CREATOR = "extra_creator"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Creator>(EXTRA_CREATOR) as Creator
        val username = user.username

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        if (username != null) {
            detailViewModel.setUser(username)
            showLoading(true)
        }

        detailViewModel.getUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvCompany.text = it.company
                    tvUname.text = it.username
                    tvLocation.text = it.location
                    if (it.url == null) {
                        tvUrl.text = resources.getString(R.string.empty)
                    } else tvUrl.text = it.url
                    Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .apply(RequestOptions())
                        .into(tvPhoto)
                    showLoading(false)
                }
            }
        })

        val sectionPageAdapter = SectionPageAdapter(this, user.username)
        binding.apply {
            viewPager.adapter = sectionPageAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        supportActionBar?.elevation = 0f

        val imageView = findViewById<ImageView>(R.id.back)
        imageView.setOnClickListener {
            finish()
        }

        val language = findViewById<ImageView>(R.id.setting)
        language.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}