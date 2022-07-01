package xyz.heydarrn.noteappdicoding.database

import androidx.lifecycle.LiveData
import androidx.room.*
import xyz.heydarrn.noteappdicoding.domain.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}