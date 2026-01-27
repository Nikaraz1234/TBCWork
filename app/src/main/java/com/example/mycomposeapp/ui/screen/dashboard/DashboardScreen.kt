package com.example.mycomposeapp.ui.screen.dashboard

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.screen.dashboard.model.LocationModel
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import com.example.mycomposeapp.ui.theme.Spacer
import com.example.mycomposeapp.ui.theme.Typography
import com.example.mycomposeapp.ui.theme.White
import kotlinx.coroutines.flow.collectLatest


@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(DashboardContract.Event.LoadLocations)
        viewModel.onEvent(DashboardContract.Event.GetTheme)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is DashboardContract.SideEffect.ShowSnackBar ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    MyComposeAppTheme(darkTheme = state.isDarkTheme) {
        DashboardContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardContent(
    state: DashboardContract.State,
    onEvent: (DashboardContract.Event) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = Spacer.spacer18),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Statistics",
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(
                onClick = { onEvent(DashboardContract.Event.ToggleTheme)},
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_theme),
                    contentDescription = stringResource(R.string.theme),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }

        }

        Spacer(Modifier.height(Spacer.spacer16))

        StatisticsCarousel(locations = state.locations)

        Spacer(Modifier.height(Spacer.spacer18))


    }
}
@Composable
fun StatisticsCarousel(
    locations: List<LocationModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Spacer.spacer18),
        horizontalArrangement = Arrangement.spacedBy(Spacer.spacer16)
    ) {
        items(
            items = locations,
            key = { it.id }
        ) { item ->
            LocationCard(item)
        }
    }
}

@Composable
fun LocationCard(
    item: LocationModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        ) {

            AsyncImage(
                model = item.photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),)

            Box(
                modifier = Modifier
                    .matchParentSize()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = Spacer.spacer32, top = Spacer.spacer32),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = item.location,
                    style = Typography.titleLarge,
                    color = White
                )
            }


            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = Spacer.spacer32, top = Spacer.spacer32),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fire),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(18.dp),
                )

                Text(
                    text = item.number,
                    style = Typography.bodyLarge,
                    color = White
                )
            }


            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = Spacer.spacer16,
                        end = Spacer.spacer16,
                        bottom = Spacer.spacer32
                    )
            ) {
                Text(
                    text = item.title,
                    style = Typography.titleLarge,
                    color = White
                )

                Spacer(Modifier.height(Spacer.spacer8))

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = "$${item.price}",
                        style = Typography.bodyLarge,
                        color = White,
                        modifier = Modifier.align(Alignment.TopStart)
                    )

                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        val maxStars = 5
                        val rating = item.stars.coerceIn(0, maxStars)

                        repeat(maxStars) { index ->
                            val filled = index < rating
                            Icon(
                                painter = if (filled) painterResource(R.drawable.ic_star_filled) else painterResource(R.drawable.ic_star_outline),
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardContentPreview() {
    DashboardContent(
        state = DashboardContract.State(
            isLoading = false,
            locations = listOf(
                LocationModel(
                    id = 1,
                    title = "Natural walk to the top",
                    location = "Barcelona",
                    number = "2500",
                    photo = "https://picsum.photos/seed/tour1/800/1200",
                    price = 120,
                    stars = 5
                )
            ),
            errorMessage = null
        ),
        onEvent = {}
    )
}