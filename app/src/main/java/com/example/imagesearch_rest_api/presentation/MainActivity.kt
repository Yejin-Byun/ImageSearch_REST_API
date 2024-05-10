package com.example.imagesearch_rest_api.presentation

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imagesearch_rest_api.R
import com.example.imagesearch_rest_api.data.SearchData
import com.example.imagesearch_rest_api.databinding.ActivityMainBinding
import com.example.imagesearch_rest_api.presentation.archive.ArchiveFragment
import com.example.imagesearch_rest_api.presentation.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var saveList = mutableListOf<SearchData>()
    private val searchFragment = SearchFragment()
    private val archiveFragment = ArchiveFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        transFragment(SearchFragment())

        binding.btnImgsearch.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, searchFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnMyarchive.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, archiveFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun transFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    fun saveData(input: String) {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("keyword", input)
        edit.apply()
    }

    fun loadData(text: EditText) {
        val pref = getSharedPreferences("pref",0)
        text.setText(pref.getString("keyword", ""))
    }

    fun addItemList(item : SearchData) {
        val containList = saveList.any { it.url == item.url }
        if(!containList)
            saveList.add(item)
    }

    fun removeItemList(item : SearchData) {
        saveList.remove(item)
    }
}