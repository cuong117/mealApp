package com.example.themeal.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.themeal.R
import com.example.themeal.base.BaseFragment
import com.example.themeal.data.model.RecentSearch
import com.example.themeal.databinding.FragmentSearchBinding
import com.example.themeal.ui.search.adapter.SearchAdapter
import com.example.themeal.util.Dialog
import com.example.themeal.util.OnClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    OnClickListener<RecentSearch> {

    private var changeQuery: ((String, Boolean) -> Unit)? = null

    private val searchViewModel by sharedViewModel<SearchViewModel>()
    private val adapter by lazy { SearchAdapter() }
    private val removeDialog by lazy {
        context?.let { Dialog(AlertDialog.Builder(it, R.style.AlertDialogTheme)) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerRecentSearch.adapter = adapter
        adapter.addClickListener(this)
        searchViewModel.currentList.observe(viewLifecycleOwner) {
            val list = mutableListOf<RecentSearch>()
            if (it.size <= ITEM_SHOW) {
                list.addAll(it)
            } else {
                list.addAll(it.slice(0 until ITEM_SHOW))
            }
            adapter.submitList(list)
        }
    }

    override fun onClick(data: RecentSearch) {
        changeQuery?.invoke(data.keyWord, true)
    }

    override fun onLongClick(data: RecentSearch) {
        removeDialog?.showRemoveDialog(
            getString(R.string.title_remove),
            getString(R.string.msg_remove, data.keyWord)
        ) { _, _ ->
            searchViewModel.deleteKeyWord(data)
        }
    }

    override fun onItemClick(data: RecentSearch) {
        changeQuery?.invoke(data.keyWord, false)
    }

    companion object {
        private const val ITEM_SHOW = 5

        fun newInstance(changeQuery: (String, Boolean) -> Unit) =
            SearchFragment().apply { this.changeQuery = changeQuery }
    }
}
