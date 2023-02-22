package com.example.githubrepositorybrowsersk.view

import android.annotation.SuppressLint
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubrepositorybrowsersk.model.utils.Repo
import com.example.githubrepositorybrowsersk.utils.ConnectionState
import com.example.githubrepositorybrowsersk.utils.connectivityState
import com.example.githubrepositorybrowsersk.viewmodel.GithubViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


//bottom bar is unused, returned padding will be 0.dp
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun RepositoriesBrowserApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)

    val showHomeButton =
        currentRoute.value?.destination?.route ==
                NavRoutes.RepositoriesList.route
    var scaffoldTitle by remember {
        mutableStateOf("Home")
    }

    if (showHomeButton) {
        scaffoldTitle = "Home"
    }
    val scaffoldState
            : ScaffoldState = rememberScaffoldState()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    if (!isConnected) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                "You don't have internet connection.",
                actionLabel = "Dismiss"
            )
        }
    }

    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(
                WindowInsets(
                    bottom = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateBottomPadding(),
                    left = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateLeftPadding(LayoutDirection.Ltr),
                    right = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateRightPadding(LayoutDirection.Ltr)

                )
            ),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = scaffoldTitle, color =  MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    NavigationIcon(
                        showHomeButton = showHomeButton,
                        onHomeClick = {},
                        onBackClick = { navController.navigateUp() }
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),

                )
        }
    ) {
        RepositoryNavHost(navController = navController,
            setScaffoldTitle = { scaffoldTitle = it })
    }
}

@Composable
fun NavigationIcon(showHomeButton: Boolean, onHomeClick: () -> Unit, onBackClick: () -> Unit) {
    if (showHomeButton) {
        IconButton(onClick = { onHomeClick() }) {
            Icon(
                imageVector = Icons.Filled.Home,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Main screen"
            )
        }
    } else {
        IconButton(
            onClick = {
                onBackClick()
            }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}



