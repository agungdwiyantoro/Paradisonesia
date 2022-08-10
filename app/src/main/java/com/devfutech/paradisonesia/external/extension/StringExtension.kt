package com.devfutech.paradisonesia.external.extension

import android.text.Editable
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.regex.Matcher
import java.util.regex.Pattern

val String.formattedDate: String
    get() = LocalDate.parse(
        this,
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    ).format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    )

val String.formattedDateToDay: String
    get() = LocalDate.parse(
        this,
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    ).format(
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    )

fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}