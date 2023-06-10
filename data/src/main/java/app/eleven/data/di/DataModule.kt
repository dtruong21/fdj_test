package app.eleven.data.di

import app.eleven.common.Constant.BASE_URL
import app.eleven.data.remote.api.FdjApi
import app.eleven.data.repository.FdjDatasource
import app.eleven.data.repository.FdjRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

	@Provides
	@Singleton
	fun provideFdjApi(): FdjApi = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(
			MoshiConverterFactory.create(
				Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
			)
		)
		.addCallAdapterFactory(CoroutineCallAdapterFactory())
		.build()
		.create(FdjApi::class.java)

	@Provides
	@Singleton
	fun provideFdjRepository(api: FdjApi): FdjRepository = FdjDatasource(api)
}