package com.example.mycomposeapp.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.navigation.Comments
import com.example.mycomposeapp.ui.navigation.Feed
import com.example.mycomposeapp.ui.navigation.Likes
import com.example.mycomposeapp.ui.navigation.Notifications
import com.example.mycomposeapp.ui.theme.MyTheme

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val shape = RoundedCornerShape(
        topStart = 26.dp,
        topEnd = 26.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .clip(shape),
        color = MyTheme.colorScheme.secondary,
        shadowElevation = 10.dp,
        tonalElevation = 0.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = MyTheme.colorScheme.onSecondary,
            modifier = Modifier.height(100.dp)
        ) {
            BottomBarItem(
                selected = currentRoute == Likes::class.qualifiedName,
                painter = painterResource(R.drawable.ic_like),
                onClick = {
                    navController.navigate(Likes) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            BottomBarItem(
                selected = currentRoute == Feed::class.qualifiedName,
                painter = painterResource(R.drawable.ic_home),
                onClick = {
                    navController.navigate(Feed) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            BottomBarItem(
                selected = currentRoute == Comments::class.qualifiedName,
                painter = painterResource(R.drawable.ic_comment),
                onClick = {
                    navController.navigate(Comments) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            BottomBarItem(
                selected = currentRoute == Notifications::class.qualifiedName,
                painter = painterResource(R.drawable.ic_notifications),
                onClick = {
                    navController.navigate(Notifications) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    selected: Boolean,
    painter: Painter,
    onClick: () -> Unit
) {
    val iconSize = if (selected) 22.dp else 18.dp

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,

            selectedIconColor = MyTheme.colorScheme.primary,
            unselectedIconColor = MyTheme.colorScheme.outline,

            selectedTextColor = MyTheme.colorScheme.primary,
            unselectedTextColor = MyTheme.colorScheme.outline
        ),
        modifier = Modifier.padding(bottom = 20.dp)
    )
}