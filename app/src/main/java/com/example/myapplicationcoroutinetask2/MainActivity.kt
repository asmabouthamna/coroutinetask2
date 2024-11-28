package com.example.myapplicationcoroutinetask2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObjectMovementScreen()
        }
    }
}

@Composable
fun ObjectMovementScreen() {
    // Create an Animatable for the Y position of the object
    val animatableY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // State to control if the animation should reset
    var isAnimating by remember { mutableStateOf(false) }

    // Display the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Moving object (red circle)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp) // Limit the animation space
        ) {
            Box(
                modifier = Modifier
                    .offset(y = animatableY.value.dp) // Y position animated
                    .size(50.dp) // Size of the circle
                    .background(Color.Red) // Circle color
            )
        }

        // Buttons to control the animation
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    isAnimating = true
                    coroutineScope.launch {
                        animatableY.animateTo(
                            targetValue = 500f, // End position (vertical movement)
                            animationSpec = tween(
                                durationMillis = 3000, // 3 seconds duration
                                easing = LinearEasing // Smooth linear animation
                            )
                        )
                        isAnimating = false
                    }
                },
                enabled = !isAnimating // Disable the button while animating
            ) {
                Text("Start Movement")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        animatableY.snapTo(0f) // Reset the animation position
                    }
                }
            ) {
                Text("Reset")
            }
        }
    }
}
