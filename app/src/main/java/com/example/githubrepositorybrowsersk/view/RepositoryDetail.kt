package com.example.githubrepositorybrowsersk.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.githubrepositorybrowsersk.R
import com.example.githubrepositorybrowsersk.model.utils.Repo

@Composable
fun RepositoryDetail(repository: Repo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimensionResource(id = R.dimen.medium_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        RepositoryEntry(title = "Repository name", value = repository.name)
        RepositoryEntry(
            title = "Repository descprition",
            value = repository.desc.ifEmpty { "This repository has no description." })
        RepositoryEntry(title = "Amount of issues", value = repository.issuesAmount.toString())
        RepositoryEntry(title = "Amount of commits", value = repository.commitsAmount.toString())
    }
}

@Composable
fun RepositoryEntry(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(dimensionResource(id = R.dimen.repo_detail_card_size))
            .clip(CutCornerShape(dimensionResource(id = R.dimen.repo_detail_corner_clip)))
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title, color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Justify,
            softWrap = true,
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .padding(top = dimensionResource(id = R.dimen.small_padding))
        )
        Text(
            text = value, color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            softWrap = true,
            modifier = Modifier.fillMaxHeight(1f)
        )
    }
}