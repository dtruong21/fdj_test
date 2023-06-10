package app.eleven.data.remote.api

import app.eleven.data.remote.dto.FdjLeaguesResponse
import app.eleven.data.remote.dto.FdjTeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FdjApi {
	@GET("/api/v1/json/50130162/all_leagues.php")
	suspend fun getAllLeagues(): FdjLeaguesResponse

	@GET("/api/v1/json/50130162/search_all_teams.php")
	suspend fun getTeamsFromLeague(@Query(value = "l") leagueName: String): FdjTeamsResponse

	@GET("/api/v1/json/50130162/searchteams.php")
	suspend fun getTeamFromName(@Query(value = "t") team: String): FdjTeamsResponse
}