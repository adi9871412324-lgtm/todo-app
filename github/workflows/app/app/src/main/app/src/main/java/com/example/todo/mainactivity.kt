package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Task(val text: String, done: Boolean = false) {
    var done by mutableStateOf(done)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { TodoScreen() } }
    }
}

@Composable
fun TodoScreen() {
    val tasks = remember { mutableStateListOf<Task>() }
    var input by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        Text("My To-Do List", style = MaterialTheme.typography.headlineMedium)
        Row(
            Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New task") },
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (input.isNotBlank()) {
                    tasks.add(Task(input.trim()))
                    input = ""
                }
            }) { Text("Add") }
        }
        LazyColumn {
            items(tasks) { task ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = task.done, onCheckedChange = { task.done = it })
                    Text(task.text, Modifier.weight(1f))
                    TextButton(onClick = { tasks.remove(task) }) { Text("Delete") }
                }
            }
        }
    }
}
