package app.eleven.data.repository

import app.eleven.data.remote.api.FdjApi
import app.eleven.data.remote.dto.LeagueDto
import app.eleven.data.remote.dto.TeamDto
import javax.inject.Inject

interface FdjRepository {
	suspend fun getTeamsByLeague(league: String): List<TeamDto>

	suspend fun getLeagues(): List<LeagueDto>

	suspend fun getTeamByName(team: String): TeamDto
}

internal class FdjDatasource @Inject constructor(private val api: FdjApi) : FdjRepository {
	override suspend fun getTeamsByLeague(league: String): List<TeamDto> =
		api.getTeamsFromLeague(leagueName = league).teams

	override suspend fun getLeagues(): List<LeagueDto> = api.getAllLeagues().leagues

	override suspend fun getTeamByName(team: String): TeamDto = api.getTeamFromName(team).teams[0]

}