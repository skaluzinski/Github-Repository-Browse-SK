package com.example.githubrepositorybrowsersk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.example.githubrepositorybrowsersk.ui.theme.GithubRepositoryBrowserSKTheme
import com.example.githubrepositorybrowsersk.view.RepositoriesBrowserApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            GithubRepositoryBrowserSKTheme {
                Surface(color = MaterialTheme.colorScheme.primary) {
                    RepositoriesBrowserApp()
                }
            }
        }
    }
}






