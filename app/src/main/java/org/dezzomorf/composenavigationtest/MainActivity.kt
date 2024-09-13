package org.dezzomorf.composenavigationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.dezzomorf.composenavigationtest.ui.theme.ComposeNavigationTestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigationTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = FirstScreenNavigator,
                    enterTransition = { EnterTransition.None},
                    exitTransition = { ExitTransition.None}
                ) {
                    composable<FirstScreenNavigator> {
                        FirstScreen(navController = navController)
                    }
                    composable<SecondScreenNavigator> {
                        val args = it.toRoute<SecondScreenNavigator>()
                        SecondScreen(navController = navController, args = args)
                    }
                    composable<ThirdScreenNavigator> {
                        val args = it.toRoute<ThirdScreenNavigator>()
                        ThirdScreen(navController = navController, args = args)
                    }
                    composable<FourthScreenNavigator> {
                        FourthScreen(navController = navController)
                    }
                    composable<FifthScreenNavigator> {
                        FifthScreen(navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
private fun BackButton(navController: NavHostController) {
    Button(onClick = {
        navController.popBackStack()
    }) {
        Text(text = "Back")
    }
}

@Composable
private fun FirstScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )
        Button(onClick = {
            navController.navigate(SecondScreenNavigator(text = text))
        }) {
            Text(text = "Go to SecondScreen")
        }
    }
}

@Composable
private fun SecondScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    args: SecondScreenNavigator
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = args.text ?: "")
        Button(onClick = {
            navController.navigate(ThirdScreenNavigator(num = Random.nextInt()))
        }) {
            Text(text = "Go to ThirdScreen")
        }
        BackButton(navController)
    }
}

@Composable
private fun ThirdScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    args: ThirdScreenNavigator
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = args.num.toString())
        Button(onClick = {
            navController.navigate(FourthScreenNavigator)
        }) {
            Text(text = "Go to FourthScreen")
        }
        BackButton(navController)
    }
}

@Composable
private fun FourthScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate(FifthScreenNavigator)
        }) {
            Text(text = "Go to FifthScreen")
        }
        BackButton(navController)
    }
}

@Composable
private fun FifthScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(navController)
    }
}

@Serializable
object FirstScreenNavigator

@Serializable
data class SecondScreenNavigator(
    val text: String?
)

@Serializable
data class ThirdScreenNavigator(
    val num: Int
)

@Serializable
object FourthScreenNavigator

@Serializable
object FifthScreenNavigator
