package app.eleven.fdjsport.team_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TeamScreen(
	viewModel: TeamViewModel = hiltViewModel()
) {
	val uiState = viewModel.state.collectAsStateWithLifecycle().value
	Box(modifier = Modifier.fillMaxWidth()) {
		uiState.team?.let { team ->
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(20.dp)
			) {
				item {
					GlideImage(
						imageModel = team.banner,
						modifier = Modifier.fillMaxSize()
					)
					Spacer(modifier = Modifier.height(15.dp))
					Text(text = "Name: ${team.fullName}")
					Spacer(modifier = Modifier.height(15.dp))
					Text(text = "League: ${team.league}")
					Spacer(modifier = Modifier.height(15.dp))
					Text(text = "Nickname: ${team.nickname}")
					Spacer(modifier = Modifier.height(15.dp))
					Text(text = "Country: ${team.location}")
					Spacer(modifier = Modifier.height(15.dp))
					Text(text = team.description)
				}
			}
		}

		if (uiState.error.isNotBlank()) {
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
	}
}