package app.eleven.domain.use_cases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.eleven.data.repository.FdjRepository
import app.eleven.domain.common.mock
import app.eleven.domain.common.whenever
import app.eleven.domain.interactors.use_cases.GetLeaguesUC
import app.eleven.domain.model.League
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetLeaguesUCTest {

	@Rule
	@JvmField
	val rule = InstantTaskExecutorRule()

	private val repository: FdjRepository = mock<FdjRepository>()
	val getLeaguesUC = GetLeaguesUC(repository)

	@Test
	fun `Get League list with success`() {

		runBlocking {
			whenever(repository.getLeagues()).thenReturn(emptyList())

			val leagues = getLeaguesUC().last().data
			assertEquals(leagues, emptyList<League>())
		}
	}

	@Test
	fun `Get League list in initial state`() {

		runBlocking {
			val leagues = getLeaguesUC().first().data
			val message = getLeaguesUC().first().message
			assertEquals(leagues, null)
			assertTrue(message.isNullOrEmpty())
		}
	}


}