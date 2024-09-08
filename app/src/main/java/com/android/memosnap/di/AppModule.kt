package com.android.memosnap.di

import android.app.Application
import androidx.room.Room
import com.android.memosnap.feature.dailytask.data.repository.CategoryRepositoryImpl
import com.android.memosnap.feature.dailytask.data.repository.TaskRepositoryImpl
import com.android.memosnap.feature.dailytask.domain.repository.CategoryRepository
import com.android.memosnap.feature.dailytask.domain.repository.TaskRepository
import com.android.memosnap.feature.dailytask.domain.usecase.category.DeleteTaskUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.category.GetAllTasksUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.category.GetTaskUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.category.InsertTaskUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.category.TaskUseCases
import com.android.memosnap.feature.dailytask.domain.usecase.task.CategoryUseCases
import com.android.memosnap.feature.dailytask.domain.usecase.task.DeleteCategoryUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.task.GetCategoriesUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.task.GetCategoryByIdUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.task.GetCategoryByNameUseCase
import com.android.memosnap.feature.dailytask.domain.usecase.task.InsertCategoryUseCase
import com.android.memosnap.feature.data.source.AppDatabase
import com.android.memosnap.feature.note.data.repository.NoteRepositoryImpl
import com.android.memosnap.feature.note.data.repository.NoteTagRepositoryImpl
import com.android.memosnap.feature.note.domain.repository.NoteRepository
import com.android.memosnap.feature.note.domain.repository.NoteTagRepository
import com.android.memosnap.feature.note.domain.usecase.note.AddNote
import com.android.memosnap.feature.note.domain.usecase.note.AddTagToNote
import com.android.memosnap.feature.note.domain.usecase.note.DeleteNote
import com.android.memosnap.feature.note.domain.usecase.note.GetNote
import com.android.memosnap.feature.note.domain.usecase.note.GetNotes
import com.android.memosnap.feature.note.domain.usecase.note.GetTagsByNoteId
import com.android.memosnap.feature.note.domain.usecase.note.NoteUseCases
import com.android.memosnap.feature.note.domain.usecase.note.RemoveTagFromNote
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
        ).addCallback(AppDatabase.getCallback(app)).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: AppDatabase): NoteRepository {
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
            addTagToNote = AddTagToNote(repository),
            removeTagFromNote = RemoveTagFromNote(repository)
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
    fun provideTaskRepository(db: AppDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getAllTasks = GetAllTasksUseCase(repository),
            getTask = GetTaskUseCase(repository),
            insertTask = InsertTaskUseCase(repository),
            deleteTask = DeleteTaskUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: AppDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao, db.taskDao)
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