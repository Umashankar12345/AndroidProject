package com.example.nearmeet.di

import android.content.Context
import androidx.room.Room
import com.example.nearmeet.data.local.EventDao
import com.example.nearmeet.data.local.NearMeetDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NearMeetDatabase =
        Room.databaseBuilder(
            context,
            NearMeetDatabase::class.java,
            "nearmeet_db"
        ).build()

    @Provides
    @Singleton
    fun provideEventDao(
        database: NearMeetDatabase
    ): EventDao = database.eventDao()
}