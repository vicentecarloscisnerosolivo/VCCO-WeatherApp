package com.vcco.weatherapp.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * Composable Text with font weight light
 */
@Composable
fun LabelTextLight(text: String, modifier: Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light,
        modifier = modifier
    )
}

/**
 * Composable Text with font weight regular
 */
@Composable
fun LabelText(text: String, modifier: Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
