package com.example.quizzer.utils

import android.text.Spanned
import androidx.core.text.HtmlCompat

//formats html-strings
fun stringFormatter(string: String): Spanned {
    return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
}
