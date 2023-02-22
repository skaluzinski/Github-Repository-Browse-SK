package com.example.githubrepositorybrowsersk.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubrepositorybrowsersk.model.utils.Repo
import com.example.githubrepositorybrowsersk.viewmodel.GithubViewModel

@Composable
fun RepositoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    setScaffoldTitle: (String) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.RepositoriesList.route
    ) {
        composable(
            route = "${NavRoutes.RepositoryDetail.route}?{idValue}?{repoName}?{desc}?{commitAmount}?{issuesAmount}",
            arguments = listOf(
                navArgument("idValue") {
                    type = NavType.StringType
                    defaultValue = "Default"
                },
                navArgument("repoName") {
                    type = NavType.StringType
                    defaultValue = "Default"
                },
                navArgument("desc") {
                    type = NavType.StringType
                    defaultValue = "Default"
                },
                navArgument("commitAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                }, navArgument("issuesAmount") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            RepositoryDetail(
                repository = Repo(
                    id = backStackEntry.arguments?.getString("idValue") ?: "",
                    name = backStackEntry.arguments?.getString("repoName") ?: "",
                    desc = backStackEntry.arguments?.getString("desc") ?: "",
                    commitsAmount = backStackEntry.arguments?.getInt("commitAmount") ?: 0,
                    issuesAmount = backStackEntry.arguments?.getInt("issuesAmount") ?: 0
                )
            )
        }

        composable(NavRoutes.RepositoriesList.route) {
            val viewModel = hiltViewModel<GithubViewModel>()
            RepositoriesList(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
                setScaffoldTitle = setScaffoldTitle
            )
        }
    }
}