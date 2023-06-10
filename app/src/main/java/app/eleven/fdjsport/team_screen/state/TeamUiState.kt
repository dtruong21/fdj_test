package app.eleven.fdjsport.team_screen.state

import app.eleven.domain.model.Team

data class TeamUiState(
	val isLoading: Boolean = false,
	val team: Team? = null,
	val error: String = ""
)
