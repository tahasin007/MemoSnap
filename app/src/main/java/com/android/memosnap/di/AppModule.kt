package com.android.memosnap.di

import android.app.Application
import androidx.room.Room
import com.android.memosnap.feature.note.data.repository.NoteRepositoryImpl
import com.android.memosnap.feature.note.data.source.NoteDatabase
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import com.android.memosnap.feature.note.domain.usecase.AddNote
import com.android.memosnap.feature.note.domain.usecase.DeleteNote
import com.android.memosnap.feature.note.domain.usecase.GetNote
import com.android.memosnap.feature.note.domain.usecase.GetNotes
import com.android.memosnap.feature.note.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}