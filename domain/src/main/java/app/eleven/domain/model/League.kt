package app.eleven.domain.model

import app.eleven.data.remote.dto.LeagueDto

data class League(
	val id: String,
	val name: String
)


fun LeagueDto.toLeague(): League = League(
	id = idLeague ?: "",
	name = strLeague ?: ""
)

