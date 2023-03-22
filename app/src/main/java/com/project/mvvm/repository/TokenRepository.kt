package com.project.mvvm.repository

import com.project.mvvm.db.AppDatabase
import com.project.mvvm.db.entity.Token
import io.reactivex.Observable

class TokenRepository(private val appDatabase: AppDatabase) {


    fun insertTokens(tokens: List<Token>) {
        appDatabase.tokenDao().insertTokens(tokens)
    }

    fun getAllTokens(): Observable<List<Token>>{
        return appDatabase.tokenDao().getAllTokenLiveData()
    }


}