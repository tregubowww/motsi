package com.example.motsi.core.ui.designsystem.mapwidget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable

class MapIconFactory(val context: Context) {

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
        color = 0xFFFFFFFF.toInt()
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF2196F3.toInt()
        style = Paint.Style.FILL
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFFFFFF.toInt()
        style = Paint.Style.FILL
    }
    fun createMarkerWithText(text: String): Drawable {
        val paddingH = dp(8)
        val bodyHeight = dp(32)
        val pointerHeight = dp(8)
        val stroke = dp(2)
        val textSize = sp(14)

        textPaint.textSize = textSize
        val textWidth = textPaint.measureText(text).toInt()

        val width = maxOf(bodyHeight, textWidth + paddingH * 2)
        val height = bodyHeight + pointerHeight

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)

        val radiusOuter = bodyHeight / 2f
        val radiusInner = radiusOuter - stroke
        val cx = width / 2f

        val shape = shapeCache.getOrPut(width) {
            val outerRect = RectF(0f, 0f, width.toFloat(), bodyHeight.toFloat())
            val innerRect = RectF(
                stroke.toFloat(),
                stroke.toFloat(),
                width - stroke.toFloat(),
                bodyHeight - stroke.toFloat()
            )

            val outerPath = Path().apply {
                addRoundRect(outerRect, radiusOuter, radiusOuter, Path.Direction.CW)
                moveTo(cx - dp(6), bodyHeight.toFloat())
                lineTo(cx, height.toFloat())
                lineTo(cx + dp(6), bodyHeight.toFloat())
                close()
            }

            val innerPath = Path().apply {
                addRoundRect(innerRect, radiusInner, radiusInner, Path.Direction.CW)
                moveTo(cx - dp(6) + stroke, bodyHeight - stroke.toFloat())
                lineTo(cx, height - stroke.toFloat())
                lineTo(cx + dp(6) - stroke, bodyHeight - stroke.toFloat())
                close()
            }

            MarkerShape(outerPath, innerPath)
        }

        canvas.drawPath(shape.outer, strokePaint)
        canvas.drawPath(shape.inner, fillPaint)

        val cy = bodyHeight / 2f
        val y = cy - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(text, cx, y, textPaint)

        return bitmap.toDrawable(context.resources)
    }

    private data class MarkerShape(
        val outer: Path,
        val inner: Path
    )

    private val shapeCache = HashMap<Int, MarkerShape>()

    private fun dp(value: Int): Int =
        (value * context.resources.displayMetrics.density).toInt()

    private fun sp(value: Int): Float =
        value * context.resources.displayMetrics.scaledDensity

}