package com.example.sem_7_ca1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_7_ca1.ui.theme.Sem7Ca1Theme

data class Doctors(
    val name: String,
    val id: Int,
    val Dept: String,
    val Availability: Boolean
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Sem7Ca1Theme {
                DashBoard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Doctors list",
                        fontSize = 24.sp
                    )
                }
            )
        }
    ) { paddingValues ->

        val Doctors = remember {
            listOf(
                Doctors("raj", 12, "Department1", true),
                Doctors("ram", 13, "Department2", true),
                Doctors("raj", 14, "Department3", true),
                Doctors("Arun", 15, "Department4", false),
                Doctors("Kiran", 16, "Department5", true),
                Doctors("Vijay", 17, "Department6", false)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            items(
                items = Doctors,
                key = { info -> info.id }
            ) { info ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 6.dp
                        ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    home(info)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home(doc: Doctors) {

    var choice by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(false) }
    var sel by remember { mutableStateOf(false) }
    var disp by remember { mutableStateOf(false) }

    val state = rememberModalBottomSheetState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {

        Text(
            text = doc.name,
            fontSize = 22.sp
        )

        Text(
            text = "ID: ${doc.id}",
            fontSize = 17.sp
        )

        Text(
            text = doc.Dept,
            fontSize = 17.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                disp = !disp
            }
        ) {

            Text(
                text = "Click to view more info",
                fontSize = 15.sp
            )
        }
    }

    if (disp) {

        ModalBottomSheet(
            onDismissRequest = {
                disp = false
            },
            sheetState = state
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    choice = !choice
                }
            ) {

                Text(
                    text = "Click to see availability",
                    fontSize = 15.sp
                )
            }

            if (choice) {

                AnimatedVisibility(
                    visible = choice,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {

                    Text(
                        text = doc.Availability.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Text(
                text = "Click to book",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    selected = !selected
                }
            ) {

                Text(
                    text = "Click to confirm booking",
                    fontSize = 15.sp
                )
            }

            if (selected) {

                AlertDialog(
                    title = {
                        Text(
                            "Click to confirm",
                            fontSize = 20.sp
                        )
                    },

                    text = {
                        Text(
                            "Click to confirm",
                            fontSize = 16.sp
                        )
                    },

                    onDismissRequest = {
                        selected = false
                    },

                    confirmButton = {

                        Button(
                            onClick = {
                                sel = true
                                selected = false

                                Toast.makeText(
                                    context,
                                    "Confirmed booking",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        ) {

                            Text("Confirm")
                        }
                    },

                    dismissButton = {

                        Button(
                            onClick = {
                                selected = false
                            }
                        ) {

                            Text("Dismiss")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    Sem7Ca1Theme {
        DashBoard()
    }
}