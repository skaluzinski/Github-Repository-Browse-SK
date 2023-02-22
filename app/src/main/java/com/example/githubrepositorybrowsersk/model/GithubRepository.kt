package com.example.githubrepositorybrowsersk.model

import androidx.annotation.Keep
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubrepositorybrowsersk.model.utils.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGithubRepository {
    fun getRepositories(): Flow<PagingData<Repo>>
}

@Keep
class GithubRepository @Inject constructor(private val githubRemoteDataSource: GithubRemoteDataSource) :
    IGithubRepository {
    override fun getRepositories(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubRemoteDataSource = githubRemoteDataSource) }
        ).flow
    }
}

