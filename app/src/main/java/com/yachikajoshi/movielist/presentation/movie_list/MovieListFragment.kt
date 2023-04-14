package com.yachikajoshi.movielist.presentation.movie_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

//        viewModel.getTop10MovieList()
//
//        lifecycle.coroutineScope.launchWhenCreated {
//            viewModel.top10MovieList.collect {
//                if (it.isLoading) {
//                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
//                }
//                if (it.error.isNotBlank()) {
//                    Toast.makeText(context, "data error", Toast.LENGTH_SHORT).show()
//                }
//                it.data?.let {
//                    Toast.makeText(context, "got data", Toast.LENGTH_SHORT).show()
//                }
//            }
            }
        }
    }
}