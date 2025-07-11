package hu.bme.aut.android.dailydiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.dailydiary.databinding.FragmentMainpageBinding

class MainPageFragment : Fragment() {
    private lateinit var binding : FragmentMainpageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainpageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewEntry.setOnClickListener {
            findNavController().navigate(R.id.action_mainpageFragment_to_entryFragment)
        }

        binding.btnPrevEntries.setOnClickListener {
            findNavController().navigate(R.id.action_mainpageFragment_to_listFragment)
        }

    }
}