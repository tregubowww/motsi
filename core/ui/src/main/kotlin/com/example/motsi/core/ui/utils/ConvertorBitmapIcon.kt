package com.example.motsi.core.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap

fun convertSportActivityIconToBitmap(context: Context, iconActivity: Int): Bitmap {
    val size = 80
    val bitmap = createBitmap(size, size).apply {
        val canvas = Canvas(this)
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.FILL }
        // Рисуем круг
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2, paint)

        // Иконка
        val drawable = ContextCompat.getDrawable(context, iconActivity)
        drawable?.let {
            val iconSize = (size * 0.8f).toInt()
            val left = (size - iconSize) / 2
            val top = (size - iconSize) / 2
            it.setBounds(left, top, left + iconSize, top + iconSize)
            it.draw(canvas)
        }

        // обводка
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
        paint.color = Color.BLACK
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 2, paint)
    }
    return bitmap
}

fun createUserLocationBitmap(): Bitmap {
    val size = 80
    val bitmap = createBitmap(size, size).apply {
        val canvas = Canvas(this)

        // Основной красный круг
        val circlePaint = Paint().apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.FILL
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 8, circlePaint)

        // Белая обводка
        val strokePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = Color.WHITE
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - 8, strokePaint)

        // Белая точка внутри
        val dotPaint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 8f, dotPaint)
    }
    return bitmap
}