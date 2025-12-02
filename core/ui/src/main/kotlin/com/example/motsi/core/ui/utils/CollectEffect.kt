package com.example.motsi.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Удобный способ подписаться на Flow<Effect> с учётом жизненного цикла.
 * Работает только в рамках Compose-иерархии с LifecycleOwner (обычно внутри Activity/Screen).
 *
 * @param effectFlow — Flow с эффектами (обычно viewModel.effect)
 * @param onEffect — обработчик каждого эффекта
 */
@Composable
fun <T> CollectEffect(
    effectFlow: Flow<T>,
    onEffect: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effectFlow.collect { effect ->
                onEffect(effect)
            }
        }
    }
}