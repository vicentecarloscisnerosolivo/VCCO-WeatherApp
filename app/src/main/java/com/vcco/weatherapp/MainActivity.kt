package com.vcco.weatherapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcco.weatherapp.ui.home.HomeActivity
import com.vcco.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /*
    * Normally used to create network connection and retrieve data needed
    * but for time only created and showing for 3 seconds, on the on resumen
    * */

    private val TAG = MainActivity::class.simpleName

    private val timer by lazy {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(milisUntiFinish: Long) {
                Log.i(TAG, "Retrieving dummy information")
            }

            override fun onFinish() {
                Log.i(TAG, "Finish Getting information")
                launchNextScreen()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SplashScreenImage()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        timer.start()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    private fun launchNextScreen(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun SplashScreenImage(modifier: Modifier = Modifier) {
    val imagePainter = painterResource(R.drawable.ic_2d)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(colorResource(R.color.blue_sky)),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.splash_icon_description),
            modifier = Modifier.size(150.dp)
        )
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    Text(
        text = stringResource(R.string.app_name),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        SplashScreenImage(modifier = Modifier.fillMaxSize())
    }
}