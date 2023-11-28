package hu.bme.aut.android.dailydiary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.dailydiary.adapter.EntryAdapter
import hu.bme.aut.android.dailydiary.data.Entry
import hu.bme.aut.android.dailydiary.data.EntryDatabase
import hu.bme.aut.android.dailydiary.databinding.FragmentNewentryBinding

class NewEntryFragment : Fragment() {
    private lateinit var binding: FragmentNewentryBinding
    private lateinit var listener: EntryAdapter.EntryClickListener
    private lateinit var database: EntryDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewentryBinding.inflate(inflater, container, false)
        database = EntryDatabase.getDatabase(requireContext())
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
            findNavController().navigate(R.id.action_newEntryFragment_to_listFragment)
        }
    }

    private fun getEntry() = Entry(
        title = binding.title.text.toString(),
        content = binding.content.text.toString(),
        mood = binding.radioGroup.iterator().asSequence().let {
            Entry.Mood.getByOrdinal(it.takeWhile { v -> v.id != binding.radioGroup.checkedRadioButtonId }.count())
        } ?: Entry.Mood.Neutral)

}