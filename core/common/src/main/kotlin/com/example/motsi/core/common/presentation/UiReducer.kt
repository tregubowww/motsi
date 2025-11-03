package com.example.motsi.core.common.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * Редуктор состояния для управления UI состоянием в реактивном стиле.
 *
 * Обеспечивает иммутабельные обновления состояния через StateFlow.
 * Подходит для использования в ViewModel для управления состоянием экрана.
 *
 * @param T тип состояния (должен быть ненулевым)
 * @param initialValue начальное значение состояния
 */
class UiReducer<T : Any>(initialValue: T) {
    private val _stateFlow = MutableStateFlow(initialValue)
    val state: StateFlow<T> = _stateFlow.asStateFlow()

    /**
     * Обновляет состояние с помощью функции-преобразователя.
     * @param block функция, преобразующая текущее состояние в новое
     */
    fun update(block: T.() -> T) {
        _stateFlow.update { it.block() }
    }

    /**
     * Возвращает текущее состояние синхронно.
     */
    fun current(): T = _stateFlow.value
}