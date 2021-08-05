package com.example.cinematv_mvvm.ui.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.ui.film.FilmFragment

class TabLayoutAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm) {

    companion object {
        val TAB_TITLES =
            intArrayOf(R.string.Action, R.string.Animation, R.string.Comedy, R.string.Adventure, R.string.Drama)
    }

    override fun getItem(position: Int): Fragment {
        return FilmFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }


}