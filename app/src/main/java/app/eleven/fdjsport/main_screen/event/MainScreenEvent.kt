package app.eleven.fdjsport.main_screen.event

import app.eleven.domain.model.League

sealed class MainScreenEvent {
	data class OnLeagueClick(val league: String) : MainScreenEvent()
}
