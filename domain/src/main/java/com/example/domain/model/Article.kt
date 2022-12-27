package com.example.domain.model

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import com.example.core.R as coreR

data class Article(
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val urlToImage: String = "",
    val publishedAt: PublishedAt = PublishedAt(null),
)

data class PublishedAt(
    private val publishedAt: String?,
) {
    fun getFormattedTime(context: Context): String {
        return try {
            val date = getDate()
            String.format(
                context.getString(coreR.string.published_at_format),
                SimpleDateFormat(DAY_FORMAT, Locale.US).format(date),
                SimpleDateFormat(TIME_FORMAT, Locale.US).format(date),
            )
        } catch (t: Throwable) {
            ""
        }
    }

    private fun getDate(): Date {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return dateFormat.parse(publishedAt)
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        const val DAY_FORMAT = "MMMM d"
        const val TIME_FORMAT = "H:mm"
    }
}