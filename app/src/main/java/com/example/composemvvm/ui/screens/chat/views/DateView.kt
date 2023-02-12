package com.example.composemvvm.ui.screens.chat.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composemvvm.models.Message
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

@Composable
fun DateView(message: Message, messages: List<Message>) {

    val index = messages.indexOf(message)
    if (index == messages.size - 1) {
        DrawDate(message.date)
    }

    if (index < messages.size - 1) {
        val previousDate = simpleDateFormat.format(messages[index + 1].date)
        val date = simpleDateFormat.format(message.date)
        if (date != previousDate) {
            DrawDate(message.date)
        }
    }
}

@Composable
private fun DrawDate(date: Date) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = Alignment.Center) {
        Text(text = isToday(date), color = Color.Gray)
    }
}

private fun isToday(date: Date): String {
    val format = simpleDateFormat.format(date)
    return if (format == simpleDateFormat.format(Date())) "Today" else format
}