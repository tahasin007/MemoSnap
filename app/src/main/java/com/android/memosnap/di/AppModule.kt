package com.android.memosnap.di

import android.app.Application
import androidx.room.Room
import com.android.memosnap.feature.dailytask.data.repository.CategoryRepositoryImpl
import com.android.memosnap.feature.dailytask.data.source.TaskDao
import com.android.memosnap.feature.data.source.AppDatabase
import com.android.memosnap.feature.note.data.repository.NoteRepositoryImpl
import com.android.memosnap.feature.note.data.repository.NoteTagRepositoryImpl
import com.android.memosnap.feature.note.domain.repository.CategoryRepository
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import com.android.memosnap.feature.note.domain.usecase.category.CategoryUseCases
import com.android.memosnap.feature.note.domain.usecase.category.DeleteCategoryUseCase
import com.android.memosnap.feature.note.domain.usecase.category.GetCategoriesUseCase
import com.android.memosnap.feature.note.domain.usecase.category.GetCategoryByIdUseCase
import com.android.memosnap.feature.note.domain.usecase.category.GetCategoryByNameUseCase
import com.android.memosnap.feature.note.domain.usecase.category.InsertCategoryUseCase
import com.android.memosnap.feature.note.domain.usecase.note.*
import com.android.memosnap.feature.note.domain.usecase.notetag.AddNoteTag
import com.android.memosnap.feature.note.domain.usecase.notetag.DeleteNoteTag
import com.android.memosnap.feature.note.domain.usecase.notetag.GetNoteTag
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
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .addMigrations(
                AppDatabase.MIGRATION_1_2,
                AppDatabase.MIGRATION_2_3,
                AppDatabase.MIGRATION_3_4
                // MIGRATION_4_5 registered in Phase 4 when task table is removed
            )
            .addCallback(AppDatabase.getCallback(app))
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao {
        return db.taskDao
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: AppDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao, db.checklistItemDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            getNotesByCategory = GetNotesByCategory(repository),
            getTagsByNoteId = GetTagsByNoteId(repository),
            addTagToNote = AddTagToNote(repository),
            removeTagFromNote = RemoveTagFromNote(repository),
            getChecklistItems = GetChecklistItems(repository),
            insertChecklistItem = InsertChecklistItem(repository),
            deleteChecklistItem = DeleteChecklistItem(repository),
            toggleNoteCompletion = ToggleNoteCompletion(repository),
            updateNotePriority = UpdateNotePriority(repository),
            setNoteDueDate = SetNoteDueDate(repository),
            setNoteCategory = SetNoteCategory(repository),
            convertNoteType = ConvertNoteType(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNoteTagRepository(db: AppDatabase): NoteTagRepository {
        return NoteTagRepositoryImpl(db.noteTagDao)
    }

    @Provides
    @Singleton
    fun provideNoteTagUseCases(repository: NoteTagRepository): NoteTagUseCases {
        return NoteTagUseCases(
            getNoteTags = GetNoteTags(repository),
            deleteNoteTag = DeleteNoteTag(repository),
            addNoteTag = AddNoteTag(repository),
            getNotesByTag = GetNotesByTagId(repository),
            getTagById = GetNoteTag(repository)
        )
    }


    @Provides
    @Singleton
    fun provideCategoryRepository(db: AppDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao, db.noteDao)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getAllCategories = GetCategoriesUseCase(repository),
            insertCategory = InsertCategoryUseCase(repository),
            deleteCategory = DeleteCategoryUseCase(repository),
            getCategoryByName = GetCategoryByNameUseCase(repository),
            getCategoryById = GetCategoryByIdUseCase(repository)
        )
    }
}