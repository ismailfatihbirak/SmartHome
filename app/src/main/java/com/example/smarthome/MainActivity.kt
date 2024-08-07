package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.ui.theme.SmartHomeTheme
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartHomeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LampWithEnhancedGlowEffect(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LampWithEnhancedGlowEffect(modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val svgPainter = painterResource(id = R.drawable.image)
    var blurAmountFab by remember { mutableStateOf(0.dp) }
    val blurAmount by animateDpAsState(
        targetValue = if (expanded) 10.dp else 0.dp,
        animationSpec = tween(durationMillis = 1000)
    )
    var showaxsd by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = svgPainter,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .blur(blurAmount),
            contentScale = ContentScale.FillBounds)
        FloatingActionButton(
            onClick = {
                expanded = !expanded
                showaxsd = !showaxsd
                blurAmountFab = if (expanded) 10.dp else 0.dp
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 30.dp)
                .size(75.dp),
            shape = CircleShape,
            containerColor = colorResource(id = R.color.yellowy)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(animationSpec = tween(durationMillis = 1500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1500))
        ) {
            MenuItems()
        }
        AnimatedVisibility(
            visible = showaxsd,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            SliderAndLayer()
        }
    }
}
@Composable
fun MenuItems() {
    val itemCount = 5
    val radius = 90.dp
    val centerOffset = 0.dp
    val startAngle = 5.0
    val endAngle = 5.0
    val itemAngle = (180 - startAngle - endAngle) / (itemCount - 1)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = centerOffset),
        contentAlignment = Alignment.CenterEnd
    ) {
        repeat(itemCount) { index ->
            val angle = Math.toRadians(90 - startAngle - index * itemAngle)
            val xOffset = (radius.value * cos(angle)).toDp()
            val yOffset = ((radius.value * sin(angle))* 1.15f).toDp()
            MenuItem(
                text = when (index) {
                    0 -> "Party Hours"
                    1 -> "Date Night"
                    2 -> "Movie Time"
                    3 -> "Guest Mode"
                    4 -> "Reading"
                    else -> "Item $index"
                },
                modifier = Modifier.offset(-xOffset, yOffset),
                size = when (index) {
                    0 -> 154.dp
                    1 -> 128.dp
                    2 -> 150.dp
                    3 -> 132.dp
                    4 -> 104.dp
                    else -> 104.dp
                },
                backgroundColor = if(index == 3){
                    colorResource(id = R.color.yellowy).copy(alpha = 0.7f)
                }else{
                    colorResource(id = R.color.blackx).copy(alpha = 0.7f)
                },
                textColor = if(index == 3){
                    colorResource(id = R.color.blackx).copy(alpha = 0.7f)
                }else{
                    colorResource(id = R.color.yellowx).copy(alpha = 0.7f)
                }
            )
        }
    }
}

@Composable
private fun Double.toDp(): Dp = (this * LocalDensity.current.density).dp

@Composable
fun GlowLayer(modifier: Modifier = Modifier, brightness: Float, color: Color, intensity: Float) {
    Box(modifier = modifier.drawBehind {
        val glowColor = color.copy(alpha = brightness)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(glowColor, Color.Transparent),
                center = Offset(size.width / 2, size.height / 2),
                radius = size.width / 2 * intensity
            ),
            radius = size.width / 2 * intensity,
            center = Offset(size.width / 2, size.height / 2)
        )
    })
}
@Composable
fun MenuItem(
    text: String,
    modifier: Modifier,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    size: Dp,
    textColor: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun SliderAndLayer(modifier: Modifier = Modifier) {
    var brightness by remember { mutableStateOf(0.0f) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
        ) {
            GlowLayer(
                modifier = Modifier
                    .size(75.dp)
                    .align(alignment = Alignment.TopEnd)
                    .offset(x = -18.dp, y = 193.dp),
                brightness = brightness,
                color = colorResource(id = R.color.yellowx),
                intensity = 3f,
                )
        }
        Spacer(modifier = Modifier.height(32.dp))
        val percentage = (brightness * 100).roundToInt()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(400.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.Gray)

                ) {
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height((400 * brightness).dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        colorResource(id = R.color.yellowy).copy(
                                            alpha = 0.5f
                                        ), colorResource(id = R.color.yellowy).copy(alpha = 1f)
                                    )
                                )
                            )
                            .clip(RoundedCornerShape(30.dp))
                            .align(Alignment.BottomCenter)
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { change, dragAmount ->
                                    val newPosition = brightness - dragAmount / 1000
                                    brightness = newPosition.coerceIn(0f, 1f)
                                }
                            }
                    )
                    Image(painter = painterResource(id = R.drawable.group_32), contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = -(340 * brightness).dp))

                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = colorResource(id = R.color.blackx).copy(alpha = 0.7f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$percentage%",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}






