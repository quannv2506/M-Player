package com.project.mvvm.db

import android.content.Context
import androidx.room.*
import com.project.mvvm.db.dao.TokenDao
import com.project.mvvm.db.entity.Token

@Database(entities = [Token::class], version = 1)

@TypeConverters(
    DataTypeConverter::class,
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(context).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "my-wallet.db"
            ).build()
    }
}