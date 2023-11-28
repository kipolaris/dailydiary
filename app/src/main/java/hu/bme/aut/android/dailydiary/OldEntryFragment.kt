package hu.bme.aut.android.dailydiary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.iterator
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.dailydiary.MainActivity.Companion.adapter
import hu.bme.aut.android.dailydiary.adapter.EntryAdapter
import hu.bme.aut.android.dailydiary.data.Entry
import hu.bme.aut.android.dailydiary.data.EntryDatabase
import hu.bme.aut.android.dailydiary.databinding.FragmentOldentryBinding

class OldEntryFragment : Fragment() {
    private lateinit var binding: FragmentOldentryBinding
    private lateinit var listener: EntryAdapter.EntryClickListener
    private lateinit var database: EntryDatabase
    private val oldEntry = adapter.entries[adapter.currentPosition]

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOldentryBinding.inflate(inflater, container, false)
        database = EntryDatabase.getDatabase(requireContext())
        val eTitle = binding.title
        eTitle.setText(oldEntry.title)
        val eContent = binding.content
        eContent.setText(oldEntry.content)
        binding.radioGroup.iterator().asSequence().toList().let {
            val radioButton = it.elementAt(oldEntry.mood?.ordinal ?: 2) as RadioButton
            radioButton.isChecked = true
        }


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EntryAdapter.EntryClickListener
            ?: throw RuntimeException("Fragment must implement the EntryClickListener interface!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            listener.onEntryCreated(getEntry())
            listener.onEntryDeleted(oldEntry)
            findNavController().navigate(R.id.action_oldEntryFragment_to_listFragment)
        }
    }

    private fun getEntry() = Entry(
        title = binding.title.text.toString(),
        content = binding.content.text.toString(),
        mood = binding.radioGroup.iterator().asSequence().let {
            Entry.Mood.getByOrdinal(it.takeWhile { v -> v.id != binding.radioGroup.checkedRadioButtonId }.count())
        } ?: Entry.Mood.Neutral)

}