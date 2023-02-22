package com.example.githubrepositorybrowsersk.view

import androidx.annotation.Keep

@Keep
sealed class NavRoutes(val route: String) {
    object RepositoriesList : NavRoutes("list")
    object RepositoryDetail : NavRoutes("detail")
}