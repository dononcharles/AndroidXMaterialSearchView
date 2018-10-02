package com.dononcharles.materialsearchview

import android.animation.LayoutTransition
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * @author Komi V. Donon <dononcharles@gmail.com>
 * This class activity described how to set material design Toolbar SearchView
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

   lateinit var searchView: SearchView
    @BindView(R.id.contentText) lateinit var contentText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * To change Toolbar color when collapse
     */
    fun setActionBarColor(parsedColor: Int) {
        val mActionBar = supportActionBar
        mActionBar!!.setBackgroundDrawable(ColorDrawable(parsedColor))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menuItemSearch = menu.findItem(R.id.action_search)
        searchView = menuItemSearch.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        /**
         * Personalize Placeholder text and EditText color
         */
        val txtSearch = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = getString(R.string.label_search)
        txtSearch.setHintTextColor(Color.GRAY)
        txtSearch.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))

        // change search close button image
        val closeButton = searchView.findViewById<ImageView>(R.id.search_close_btn)
        closeButton.setImageResource(R.drawable.ic_close)

        // Make animation transition
        val searchBar = searchView.findViewById(R.id.search_bar) as LinearLayout
        searchBar.layoutTransition = LayoutTransition()

        // do something when collapsed and expanded
        menuItemSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                //do something when collapsed
                if (supportActionBar != null) {
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                }
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                // do something when expanded
                invalidateOptionsMenu()
                if (supportActionBar != null) {
                    setActionBarColor(Color.parseColor("#008577"))
                }
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this.callSearch(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
               this.callSearch(newText)
                return true
            }

            private fun callSearch(query: String) {
                //Do searching
                contentText.text = query
                Log.i("query", "" + query)

            }
        })

        searchView.setOnCloseListener { false }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
