package com.example.githubrepositorybrowsersk.model.utils

import androidx.annotation.Keep


//It's impossible to name it as Repository, graphql has schemaType named Repository -> name shadowing
@Keep
data class Repo(
    val id: String,
    val name: String,
    val desc: String,
    val issuesAmount: Int?,
    val commitsAmount: Int?
)
