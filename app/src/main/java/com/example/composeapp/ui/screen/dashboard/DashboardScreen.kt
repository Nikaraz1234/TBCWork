package com.example.composeapp.ui.screen.dashboard

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composeapp.R
import com.example.composeapp.ui.screen.dashboard.model.ChatModel
import com.example.composeapp.ui.theme.Radius
import com.example.composeapp.ui.theme.Spacer
import com.example.composeapp.ui.theme.White
import com.example.composeapp.ui.theme.appBg
import com.example.composeapp.ui.theme.etBg
import com.example.composeapp.ui.theme.gray
import com.example.composeapp.ui.theme.lightGray
import com.example.composeapp.ui.theme.searchBtn
import com.example.composeapp.ui.theme.yellow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val sideEffect = viewModel.sideEffect

    LaunchedEffect(Unit) {
        viewModel.onEvent(DashboardContract.Event.LoadChats)
    }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when (effect) {
                is DashboardContract.SideEffect.ShowError -> {
                }
            }
        }
    }
    DashboardContent(
        state = state,
        onEvent = viewModel::onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardContract.State,
    onEvent: (DashboardContract.Event) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(appBg)) {
        Spacer(modifier = Modifier.height(Spacer.spacer16))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = appBg,
                    shape = Radius.radius16
                )
                .padding(horizontal = Spacer.spacer8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { query -> onEvent(DashboardContract.Event.OnSearchQueryChanged(query)) },
                placeholder = { Text("Search")  },
                modifier = Modifier.fillMaxHeight(),
                shape = Radius.radius12,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = etBg,
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                },
            )


            Spacer(modifier = Modifier.width(Spacer.spacer8))

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = searchBtn,
                        shape = Radius.radius12
                    )
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(vertical = Spacer.spacer18, horizontal = Spacer.spacer16),
            verticalArrangement = Arrangement.spacedBy(Spacer.spacer16)
        ) {
            itemsIndexed(state.filteredChats, key = { _, chat -> chat.id }) { index, chat ->
                ChatItem(chat = chat)

                if (index < state.filteredChats.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = Spacer.spacer8),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }

        }

    }

}
@Composable
fun ChatItem(
    chat: ChatModel,
    modifier: Modifier = Modifier,
    avatarColor: Color = yellow
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.owner.take(1),
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(Spacer.spacer16))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.owner,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = White
            )
            Spacer(modifier = Modifier.height(Spacer.spacer5))

            Row(verticalAlignment = Alignment.CenterVertically) {
                val iconRes = when (chat.lastMessageType) {
                    ChatModel.ChatType.FILE -> R.drawable.ic_file
                    ChatModel.ChatType.VOICE -> R.drawable.ic_voice
                    else -> null
                }

                val messageText = when (chat.lastMessageType) {
                    ChatModel.ChatType.FILE -> stringResource(R.string.file_message)
                    ChatModel.ChatType.VOICE -> stringResource(R.string.voice_message)
                    else -> chat.lastMessage
                }

                if (iconRes != null) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = chat.lastMessageType.name,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(Spacer.spacer5))
                }

                Text(
                    text = messageText,
                    fontSize = 14.sp,
                    color = gray,
                    maxLines = 1
                )


            }
            Spacer(modifier = Modifier.height(Spacer.spacer5))


        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = chat.lastActive,
                fontSize = 12.sp,
                color = lightGray
            )

            Spacer(modifier = Modifier.height(Spacer.spacer8))

            if (chat.unreadMessages > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(searchBtn),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.unreadMessages.toString(),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }else if (chat.isTyping) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = "Typing",
                    modifier = Modifier
                        .size(16.dp),
                    tint = searchBtn
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardContent(
        state = DashboardContract.State(),
        onEvent = {}
    )
}