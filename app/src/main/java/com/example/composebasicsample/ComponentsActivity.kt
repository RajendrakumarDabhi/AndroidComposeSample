package com.example.composebasicsample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    var isListShow by remember { mutableStateOf(false) }
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        //region topbar
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
        //endregion

        //region Textview Sample
        SelectionContainer() {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(2.dp)
                )
                DisableSelection {
                    Text(
                        text = "Disabled Selection ",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
        //endregion

        Spacer(modifier = Modifier.height(2.dp))

        //region TextField Sample
        Box(modifier = Modifier.clickable {
            isListShow = true
        }) {
            OutlinedTextField( //TextField can also be used here for simple decoration
                value = textState.value,
                leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
                label = { Text("Enter Contact Person") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        Log.e("On Done", "On Done Clicked")
                    }
                ),
                onValueChange = { isListShow = true },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            )
        }
        //endregion

        //region column sample
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
        //endregion

        //region row sample
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
        //endregion
        Spacer(modifier = Modifier.height(1.dp))
        //region custom view sample
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            GoogleButton(
                text = "Sign Up with Google",
                loadingText = "Creating Account...",
                onClicked = {}
            )
        }
        //endregion

        Spacer(modifier = Modifier.height(1.dp))
        //region expedable cardview sample
        ExpendableCardView(
            title = "Click to expend",
            description = "Loreium iup locak test message  here taken by lokcan name here loc are joined"
        )
        //endregion
        //region imageview sample
        LiveImageView(
            height = 150.dp,
            width = 150.dp,
            imgUrl = "https://images.unsplash.com/photo-1600804340584-c7db2eacf0bf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cHVwcHl8ZW58MHx8MHx8&w=1000&q=80"
        )
        //endregion
        GradientButton(
            "Button", Color.White, Brush.horizontalGradient(
                colors = listOf(
                    Color.Blue, Color.Gray
                ),
            )
        ) {
            showToast(mContext, "Button Clicked")
        }
    }
    //region Listviewsample
    if (isListShow) {
        val listData = getAllData()
        LazyColumn(
            modifier = Modifier.padding(start = 24.dp, top = 104.dp, bottom = 54.dp, end = 24.dp)
        ) {
            items(listData.size) {
                UserItem(person = listData.get(it), MaterialTheme.typography) {
                    isListShow = false
                    textState.value = TextFieldValue(it)
                }
            }
        }
    }
    //endregion
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

