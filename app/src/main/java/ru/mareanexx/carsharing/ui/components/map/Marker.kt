package ru.mareanexx.carsharing.ui.components.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.mareanexx.carsharing.R

fun createBitmapFromView(context: Context, string: String): Bitmap {
    val container = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    val button = TextView(context).apply {
        text = string
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
        textSize = 15f
        isAllCaps = false
        setPadding(1, 8, 1, 8)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        gravity = Gravity.CENTER
    }

    val locationIcon = ImageView(context).apply {
        setImageResource(R.drawable.location)
        setColorFilter(ContextCompat.getColor(context, android.R.color.black))
        layoutParams = LinearLayout.LayoutParams(200, 200)
    }

    container.addView(button)
    container.addView(locationIcon)

    val width = 600
    val height = 400
    container.measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED)
    )
    container.layout(0, 0, width, height)
    val bitmap = Bitmap.createBitmap(width, container.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    container.draw(canvas)

    return bitmap
}