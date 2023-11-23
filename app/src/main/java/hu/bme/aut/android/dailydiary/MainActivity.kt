package hu.bme.aut.android.dailydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.dailydiary.adapter.EntryAdapter
import hu.bme.aut.android.dailydiary.data.Entry
import hu.bme.aut.android.dailydiary.data.EntryDatabase
import hu.bme.aut.android.dailydiary.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), EntryAdapter.EntryClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: EntryDatabase
    companion object {
        lateinit var adapter: EntryAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        adapter = EntryAdapter(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        database = EntryDatabase.getDatabase(applicationContext)

        setContentView(binding.root)
    }
    override fun onEntryChanged(entry: Entry) {
        thread {
            database.entryDao().update(entry)
            Log.d("MainActivity", "Entry update was successful")
        }
    }

    override fun onEntryCreated(newEntry: Entry) {
        thread {
            val insertId = database.entryDao().insert(newEntry)
            newEntry.id = insertId
            runOnUiThread {
                adapter.addEntry(newEntry)
            }
        }
    }

    override fun onEntryDeleted(entry: Entry) {
        thread {
            database.entryDao().deleteEntry(entry)
            runOnUiThread {
                adapter.deleteEntry(entry)
            }
        }
    }
    override fun goToOldEntry() {
        val listFragment = ListFragment.instance
        listFragment.findNavController().navigate(R.id.action_listFragment_to_oldEntryFragment)
    }
}