package app.eleven.fdjsport.main_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.eleven.domain.model.Team
import app.eleven.fdjsport.Screen
import app.eleven.fdjsport.main_screen.event.MainScreenEvent
import app.eleven.fdjsport.main_screen.state.MainUiState
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = hiltViewModel()) {
	val uiState = viewModel.state.collectAsStateWithLifecycle()
	val context = LocalContext.current

	when {
		uiState.value.isLoading -> {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator(
					modifier = Modifier.align(Alignment.Center),
					color = Color.Red
				)
			}
		}

		uiState.value.error.isNotEmpty() -> {
			LaunchedEffect(key1 = Unit) {
				Toast.makeText(context, uiState.value.error, Toast.LENGTH_SHORT).show()
			}
		}

		uiState.value.leagues.isNotEmpty() -> {
			val leagues = uiState.value.leagues.map { it.name }
			val text = remember {
				mutableStateOf("")
			}

			Column() {
				Row() {
					TextFieldAutocomplete(list = leagues, setValue = {
						text.value = it
						viewModel.onEventChanged(MainScreenEvent.OnLeagueClick(text.value))
					})
				}
				TeamListScreen(navController = navController, uiState.value)
			}
		}
	}


}

@Composable
fun TextFieldAutocomplete(
	list: List<String>,
	setValue: (String) -> Unit
) {
	val text = remember { mutableStateOf("") }  // text in textfield
	val expanded = remember { mutableStateOf(false) } // for dropdownmenu
	var modifier: Modifier
	val dropList = remember { mutableStateOf(listOf("")) }
	dropList.value = list.filter { it.contains(text.value, ignoreCase = true) }

	Card(
		Modifier
			.padding(10.dp)
			.fillMaxWidth(), elevation = 10.dp, border = BorderStroke(1.dp, Color.LightGray)
	) {
		Column(
			Modifier
				.fillMaxWidth()
				.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
		) {
			Box(modifier = Modifier.wrapContentSize(Alignment.TopStart).testTag("SearchBar")) {
				TextField(value = text.value,
					modifier = Modifier
						.fillMaxWidth(),
					onValueChange = {
						text.value = it

					})
				if (dropList.value.isNotEmpty() && text.value != "") {
					modifier = if (dropList.value.size > 7) {
						Modifier.height(300.dp)
					} else {
						Modifier
					}

					expanded.value = !(dropList.value.size == 1 && dropList.value[0] == text.value)

					DropdownMenu(
						expanded = expanded.value,
						onDismissRequest = { expanded.value = false },
						properties = PopupProperties(focusable = false),
						modifier = modifier.fillMaxWidth().testTag("Dropdown")
					) {
						dropList.value.forEach {
							DropdownMenuItem(modifier = Modifier.testTag("list"), onClick = {
								text.value = it
								setValue(it)
								dropList.value = emptyList()
								expanded.value = false

							}) {
								Text(text = it)
							}
						}
					}
				} else {
					expanded.value = false
				}
			}
		}
	}
}

@Composable
fun TeamListScreen(
	navController: NavController,
	uiState: MainUiState
) {
	val numberOfItemsByRow = LocalConfiguration.current.screenWidthDp / 250
	Timber.e("Size ${uiState.teams.size}")
	Box(modifier = Modifier.fillMaxWidth()) {
		if (uiState.teams.isNotEmpty()) {
			LazyVerticalGrid(columns = GridCells.Fixed(2)) {
				items(uiState.teams.chunked(numberOfItemsByRow)) { rowItems ->
					Row(
						horizontalArrangement = Arrangement.Center,
						modifier = Modifier.padding(4.dp)
					) {
						rowItems.forEach { team ->
							TeamListItem(team = team, onItemClick = {
								navController.navigate(Screen.TeamScreen.route + "/${team.name}")
							})
						}
					}
				}
			}
		}

		if (uiState.error.isNotEmpty()) {
			Text(
				text = uiState.error,
				color = MaterialTheme.colors.error,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp)
					.align(Alignment.Center)
			)
		}
		if (uiState.isLoading) {
			CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
		}
		if (uiState.leagues.isEmpty()) {
			Text(
				text = "No data from database",
				color = MaterialTheme.colors.error,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp)
					.align(Alignment.Center)
			)
		}

	}
}

@Composable
fun TeamListItem(
	team: Team,
	onItemClick: (Team) -> Unit
) {
	Row(modifier = Modifier
		.fillMaxWidth()
		.clickable { onItemClick(team) }
		.padding(20.dp),
		horizontalArrangement = Arrangement.SpaceBetween) {
		GlideImage(
			imageModel = team.badge,
			modifier = Modifier,
			loading = {
				CircularProgressIndicator(
					modifier = Modifier.fillMaxSize()
				)
			},
			failure = {
				Text(text = "Image request failed")
			}
		)
	}
}
