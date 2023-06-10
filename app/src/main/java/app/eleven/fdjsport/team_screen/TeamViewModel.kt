package app.eleven.fdjsport.team_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.eleven.common.Resource
import app.eleven.domain.interactors.FdjInteractor
import app.eleven.fdjsport.team_screen.state.TeamUiState
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
class TeamViewModel @Inject constructor(
	private val fdjInteractor: FdjInteractor,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val _state = MutableStateFlow(TeamUiState())
	val state = _state.asStateFlow()

	private val name: String = checkNotNull(savedStateHandle["team"])

	init {
		getTeam(name = name)
	}

	private fun getTeam(name: String) {
		fdjInteractor.getTeamDetailUC(name).onEach { resource ->
			when (resource) {
				is Resource.Error -> {
					_state.update {
						it.copy(
							isLoading = false,
							team = null,
							error = resource.message ?: "An unexpected error happened"
						)
					}
				}

				is Resource.Loading -> _state.update {
					it.copy(
						isLoading = true,
						team = null,
						error = ""
					)
				}

				is Resource.Success -> _state.update {
					it.copy(
						isLoading = false,
						team = resource.data,
						error = ""
					)
				}
			}
		}.launchIn(viewModelScope.plus(Dispatchers.IO))
	}
}