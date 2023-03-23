package com.quannv.music.repository

import com.quannv.music.db.AppDatabase
import com.quannv.music.db.entity.Token
import io.reactivex.Observable

class TokenRepository(private val appDatabase: AppDatabase) {


    fun insertTokens(tokens: List<Token>) {
        appDatabase.tokenDao().insertTokens(tokens)
    }

    fun getAllTokens(): Observable<List<Token>>{
        return appDatabase.tokenDao().getAllTokenLiveData()
    }


}