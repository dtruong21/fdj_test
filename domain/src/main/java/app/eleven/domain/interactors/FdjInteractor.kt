package app.eleven.domain.interactors

import app.eleven.domain.interactors.use_cases.GetLeaguesUC
import app.eleven.domain.interactors.use_cases.GetTeamDetailUC
import app.eleven.domain.interactors.use_cases.GetTeamsByLeagueNameUC
import javax.inject.Inject

data class FdjInteractor @Inject constructor(
	val getLeaguesUC: GetLeaguesUC,
	val getTeamsByLeagueNameUC: GetTeamsByLeagueNameUC,
	val getTeamDetailUC: GetTeamDetailUC
)