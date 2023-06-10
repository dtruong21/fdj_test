package app.eleven.fdjsport.main_screen.state

import app.eleven.domain.model.League
import app.eleven.domain.model.Team

data class MainUiState(
	val isLoading: Boolean = false,
	val leagues: List<League> = emptyList(),
	val error: String = "",
	val teams: List<Team> = emptyList()
)
