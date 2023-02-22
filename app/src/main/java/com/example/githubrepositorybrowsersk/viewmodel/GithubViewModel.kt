package com.example.githubrepositorybrowsersk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositorybrowsersk.model.GithubRepository
import com.example.githubrepositorybrowsersk.model.utils.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(private val githubRepository: GithubRepository) :
    ViewModel() {
    val repos: Flow<PagingData<Repo>> =
        githubRepository.getRepositories().cachedIn(viewModelScope)
}