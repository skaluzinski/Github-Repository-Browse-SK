package com.example.githubrepositorybrowsersk.model

import androidx.annotation.Keep
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.executeCacheAndNetwork
import com.example.GetReposQuery
import com.example.SearchRepoDetailsQuery
import com.example.githubrepositorybrowsersk.R
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Keep
class GithubRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    fun getRepos(cursor: String?): Flow<ApolloResponse<GetReposQuery.Data>> {
        return apolloClient.query(
            GetReposQuery(cursor = Optional.presentIfNotNull(cursor))
        ).executeCacheAndNetwork()
    }

    fun searchRepo(
        name: String,
        owner: String = R.string.default_user.toString()
    ): Flow<ApolloResponse<SearchRepoDetailsQuery.Data>> {
        return apolloClient.query(
            SearchRepoDetailsQuery(
                name = name,
                owner = owner
            )
        ).executeCacheAndNetwork()
    }
}