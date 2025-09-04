package com.shahpourkhast.rohamfood.di

import android.content.Context
import androidx.room.Room
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodsDatabase =
        Room.databaseBuilder(context, FoodsDatabase::class.java, "foods.db")
            // .fallbackToDestructiveMigration() // optional
            .build()

    @Provides
    fun provideFoodsDao(db: FoodsDatabase): FoodsDao = db.foodsDao
}
