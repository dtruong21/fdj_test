package app.eleven.domain.interactors.use_cases

import app.eleven.common.Resource
import app.eleven.data.repository.FdjRepository
import app.eleven.domain.model.League
import app.eleven.domain.model.toLeague
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class GetLeaguesUC @Inject constructor(private val repository: FdjRepository) {
	operator fun invoke(): Flow<Resource<List<League>>> = flow {
		try {
			emit(Resource.Loading())
			val leagues = repository.getLeagues().map { it.toLeague() }
			emit(Resource.Success(leagues))
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