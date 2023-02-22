package com.example.githubrepositorybrowsersk.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.githubrepositorybrowsersk.R
import com.example.githubrepositorybrowsersk.model.utils.Repo
import com.example.githubrepositorybrowsersk.viewmodel.GithubViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepositoriesList(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: GithubViewModel,
    setScaffoldTitle: (String) -> Unit
) {
    val userListItems: LazyPagingItems<Repo> = viewModel.repos.collectAsLazyPagingItems()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = modifier.padding(
                top = dimensionResource(id = R.dimen.medium_padding),
                start = dimensionResource(id = R.dimen.medium_padding),
                end = dimensionResource(id = R.dimen.medium_padding)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.repo_list_item_spacing))
        ) {
            items(items = userListItems,
                key = { repository ->
                    repository.id
                }
            ) { repository ->
                ShortRepoCard(
                    modifier = Modifier
                        .requiredHeight(dimensionResource(id = R.dimen.small_repo_card_size))
                        .animateItemPlacement(),
                    repo = repository!!,
                    onClick = { _ ->
                        setScaffoldTitle(repository.name)
                        navigateToRepoDetails(
                            navController = navController,
                            repository = repository
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ShortRepoCard(
    modifier: Modifier = Modifier,
    repo: Repo,
    onClick: (repo: Repo) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.small_repo_card_corner_clip)))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .clickable { onClick(repo) }
            .wrapContentHeight()
    ) {
        Text(
            maxLines = 1,
            text = repo.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

//Todo determine whether should I make another package navigation and put it with navhost
private fun navigateToRepoDetails(navController: NavHostController, repository: Repo) {
    navController.navigate(
        NavRoutes.RepositoryDetail.route +
                "?${repository.id}" +
                "?${repository.name}" +
                "?${repository.desc}" +
                "?${repository.commitsAmount}" +
                "?${repository.issuesAmount}"
    )
}