package com.example.motsi.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.motsi.core.ui.theming.Title1Primary
import com.example.motsi.impl.presentation.ActivityDetailsViewModel

@Composable
internal fun ActivityDetailsScreen(viewModel: ActivityDetailsViewModel) {
    val id by viewModel.loadingScreenState.collectAsState()
    val counter by viewModel.counter.collectAsState()
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue)){
        Title1Primary(text  = id, Modifier.padding(100.dp))
        Title1Primary(text  = counter.toString(), Modifier.padding(100.dp))
    }

    Button(onClick = { viewModel.onClick() }) { }
}