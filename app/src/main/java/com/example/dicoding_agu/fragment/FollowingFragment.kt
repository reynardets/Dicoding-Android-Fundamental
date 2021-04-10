package com.example.dicoding_agu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_agu.adapter.CreatorAdapter
import com.example.dicoding_agu.databinding.FragmentFollowingBinding
import com.example.dicoding_agu.viewmodel.FollowViewModel

class FollowingFragment : Fragment() {

    companion object {
        const val USERNAME = "username"
    }

    private lateinit var username: String
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followViewModel: FollowViewModel
    private lateinit var adapter: CreatorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CreatorAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.adapter = adapter

        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        followViewModel.setFollowingUser(username)
        followViewModel.getFollowingUser().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
            }
        })
    }


}