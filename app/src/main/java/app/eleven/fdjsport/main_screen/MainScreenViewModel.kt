package app.eleven.fdjsport.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.eleven.common.Resource
import app.eleven.domain.interactors.FdjInteractor
import app.eleven.fdjsport.main_screen.event.MainScreenEvent
import app.eleven.fdjsport.main_screen.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val fdjInteractor: FdjInteractor
) : ViewModel() {

	private val _state = MutableStateFlow(MainUiState())
	val state = _state.asStateFlow()

	init {
		getLeagues()
	}

	 internal fun getLeagues() {
		fdjInteractor.getLeaguesUC().onEach { resource ->
			when (resource) {
				is Resource.Error -> _state.update {
					it.copy(
						isLoading = false,
						leagues = emptyList(),
						error = resource.message ?: "An unexpected error happened"
					)
				}

				is Resource.Loading -> _state.update {
					it.copy(
						isLoading = true,
						leagues = emptyList(),
						error = ""
					)
				}

				is Resource.Success -> _state.update {
					it.copy(
						isLoading = false,
						leagues = resource.data ?: emptyList(),
						error = ""
					)
				}
			}
		}.launchIn(viewModelScope.plus(Dispatchers.IO))
	}

	internal fun getTeamsFromLeague(league: String) {
		fdjInteractor.getTeamsByLeagueNameUC(league).onEach { resource ->
			when (resource) {
				is Resource.Error -> _state.update {
					it.copy(
						isLoading = false,
						teams = emptyList(),
						error = resource.message ?: "An unexpected error happened"
					)
				}

				is Resource.Loading -> _state.update {
					it.copy(
						isLoading = true,
						teams = emptyList(),
						error = ""
					)
				}

				is Resource.Success -> _state.update {
					it.copy(
						isLoading = false,
						teams = resource.data ?: emptyList(),
						error = ""
					)
				}
			}
		}.launchIn(viewModelScope.plus(Dispatchers.IO))
	}

	fun onEventChanged(event: MainScreenEvent) {
		when (event) {
			is MainScreenEvent.OnLeagueClick -> getTeamsFromLeague(event.league)
		}
	}
}