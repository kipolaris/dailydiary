package hu.bme.aut.android.dailydiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.dailydiary.data.EntryDatabase
import hu.bme.aut.android.dailydiary.databinding.FragmentListBinding
import kotlin.concurrent.thread

class ListFragment : Fragment() {
    private lateinit var binding : FragmentListBinding
    private lateinit var database: EntryDatabase
    private var sortAsc=false

    companion object{
        lateinit var instance : ListFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        database = EntryDatabase.getDatabase(requireContext())
        initRecyclerView()
        instance = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_mainpageFragment)
        }

        binding.btnSort.setOnClickListener {
            if(sortAsc) {
                binding.btnSort.text = getString(R.string.sort_desc)
                sortAsc=false
                sortEntries(false)
            }
            else {
                binding.btnSort.text = getString(R.string.sort_asc)
                sortAsc=true
                sortEntries(true)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvMain.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding.rvMain.adapter = MainActivity.adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.entryDao().getAll()
            requireActivity().runOnUiThread {
                MainActivity.adapter.update(items)
            }
        }
    }

    private fun sortEntries(sortasc: Boolean) {
        thread {
            val entries = database.entryDao().getAll()
            if(!sortasc) {
                requireActivity().runOnUiThread {
                    MainActivity.adapter.update(entries.sortedByDescending { it.date })
                }
            }
            if(sortasc) {
                requireActivity().runOnUiThread {
                    MainActivity.adapter.update(entries.sortedBy { it.date })
                }
            }

        }

    }
}