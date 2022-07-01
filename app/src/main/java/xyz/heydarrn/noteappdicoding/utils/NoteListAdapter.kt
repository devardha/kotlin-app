package xyz.heydarrn.noteappdicoding.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.heydarrn.noteappdicoding.databinding.PersonalNoteBinding
import xyz.heydarrn.noteappdicoding.domain.Note

class NoteListAdapter:ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback()) {
    var whenNoteSelected:OnPersonalNoteClicked?=null

    fun setThisNoteToUpdate(thisNote:OnPersonalNoteClicked) {
        this.whenNoteSelected=thisNote
    }
    class NoteViewHolder(private val cardNote:PersonalNoteBinding):RecyclerView.ViewHolder(cardNote.root) {
        fun bindingNote(note: Note) {
            cardNote.apply {
                textDatetimeCreated.text=note.date
                textNoteTitle.text=note.title
                textNoteDescription.text=note.description

                root.setOnClickListener {

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view=PersonalNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindingNote(getItem(position))
    }

    interface OnPersonalNoteClicked {
        fun selectThisNote(sendNote: Note)
    }
}