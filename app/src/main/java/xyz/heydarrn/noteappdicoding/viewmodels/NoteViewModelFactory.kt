package xyz.heydarrn.noteappdicoding.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: NoteViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): NoteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(NoteViewModelFactory::class.java) {
                    INSTANCE = NoteViewModelFactory(application)
                }
            }
            return INSTANCE as NoteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NoteViewModel::class.java) -> {
                NoteViewModel(mApplication) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel is not known. Troubled viewmodel : ${modelClass.name}")
            }
        }
    }
}