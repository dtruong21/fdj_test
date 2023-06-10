package app.eleven.domain.common

import app.eleven.data.remote.dto.LeagueDto
import app.eleven.data.remote.dto.TeamDto
import app.eleven.data.repository.FdjRepository

class FakeLeagueRepository : FdjRepository {

	val league1 = LeagueDto("string1", "Premier League", "", "")
	val league2 = LeagueDto("string2", "Premier Liga", "", "")
	val league3 = LeagueDto("string3", "Ligue 1", "", "")
	val league4 = LeagueDto("string4", "La Liga", "", "")
	val league5 = LeagueDto("string5", "Seria A", "", "")
	override suspend fun getTeamsByLeague(league: String): List<TeamDto> {
		TODO("Not yet implemented")
	}

	override suspend fun getLeagues(): List<LeagueDto> =
		listOf(league1, league2, league3, league4, league5)

	override suspend fun getTeamByName(team: String): TeamDto {
		TODO("Not yet implemented")
	}
}