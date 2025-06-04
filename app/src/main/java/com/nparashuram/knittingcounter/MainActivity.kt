package com.nparashuram.knittingcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nparashuram.knittingcounter.ui.theme.CounterTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.animation.core.animateDpAsState
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalDensity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            CounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    CounterScreen()
                }
            }
        }
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    var isPressed by remember { mutableStateOf(false) }
    var buttonClicked by remember { mutableStateOf(false) }
    val buttonHeight = if (isPressed) 24.dp else 48.dp
    val animatedHeight by animateDpAsState(targetValue = buttonHeight, label = "buttonHeight")
    val digitBoxSize = 80.dp
    val digitBoxSizePx = with(LocalDensity.current) { digitBoxSize.toPx() }

    LaunchedEffect(buttonClicked) {
        if (buttonClicked) {
            isPressed = true
            delay(150)
            isPressed = false
            buttonClicked = false
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val boxWidth = maxWidth
        val boxHeight = maxHeight

        // Calculate image dimensions and position
        val imageAspectRatio = 433f / 493f // Actual image aspect ratio (height/width)
        val imageWidth = boxWidth
        val imageHeight = boxWidth * imageAspectRatio

        // Background image - aligned to top
        Image(
            painter = painterResource(id = R.drawable.counter_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Fit
        )

        // Calculate positions relative to image top
        val topButtonYOffset = imageHeight * 0.15f // Position for the large elevated button
        val digitYOffset = imageHeight * 0.38f // Moved up from 0.45f
        val bottomButtonYOffset = imageHeight * 0.82f // Position for bottom row buttons
        val digitXOffset = boxWidth * 0.08f // Horizontal spacing for digits
        val bottomButtonSpacing = boxWidth * 0.35f // Spacing between bottom buttons

        // --- Top Center: Increase Counter Button ---
        Button(
            onClick = {
                count++
                buttonClicked = true
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = topButtonYOffset - animatedHeight)
                .height(animatedHeight)
                .width(boxWidth * 0.4f), // Make button wider
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("Increase Counter", color = Color.Black)
        }

        // --- Digits ---
        val digits = count.toString().padStart(2, '0')
        // Left eye
        DigitBox(
            digit = digits[0],
            modifier = Modifier
                .size(digitBoxSize)
                .align(Alignment.TopCenter)
                .offset(x = -digitXOffset, y = digitYOffset)
        )
        // Right eye
        DigitBox(
            digit = digits[1],
            modifier = Modifier
                .size(digitBoxSize)
                .align(Alignment.TopCenter)
                .offset(x = digitXOffset, y = digitYOffset)
        )

        // --- Bottom Row Buttons ---
        val bottomButtonHeight = imageHeight * 0.12f
        val bottomButtonWidth = boxWidth * 0.18f // Slightly smaller width to accommodate more spacing

        // Left (-)
        Button(
            onClick = { if (count > 0) count-- },
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = bottomButtonSpacing * 0.49f, y = bottomButtonYOffset) // Adjusted offset
                .height(bottomButtonHeight)
                .width(bottomButtonWidth),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("-", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // Center (Reset)
        Button(
            onClick = { count = 0 },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = bottomButtonYOffset)
                .height(bottomButtonHeight)
                .width(bottomButtonWidth),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("00", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        // Right (+)
        Button(
            onClick = { count++ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = -bottomButtonSpacing * 0.5f, y = bottomButtonYOffset) // Adjusted offset
                .height(bottomButtonHeight)
                .width(bottomButtonWidth),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("+", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DigitBox(digit: Char, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit.toString(),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}