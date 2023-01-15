package com.example.composebasicsample

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasicsample.ui.theme.ComposeBasicSampleTheme

class ComponentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Body()
                }
            }
        }
    }
}

@Composable
fun Body() {
    val mContext = LocalContext.current
    Column() {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text("Toolbar", style = MaterialTheme.typography.h5)
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            navigationIcon = {
                IconButton(onClick = {
                    showToast(mContext, "Back Clicked")
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }, actions = {
                IconButton(onClick = {
                    showToast(mContext, "Settings Clicked")
                }) {
                    Icon(Icons.Filled.Settings, null)
                }
            })
        SelectionContainer() {
            Row() {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(2.dp)
                )
                DisableSelection {
                    Text(
                        text = "Disabled Selection",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        modifier = Modifier.padding(2.dp)
                    )
                }
                Text(
                    text = "Selectable ",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = textState.value,
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
            label = { Text("Enter email here") },
            onValueChange = { textState.value = it },
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .height(160.dp)
                .padding(2.dp)
                .background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            CustomSurface(3f, color = Color.Green)
            CustomSurface(2f, color = Color.Blue)
            CustomSurface(1f, color = Color.Red)
        }
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(2.dp)
                .background(Color.Gray),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomSurface(1f, color = Color.Green)
            CustomSurface(2f, color = Color.Blue)
            CustomSurface(3f, color = Color.Red)
        }
        ExpendableCardView(
            title = "Click to expend",
            description = "Loreium iup locak test message  here taken by lokcan name here loc are joined"
        )
    }
}

@Composable
fun ColumnScope.CustomSurface(weight: Float, color: Color) {
    Surface(
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth(), color = color
    ) {}
}

@Composable
fun RowScope.CustomSurface(weight: Float, color: Color) {
    Surface(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight(), color = color
    ) {}
}

fun showToast(context: Context, s: String) {
    Toast.makeText(context, s, Toast.LENGTH_LONG).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ComposeBasicSampleTheme {
        Body()
    }
}

