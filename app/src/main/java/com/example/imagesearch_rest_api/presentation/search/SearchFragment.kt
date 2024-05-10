package com.example.imagesearch_rest_api.presentation.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch_rest_api.data.ApiUrl.Companion.API_KEY
import com.example.imagesearch_rest_api.data.SearchData
import com.example.imagesearch_rest_api.retrofit.NetworkClient
import com.example.imagesearch_rest_api.databinding.FragmentSearchBinding
import com.example.imagesearch_rest_api.presentation.MainActivity
import com.example.imagesearch_rest_api.retrofit.SearchDocument
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val main by lazy { activity as MainActivity }
    private val handler = Handler(Looper.getMainLooper())
    private var items = mutableListOf<SearchDocument>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        main.loadData(binding.editSearchText)

        binding.editSearchText.setOnClickListener {
            binding.editSearchText.text.clear()
        }

        binding.btnSearch.setOnClickListener {
            val input = binding.editSearchText.text.toString()
            if (!input.isNullOrEmpty()) {
                val trimInput = input.trim()
                main.saveData(trimInput)
                communicateNetwork(queryParameter(trimInput))
                hideKeyboard()
            } else {
                Toast.makeText(context, "검색할 내용을 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun communicateNetwork(param: HashMap<String, String>) = lifecycleScope.launch {
        val authorization = "$API_KEY"
        val response = NetworkClient.service.searchImages(authorization, param)
        items = response.documents

        val searchDataList = items.map {
            SearchData(it.thumbnailUrl, it.siteName, it.dateTime)
        }

        handler.post {
            binding.recyclerGridView.apply {
                adapter = SearchAdapter(items, context, searchDataList.toMutableList())
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }
        }
    }

    private fun hideKeyboard() {
        val inputMutableList = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMutableList.hideSoftInputFromWindow(binding.editSearchText.windowToken, 0)
    }

    private fun queryParameter(input: String): HashMap<String, String> {
        return hashMapOf(
            "query" to input,
            "sort" to "recency",
            "page" to "1",
            "size" to "80"
        )
    }
}