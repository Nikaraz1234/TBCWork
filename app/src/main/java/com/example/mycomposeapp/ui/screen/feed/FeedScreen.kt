package com.example.mycomposeapp.ui.screen.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.components.Loader
import com.example.mycomposeapp.ui.components.NetworkImage
import com.example.mycomposeapp.ui.screen.feed.model.PostModel
import com.example.mycomposeapp.ui.screen.feed.model.StoryModel
import com.example.mycomposeapp.ui.theme.DarkSurface
import com.example.mycomposeapp.ui.theme.DarkTextPrimary
import com.example.mycomposeapp.ui.theme.DarkTextSecondary
import com.example.mycomposeapp.ui.theme.LightSurface
import com.example.mycomposeapp.ui.theme.MyTheme
import com.example.mycomposeapp.ui.theme.Radius
import com.example.mycomposeapp.ui.theme.Spacer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FeedScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(FeedContract.Event.LoadStories)
        viewModel.onEvent(FeedContract.Event.LoadPosts)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is FeedContract.SideEffect.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = effect.message,
                    )
                }
            }
        }

    }

    FeedContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FeedContent(
    state: FeedContract.State,
    onEvent: (FeedContract.Event) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Spacer.spacer12),
        ) {

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Spacer.spacer16),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.stories,
                        key = { it.id }
                    ) { story ->
                        StoryItem(item = story)
                    }
                }
            }

            items(
                items = state.posts,
                key = { it.id }
            ) { post ->
                PostItem(
                    item = post,
                    modifier = Modifier.padding(horizontal = Spacer.spacer32)
                )
            }
        }

        if (state.isLoading) {
            Loader()
        }
    }


}


@Composable
private fun StoryItem(
    item: StoryModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(width = 120.dp, height = 180.dp),
        shape = Radius.radius16,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = item.cover,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = item.title,
                style = MyTheme.typography.titleLarge,
                color = LightSurface,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = Spacer.spacer20, bottom = Spacer.spacer20)
            )
        }
    }
}
@Composable
private fun PostItem(
    item: PostModel,
    modifier: Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Radius.radius16,
        colors = CardDefaults.cardColors(
            containerColor = MyTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(DarkSurface)
        ) {
            Row(modifier = Modifier
                .padding(Spacer.spacer16,Spacer.spacer16,Spacer.spacer16,Spacer.spacer8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NetworkImage(
                    imageUrl = item.avatar,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    placeholder = R.drawable.ic_avatar
                )

                Spacer(modifier = Modifier.width(Spacer.spacer16))

                Column {
                    Text(
                        text = item.name,
                        style = MyTheme.typography.bodyLarge,
                        color = DarkTextPrimary
                    )

                    Text(
                        text = item.postDate,
                        style = MyTheme.typography.bodySmall,
                        color = DarkTextSecondary
                    )

                }
            }

            PostImagesLayout(
                images = item.images,
                modifier = Modifier.padding(Spacer.spacer16, Spacer.spacer8)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacer.spacer16, vertical = Spacer.spacer5),
                thickness = 1.dp,
                color = MyTheme.colorScheme.outline
            )


            PostActions(
                commentsCount = item.commentsCount,
                likesCount = item.likesCount,
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MyTheme.colorScheme.outline
            )

            var commentText by remember { mutableStateOf("") }
            CommentInputRow(
                avatarUrl = item.avatar,
                commentText = commentText,
                onCommentChange = { commentText = it }
            )
        }
    }
}
@Composable
private fun CommentInputRow(
    avatarUrl: String?,
    onCommentChange: (String) -> Unit,
    commentText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacer.spacer16, vertical = Spacer.spacer12),
        verticalAlignment = Alignment.CenterVertically
    ) {

        NetworkImage(
            imageUrl = avatarUrl,
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            placeholder = R.drawable.ic_avatar
        )

        Spacer(modifier = Modifier.width(Spacer.spacer12))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .background(
                    color = MyTheme.colorScheme.surfaceVariant,
                    shape = Radius.radius16
                )
                .padding(horizontal = Spacer.spacer12),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = commentText,
                    onValueChange = onCommentChange,
                    modifier = Modifier.weight(1f),
                    textStyle = MyTheme.typography.bodyMedium.copy(
                        color = DarkTextPrimary
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (commentText.isEmpty()) {
                            Text(
                                text = "Write comment...",
                                style = MyTheme.typography.bodyMedium,
                                color = DarkTextSecondary
                            )
                        }
                        innerTextField()
                    }
                )

                Icon(
                    painter = painterResource(R.drawable.ic_share_comment),
                    contentDescription = null,
                    tint = MyTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }
}

@Composable
private fun PostActions(
    commentsCount: Int,
    likesCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacer.spacer16, vertical = Spacer.spacer12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        ActionItem(
            iconRes = R.drawable.ic_comment,
            text = "$commentsCount Comments"
        )

        ActionItem(
            iconRes = R.drawable.ic_like,
            text = "$likesCount Likes"
        )

        ActionItem(
            iconRes = R.drawable.ic_share,
            text = "Share"
        )
    }
}

@Composable
private fun ActionItem(
    iconRes: Int,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = DarkTextPrimary
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MyTheme.typography.bodySmall,
            color = DarkTextPrimary
        )

    }
}
@Composable
private fun PostImagesLayout(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) return


    when (images.size) {

        1 -> {
            NetworkImage(
                imageUrl = images[0],
                modifier = modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = Radius.radius16,
                placeholder = R.drawable.ic_avatar
            )
        }

        2 -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(220.dp),
                horizontalArrangement = Arrangement.spacedBy(Spacer.spacer8)
            ) {
                NetworkImage(
                    imageUrl = images[0],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = Radius.radius16,
                )

                NetworkImage(
                    imageUrl = images[1],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = Radius.radius16,
                )
            }
        }
        3 -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(220.dp),
                horizontalArrangement = Arrangement.spacedBy(Spacer.spacer8)
            ) {

                NetworkImage(
                    imageUrl = images[0],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = Radius.radius16,
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(Spacer.spacer8)
                ) {
                    NetworkImage(
                        imageUrl = images[1],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        shape = Radius.radius16,
                    )

                    NetworkImage(
                        imageUrl = images[2],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        shape = Radius.radius16,
                    )
                }
            }
        }
    }
}