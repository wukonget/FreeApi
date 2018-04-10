package com.peng.freeapi

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.peng.freeapi.fragments.ImageListFragment
import com.peng.freeapi.fragments.NameListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.title = getString(R.string.app_name)
        collapsingToolbarLayout.setExpandedTitleColor(resources.getColor(R.color.colorPrimary))

        setViewPagerAndTab()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "提示", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun setViewPagerAndTab() {

        val nameListFragment = NameListFragment()
        val imageListFragment = ImageListFragment()

        val titles = ArrayList<String>()
        val pagerList = ArrayList<Fragment>()

        titles.add("个性网名")
        titles.add("美图欣赏")
        pagerList.add(nameListFragment)
        pagerList.add(imageListFragment)

        viewpager.adapter = ViewPagerAdapter(titles,pagerList,supportFragmentManager)

        tabLayout.setupWithViewPager(viewpager,true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class ViewPagerAdapter(titles:ArrayList<String> = ArrayList<String>(),pagerList:ArrayList<Fragment> = ArrayList<Fragment>(),fm:FragmentManager) : FragmentPagerAdapter (fm){
        private var pagerList = pagerList
        private var titles = titles

        override fun getItem(position: Int): Fragment {
            return pagerList[position]
        }

        override fun getCount(): Int {
            return pagerList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
