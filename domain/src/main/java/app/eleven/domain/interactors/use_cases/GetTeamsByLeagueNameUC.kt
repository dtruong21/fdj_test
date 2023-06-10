package app.eleven.domain.interactors.use_cases

import app.eleven.common.Resource
import app.eleven.data.repository.FdjRepository
import app.eleven.domain.model.Team
import app.eleven.domain.model.toTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class GetTeamsByLeagueNameUC @Inject constructor(private val repository: FdjRepository) {
	operator fun invoke(leagueName: String): Flow<Resource<List<Team>>> = flow {
		try {
			emit(Resource.Loading())
			val teams = repository.getTeamsByLeague(leagueName).map { it.toTeam() }
			emit(Resource.Success(teams))
		} catch (e: HttpException) {
			emit(
				Resource.Error(
					message = (e.localizedMessage?.plus(" with code : ${e.code()}"))
						?: "An unexpected error occured"
				)
			)
		} catch (e: IOException) {
			emit(
				Resource.Error(
					message = ("Couldn't reach server. Check your internet connection"
							)
				)
			)
		}
	}
}