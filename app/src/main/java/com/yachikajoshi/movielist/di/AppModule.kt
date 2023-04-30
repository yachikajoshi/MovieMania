package com.yachikajoshi.movielist.di

import com.yachikajoshi.movielist.common.Constants
import com.yachikajoshi.movielist.data.datasource.MovieAPIService
import com.yachikajoshi.movielist.data.repo.FakeMovieRepo
import com.yachikajoshi.movielist.data.repo.GetMoviesImpl
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun MovieAPIService(retrofit: Retrofit): MovieAPIService =
        retrofit.create(MovieAPIService::class.java)


    @Provides
    fun provideTop10MoviesRepository(apiService: MovieAPIService): MoviesRepository =
        GetMoviesImpl(apiService)
}