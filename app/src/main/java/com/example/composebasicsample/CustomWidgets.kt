package com.example.composebasicsample

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpendableCardView(
    title: String,
    titleFontSize: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.h6,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shapes.medium,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = titleFontSize,
                    fontWeight = titleFontWeight, modifier = Modifier
                        .weight(6f)
                        .padding(padding)
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = "Sign Up with Google",
    loadingText: String = "Creating Account...",
    icon: Int = R.drawable.ic_google,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.clickable { clicked = !clicked },
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else text)
            if (clicked) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
                onClicked()
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LiveImageView(height: Dp, width: Dp, imgUrl: String, desc: String = "") {
    Box(
        modifier = Modifier
            .height(height = height)
            .width(width = width),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberImagePainter(data = imgUrl, builder = {
            placeholder(R.drawable.img_placeholder)
            error(R.drawable.img_data_error)
            //setHeader() to be used to set header
        })
        /* //Can be useful to track state and react based on that
         val painterState = painter.state
         if (painterState is ImagePainter.State.Loading) {
             CircularProgressIndicator()
         }*/
        Image(painter = painter, contentDescription = desc)
    }
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() })
    {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Composable
fun UserItem(person: Person, typography: Typography, onClick: (name:String) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        //Design your ui here and assign its value from passed object
        Text(
            text = "${person.firstName}, ${person.lastName}",
            color = Color.Black,
            fontSize = typography.h5.fontSize,
            fontWeight = FontWeight.Normal
            ,
            modifier = Modifier.clickable {
                onClick("${person.firstName}, ${person.lastName}" )
            }
        )
    }
}


@Composable
@Preview
private fun GoogleButtonPreview() {
    GoogleButton(
        text = "Sign Up with Google",
        loadingText = "Creating Account...",
        onClicked = {}
    )
}

@Composable
@Preview
private fun GradiantButtonPreview() {
    GradientButton(
        "Button", Color.White, Brush.horizontalGradient(
            colors = listOf(
                Color.Blue, Color.Gray
            ),
        )
    ) {

    }
}

@Composable
@Preview
fun Preview() {
    ExpendableCardView(title = "Hello Test", description = "Desc test dec")
}

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)

fun getAllData(): List<Person> {
    return listOf(
        Person(
            id = 0,
            firstName = "John",
            lastName = "Doe",
            age = 20
        ),
        Person(
            id = 1,
            firstName = "Maria",
            lastName = "Garcia",
            age = 21
        ),
        Person(
            id = 2,
            firstName = "James",
            lastName = "Johnson",
            age = 22
        ),
        Person(
            id = 3,
            firstName = "Michael",
            lastName = "Brown",
            age = 23
        ),
        Person(
            id = 4,
            firstName = "Robert",
            lastName = "Davis",
            age = 24
        ),
        Person(
            id = 5,
            firstName = "Jenifer",
            lastName = "Miller",
            age = 25
        ),
        Person(
            id = 6,
            firstName = "Sarah",
            lastName = "Lopez",
            age = 26
        ),
        Person(
            id = 7,
            firstName = "Charles",
            lastName = "Wilson",
            age = 27
        ),
        Person(
            id = 8,
            firstName = "Daniel",
            lastName = "Taylor",
            age = 28
        ),
        Person(
            id = 9,
            firstName = "Mark",
            lastName = "Lee",
            age = 29
        ),
    )
}