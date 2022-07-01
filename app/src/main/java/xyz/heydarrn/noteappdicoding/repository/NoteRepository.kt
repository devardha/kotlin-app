package xyz.heydarrn.noteappdicoding.repository

import android.app.Application
import androidx.lifecycle.LiveData
import xyz.heydarrn.noteappdicoding.database.NoteDAO
import xyz.heydarrn.noteappdicoding.database.NoteDatabase
import xyz.heydarrn.noteappdicoding.domain.Note
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao: NoteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }
    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }
    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }
    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}