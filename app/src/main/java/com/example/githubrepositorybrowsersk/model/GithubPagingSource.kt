package com.example.githubrepositorybrowsersk.model

import androidx.annotation.Keep
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepositorybrowsersk.model.utils.Repo
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@Keep
class GithubPagingSource @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource
) : PagingSource<String, Repo>() {

    override fun getRefreshKey(
        state: PagingState<String, Repo>,
    ): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(
                anchorPageIndex - 1
            )?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Repo> {
        return try {
            val cursor = params.key
            val response = githubRemoteDataSource.getRepos(cursor).firstOrNull()?.data?.viewer?.repositories
            val repos = response?.nodes?.map { node ->
                val commits = node!!.repositoryDetails.repositoryCommitAmount.`object`?.onCommit?.history?.totalCount
                Repo(
                    id  = node.repositoryDetails.id,
                    name = node!!.repositoryDetails.name,
                    desc = node.repositoryDetails.description ?: "",
                    issuesAmount = node.repositoryDetails.issues.totalCount,
                    commitsAmount = commits ?: 0
                )
            }
            val pageInfo = response!!.pageInfo
            LoadResult.Page(
                data = repos ?: listOf(),
                prevKey = cursor,
                nextKey = pageInfo.endCursor
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}