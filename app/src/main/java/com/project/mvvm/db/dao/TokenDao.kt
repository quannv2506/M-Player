package com.project.mvvm.db.dao

import androidx.room.*
import com.project.mvvm.db.entity.Token
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Token)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTokens(tokens: List<Token>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateToken(token: Token)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTokens(tokens: List<Token>)

    @Delete
    fun deleteToken(token: Token)

    @Delete
    fun deleteTokens(token: List<Token>)

    @Query("SELECT * FROM tokens where chainId = :chainId")
    fun getAllTokenByChain(chainId: String): List<Token>

    @Query("SELECT * FROM tokens")
    fun getAllToken(): List<Token>

    @Query("SELECT * FROM tokens")
    fun getAllTokenLiveData(): Observable<List<Token>>

    @Query("SELECT * FROM tokens WHERE chainId = :chain AND symbol = :symbol")
    fun getTokenBySymbol(chain: String, symbol: String): Token?

    @Query("SELECT * FROM tokens WHERE chainId = :chain AND address = :address")
    fun getTokenByAddress(chain: String, address: String): Token?

    @Query("SELECT * FROM tokens where chainId = :chainId")
    fun all(chainId: String): Flowable<List<Token>>

    @Query("Delete from tokens where primaryKey in (:listPrimaryKey)")
    fun deleteTokensByListPrimaryKey(listPrimaryKey: List<String>)

    @Query("Delete from tokens where primaryKey = :primaryKey")
    fun deleteTokenByPrimaryKey(primaryKey: String)
}