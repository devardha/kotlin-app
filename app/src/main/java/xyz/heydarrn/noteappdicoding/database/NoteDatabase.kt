package xyz.heydarrn.noteappdicoding.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.coroutineScope
import org.json.JSONArray
import org.json.JSONException
import xyz.heydarrn.noteappdicoding.R
import xyz.heydarrn.noteappdicoding.domain.Note
import java.io.BufferedReader
import java.util.concurrent.Executors

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class.java) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            NoteDatabase::class.java, 
                            "note_database"
                        ).addCallback(object : RoomDatabase.Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val executor=Executors.newSingleThreadExecutor()
                                executor.execute {
                                    fillWithStartingNotes(context)
                                }
                            }

                        }).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as NoteDatabase
        }

        private fun loadJSONArray(context: Context):JSONArray? {
            val inputStream=context.resources.openRawResource(R.raw.starting_note)
            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }

        private fun fillWithStartingNotes(context: Context) {
            val dao= getDatabase(context).noteDao()

            try {
                val notes = loadJSONArray(context)
                if (notes!=null) {
                    for (i in 0 until notes.length()) {
                        val item = notes.getJSONObject(i)
                        val noteID=item.getInt("id")
                        val noteTitle= item.getString("title")
                        val noteDesc= item.getString("description")
                        val noteCreated=item.getString("date")

                        val noteEntity=Note(
                            id = noteID,
                            title = noteTitle,
                            description = noteDesc,
                            date = noteCreated
                        )

                        dao.insert(noteEntity)
                    }
                }
            }catch(e:JSONException) {
                Log.d("fill Error", "fillWithStartingNotes: $e")
            }
        }
    }
}