package ru.skillbranch.devintensive.ui.archive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_group.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.ui.custom.ChatDividerItemDecoration
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel

class ArchiveActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewModel: ArchiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        initToolbar()
        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Введите имя пользователя"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.handleSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.handleSearchQuery(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home){
            finish()
            overridePendingTransition(R.anim.idle, R.anim.bottom_down)
            true
        }
        else{
            super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        chatAdapter = ChatAdapter {
            Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG).show()
        }

        val divider = ChatDividerItemDecoration(this)
        val touchCallback = ChatItemTouchHelperCallback(chatAdapter){
            val chatItemId = it.id
            viewModel.restoreFromArchive(chatItemId)
            //Snackbar.make(rv_archive_list, "Восстановить чат с ${it.title} из архива?", Snackbar.LENGTH_LONG)
            //    .setAction("ОТМЕНА", View.OnClickListener { viewModel.addToArchive(chatItemId) })
            //    .show()
            val snackbar = Snackbar.make(rv_archive_list, "Восстановить чат с ${it.title} из архива?", Snackbar.LENGTH_LONG)
                .setAction("ОТМЕНА", View.OnClickListener { viewModel.addToArchive(chatItemId) })

            val typedValue = TypedValue()
            rv_archive_list.context.theme.resolveAttribute(R.attr.colorItemBackground, typedValue, true)

            snackbar.view.findViewById<TextView>(R.id.snackbar_text).setTextColor(typedValue.data)
            snackbar.show()
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv_archive_list)

        with(rv_archive_list){
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.getChatData().observe(this, Observer { chatAdapter.updateDate(it) })
    }
}
