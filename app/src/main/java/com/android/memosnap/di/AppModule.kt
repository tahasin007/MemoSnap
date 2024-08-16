package com.android.memosnap.di

import android.app.Application
import androidx.room.Room
import com.android.memosnap.feature.note.data.repository.NoteRepositoryImpl
import com.android.memosnap.feature.note.data.repository.NoteTagRepositoryImpl
import com.android.memosnap.feature.note.data.source.NoteDatabase
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import com.android.memosnap.feature.note.domain.usecase.note.AddNote
import com.android.memosnap.feature.note.domain.usecase.note.AddTagToNote
import com.android.memosnap.feature.note.domain.usecase.note.DeleteNote
import com.android.memosnap.feature.note.domain.usecase.note.GetNote
import com.android.memosnap.feature.note.domain.usecase.note.GetNotes
import com.android.memosnap.feature.note.domain.usecase.note.GetTagsByNoteId
import com.android.memosnap.feature.note.domain.usecase.note.NoteUseCases
import com.android.memosnap.feature.note.domain.usecase.notetag.AddNoteTag
import com.android.memosnap.feature.note.domain.usecase.notetag.DeleteNoteTag
import com.android.memosnap.feature.note.domain.usecase.notetag.GetNoteTags
import com.android.memosnap.feature.note.domain.usecase.notetag.GetNotesByTagId
import com.android.memosnap.feature.note.domain.usecase.notetag.NoteTagUseCases
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
            getNote = GetNote(repository),
            getTagsByNoteId = GetTagsByNoteId(repository),
            addTagToNote = AddTagToNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteTagRepository(db: NoteDatabase): NoteTagRepository {
        return NoteTagRepositoryImpl(db.noteTagDao)
    }

    @Provides
    @Singleton
    fun provideNoteTagUseCases(repository: NoteTagRepository): NoteTagUseCases {
        return NoteTagUseCases(
            getNoteTags = GetNoteTags(repository),
            deleteNoteTag = DeleteNoteTag(repository),
            addNoteTag = AddNoteTag(repository),
            getNotesByTag = GetNotesByTagId(repository)
        )
    }
}