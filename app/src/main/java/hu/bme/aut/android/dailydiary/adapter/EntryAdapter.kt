package hu.bme.aut.android.dailydiary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.dailydiary.data.Entry
import hu.bme.aut.android.dailydiary.databinding.EntryListItemBinding
import kotlin.properties.Delegates

class EntryAdapter(private val listener: EntryClickListener) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {
    val entries = mutableListOf<Entry>()
    var currentPosition by Delegates.notNull<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EntryViewHolder(
        EntryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]

        holder.binding.tvName.text = entry.title

        holder.binding.tvDate.text = entry.date

        holder.binding.ibEdit.setOnClickListener {
            currentPosition = position
            listener.goToOldEntry()
        }

        holder.binding.ibRemove.setOnClickListener {
            listener.onEntryDeleted(entry)
        }

    }

    override fun getItemCount(): Int = entries.size

    interface EntryClickListener {
        fun onEntryCreated(newEntry: Entry)
        fun onEntryChanged(entry: Entry)
        fun onEntryDeleted(entry: Entry)
        fun goToOldEntry()
    }

    fun addEntry(entry: Entry) {
        entries.add(entry)
        notifyItemInserted(entries.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(allEntries: List<Entry>) {
        entries.clear()
        entries.addAll(allEntries)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteEntry(item: Entry) {
        entries.remove(item)
        notifyDataSetChanged()
    }

    inner class EntryViewHolder(val binding: EntryListItemBinding) : RecyclerView.ViewHolder(binding.root)
}