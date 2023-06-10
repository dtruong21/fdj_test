package app.eleven.fdjsport

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.eleven.fdjsport.main_screen.MainScreen
import app.eleven.fdjsport.team_screen.TeamScreen

sealed class Screen(val route: String) {
	object MainScreen : Screen("main")
	object TeamScreen : Screen("team")
}

@Composable
fun NavigationGraph(navHostController: NavHostController, paddingValues: PaddingValues) {
	NavHost(
		navController = navHostController,
		startDestination = Screen.MainScreen.route,
		modifier = Modifier.padding(paddingValues)
	) {
		composable(Screen.MainScreen.route) {
			MainScreen(navController = navHostController)
		}
		composable(
			Screen.TeamScreen.route + "/{team}",
			arguments = listOf(navArgument("team") { type = NavType.StringType })
		) {
			TeamScreen()
		}
	}

}