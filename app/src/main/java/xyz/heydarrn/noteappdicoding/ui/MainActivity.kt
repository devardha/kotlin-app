package xyz.heydarrn.noteappdicoding.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.noteappdicoding.R
import xyz.heydarrn.noteappdicoding.databinding.ActivityMainBinding
import xyz.heydarrn.noteappdicoding.utils.NoteListAdapter
import xyz.heydarrn.noteappdicoding.viewmodels.NoteViewModel
import xyz.heydarrn.noteappdicoding.viewmodels.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain:ActivityMainBinding
    private val adapterNote by lazy { NoteListAdapter() }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModelFactory.getInstance(application))[NoteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        monitorViewModel()
    }

    private fun monitorViewModel() {
        viewModel.getAllNotes().observe(this) {
            if (it!=null) {
                adapterNote.submitList(it)
            }
        }
        bindingMain.recyclerViewNote.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterNote
        }
    }
}