package xyz.heydarrn.noteappdicoding.utils

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.noteappdicoding.domain.Note

class NoteDiffCallback:DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}