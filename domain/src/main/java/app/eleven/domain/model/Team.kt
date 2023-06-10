package app.eleven.domain.model

import app.eleven.data.remote.dto.TeamDto

data class Team(
	val id: String,
	val name: String,
	val fullName: String,
	val league: String,
	val stadium: String,
	val nickname: String,
	val description: String,
	val banner: String,
	val location: String,
	val badge: String
)


fun TeamDto.toTeam(): Team = Team(
	id = idAPIfootball ?: "",
	name = strTeam ?: "",
	fullName = strAlternate ?: "",
	league = strLeague ?: "",
	stadium = strStadium ?: "",
	nickname = strKeywords ?: "",
	description = strDescriptionEN ?: "",
	banner = strTeamBanner ?: "",
	location = strCountry ?: "",
	badge = strTeamBadge ?: ""
)
