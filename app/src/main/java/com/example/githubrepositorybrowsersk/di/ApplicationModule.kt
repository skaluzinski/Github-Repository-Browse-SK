package com.example.githubrepositorybrowsersk.di

import androidx.annotation.Keep
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.githubrepositorybrowsersk.model.GithubRemoteDataSource
import com.example.githubrepositorybrowsersk.model.GithubRepository
import com.example.githubrepositorybrowsersk.model.IGithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Qualifier
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @ApiKeyInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun provideApolloClient(@ApiKeyInterceptorOkHttpClient authorizationInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloService(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api.github.com/graphql")
            .addHttpHeader("Authorization", API_KEY)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubRepo(githubRemoteDataSource: GithubRemoteDataSource): IGithubRepository {
        return GithubRepository(githubRemoteDataSource)
    }
}

private const val API_KEY = "bearer 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"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptorOkHttpClient

@Keep
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val httpUrl: HttpUrl = original.url
        val url: HttpUrl = httpUrl.newBuilder()
            .addQueryParameter("Authorization", API_KEY)
            .build()
        val request: Request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
