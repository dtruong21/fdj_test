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

open class GetTeamDetailUC @Inject constructor(private val repository: FdjRepository) {
	operator fun invoke(name: String): Flow<Resource<Team>> = flow {
		try {
			emit(Resource.Loading())
			val team = repository.getTeamByName(team = name).toTeam()
			emit(Resource.Success(team))
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