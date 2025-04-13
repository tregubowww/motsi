package com.example.motsi.feature.search.impl.presentation.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.designsystem.appbar.SearchField
import com.example.motsi.feature.search.impl.presentation.SearchClickHandler
import com.example.motsi.feature.search.impl.presentation.SearchViewModel

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    clickHandler: SearchClickHandler,
    bottomNavBar: @Composable () -> Unit
) {
    SearchScreenSuccess(bottomNavBar, clickHandler)
}


@Composable
private fun SearchScreenSuccess(
    bottomNavBar: @Composable () -> Unit,
    clickHandler: SearchClickHandler
) {

    Scaffold(
        modifier = Modifier,
        topBar = { SearchAppBar() },
        bottomBar = bottomNavBar
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp)
                    .clickable(onClick = { clickHandler.onActivityClick() }),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(modifier = Modifier
                    .fillMaxSize(), text = " детальная инфа по активности ")
            }
        }
    }
}

@Composable
private fun SearchAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        var query: String by rememberSaveable { mutableStateOf("") }

        SearchField(
            query = query,
            onTextChange1 = { query = it },
            modifier = Modifier.padding(horizontal = 16.dp),
            hint = "Поиск в Геленджике",
            isNeedToFocused = false,
            isEnabled = true,
            onSearchClicked = { Log.e("sadasd", "asdsad") }
        )
    }
}



