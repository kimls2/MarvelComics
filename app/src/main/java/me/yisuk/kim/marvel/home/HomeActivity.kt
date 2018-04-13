package me.yisuk.kim.marvel.home

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.SharedElementHelper
import me.yisuk.kim.marvel.comicdetails.ComicDetailsActivity
import me.yisuk.kim.marvel.data.entities.MarvelComic
import me.yisuk.kim.marvel.extentions.observeK
import me.yisuk.kim.marvel.extentions.replaceFragmentInActivity
import me.yisuk.kim.marvel.home.comic.ComicFragment
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navigatorViewModel: HomeNavigatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigatorViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeNavigatorViewModel::class.java)

        navigatorViewModel.showComicDetailCall.observeK(this, this::goToDetail)

        replaceFragmentInActivity(ComicFragment(), content.id)
    }

    @SuppressLint("RestrictedApi")
    private fun goToDetail(pair: Pair<MarvelComic, SharedElementHelper?>?) {
        pair?.let {
            startActivityForResult(
                    ComicDetailsActivity.createIntent(this, it.first.id!!),
                    0,
                    it.second?.applyToIntent(this))
        }

    }
}
