package com.akondi.mviarchitecture.repository

import com.akondi.mviarchitecture.model.Blog
import com.akondi.mviarchitecture.retrofit.BlogRetrofit
import com.akondi.mviarchitecture.retrofit.NetworkMapper
import com.akondi.mviarchitecture.room.BlogDao
import com.akondi.mviarchitecture.room.CacheMapper
import com.akondi.mviarchitecture.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow{
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}