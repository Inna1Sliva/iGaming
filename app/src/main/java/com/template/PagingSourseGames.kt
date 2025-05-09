package com.template


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.FirebaseDatabase
import com.template.data.model.ui.Games
import kotlinx.coroutines.tasks.await

class PagingSourseGames(private val database: FirebaseDatabase): PagingSource<Int, Games>(){

    override fun getRefreshKey(state: PagingState<Int, Games>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Games> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        return try {
           val querySnapsho = database.getReference("igaming")
               .orderByKey()
               .startAt(page.toString())
               .limitToFirst(pageSize)
               .get()
               .await()

            val games =querySnapsho.children.mapNotNull { it.getValue(Games::class.java) }

            LoadResult.Page(
                data = games,
                prevKey = if (page == 0) null else page - pageSize,
                nextKey = if (games.isEmpty()) null else page + pageSize)

        }catch (e:Exception){
            LoadResult.Error(e)
        }

    }

}
