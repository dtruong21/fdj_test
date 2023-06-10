package app.eleven.data.remote.dto

import com.squareup.moshi.Json

data class LeagueDto(
	@Json(name = "idLeague")
	val idLeague: String?,
	@Json(name = "strLeague")
	val strLeague: String?,
	@Json(name = "strLeagueAlternate")
	val strLeagueAlternate: String?,
	@Json(name = "strSport")
	val strSport: String?
)

data class FdjLeaguesResponse(
	@Json(name = "leagues")
	val leagues: List<LeagueDto>
)

