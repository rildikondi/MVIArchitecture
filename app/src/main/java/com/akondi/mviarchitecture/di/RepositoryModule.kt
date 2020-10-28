package com.akondi.mviarchitecture.di

import com.akondi.mviarchitecture.repository.MainRepository
import com.akondi.mviarchitecture.retrofit.BlogRetrofit
import com.akondi.mviarchitecture.retrofit.NetworkMapper
import com.akondi.mviarchitecture.room.BlogDao
import com.akondi.mviarchitecture.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}
