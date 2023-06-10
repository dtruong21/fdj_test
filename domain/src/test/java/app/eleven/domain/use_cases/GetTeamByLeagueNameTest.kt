package app.eleven.domain.use_cases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.eleven.data.repository.FdjRepository
import app.eleven.domain.common.mock
import app.eleven.domain.common.whenever
import app.eleven.domain.interactors.use_cases.GetTeamsByLeagueNameUC
import app.eleven.domain.model.League
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GetTeamByLeagueNameTest {
	@Rule
	@JvmField
	val rule = InstantTaskExecutorRule()

	private val repository: FdjRepository = mock<FdjRepository>()
	val getTeamsUC = GetTeamsByLeagueNameUC(repository)

	@Test
	fun `Get League list with success`() {

		runBlocking {
			whenever(repository.getTeamsByLeague("Premier League")).thenReturn(emptyList())

			val leagues = getTeamsUC("Premier League").last().data
			Assert.assertEquals(leagues, emptyList<League>())
		}
	}

	@Test
	fun `Get League list in initial state`() {

		runBlocking {
			val leagues = getTeamsUC("Premier League").first().data
			val message = getTeamsUC("Premier League").first().message
			Assert.assertEquals(leagues, null)
			Assert.assertTrue(message.isNullOrEmpty())
		}
	}
}