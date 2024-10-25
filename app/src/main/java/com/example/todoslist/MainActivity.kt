package com.example.todoslist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.todoslist.ui.theme.ToDosListTheme

class MainActivity : ComponentActivity() {

    private val database by lazy{
        Room.databaseBuilder(
            this,
            TaskDatabaseClass::class.java,
            "taskDB"
        )
            .build()
    }

    private val viewModel by lazy {
        TaskViewModel(database.taskDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDosListTheme {
                val tasks by viewModel.state.collectAsState()
                MyContent(tasks)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MyContent(tasks: List<Task> = emptyList()){

        var isToAddTask by remember {
            mutableStateOf(false)
        }
        var showAlert by remember{
            mutableStateOf(false)
        }
        var taskToDelete by remember{
            mutableStateOf<Task?>(null)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xC68A5FDA)),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Tasks",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FloatingActionButton(
                        onClick = {

                        },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(Icons.Default.Add, "add task")
                    }
                }
            }
        ){ innerPadding ->

            AnimatedVisibility(!isToAddTask) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(tasks) { task ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxSize()
                                .padding(5.dp),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text( "",
                                    modifier = Modifier.clickable {

                                    }
                                )
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(isToAddTask) {
                Column(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .width(width = 330.dp)
                        ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            var taskText by remember {
                                mutableStateOf("")
                            }

                            OutlinedTextField(
                                value = "",
                                onValueChange = {

                                },
                                placeholder = {

                                },
                                modifier = Modifier.padding(5.dp)
                            )

                            Button(
                                onClick = {
                                    if(taskText.isNotBlank()){

                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xF3A76DE8))
                            ){
                                Text("Add")
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(showAlert) {
                AlertDialog(
                    onDismissRequest = { /** TODO **/ },
                    confirmButton = {
                        Button(
                            onClick = {

                            }
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {

                            }
                        ) {
                            Text("No")
                        }
                    },
                    title = {
                        Text("Have you finished your task!")
                    }
                )
            }
        }
    }

}
