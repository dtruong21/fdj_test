package app.eleven.fdjsport

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.eleven.common.Resource
import app.eleven.domain.interactors.FdjInteractor
import app.eleven.domain.interactors.use_cases.GetLeaguesUC
import app.eleven.domain.interactors.use_cases.GetTeamDetailUC
import app.eleven.domain.interactors.use_cases.GetTeamsByLeagueNameUC
import app.eleven.domain.model.League
import app.eleven.fdjsport.common.mock
import app.eleven.fdjsport.common.whenever
import app.eleven.fdjsport.main_screen.MainScreenViewModel
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

	@Rule
	@JvmField
	val rule = InstantTaskExecutorRule()

	private val getLeaguesUC = mock<GetLeaguesUC>()
	private val getTeamsByLeagueNameUC = mock<GetTeamsByLeagueNameUC>()
	private val getTeamDetailUC = mock<GetTeamDetailUC>()

	private val fdjInteractor by lazy {
		FdjInteractor(
			getLeaguesUC,
			getTeamsByLeagueNameUC,
			getTeamDetailUC
		)
	}

	val viewModel by lazy { MainScreenViewModel(fdjInteractor) }

	@Test
	fun testLeaguesListSuccessStatus() {
		whenever(fdjInteractor.getLeaguesUC()).thenReturn(flow { emit(Resource.Success(emptyList())) })

		viewModel.getLeagues()

		assertEquals(emptyList<League>(), viewModel.state.value.leagues)
	}

}
