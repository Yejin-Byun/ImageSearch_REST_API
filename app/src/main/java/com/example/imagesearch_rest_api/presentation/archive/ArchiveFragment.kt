package com.example.imagesearch_rest_api.presentation.archive

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch_rest_api.data.SearchData
import com.example.imagesearch_rest_api.databinding.FragmentArchiveBinding
import com.example.imagesearch_rest_api.presentation.MainActivity

class ArchiveFragment : Fragment() {
    private val binding by lazy { FragmentArchiveBinding.inflate(layoutInflater) }
    private var savedList : MutableList<SearchData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedList = (activity as MainActivity).saveList

        binding.recyclerGridView.adapter = ArchiveAdapter(requireContext(), savedList).apply {
            data = savedList.toMutableList()
            Log.d("data", "$data")
            Log.d("save", "$savedList")
        }

        binding.recyclerGridView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
        return binding.root
    }

}