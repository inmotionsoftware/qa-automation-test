package com.inmotionsoftware.theautomator

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.inmotionsoftware.theautomator.repo.Repo

/**
 * Created by jhunt on 2/1/18.
 */

class MainActivity : AppCompatActivity() {

    val fragmentVisibilityManager = FragmentVisibilityManager(supportFragmentManager)
    val repo = Repo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentVisibilityManager.showLoginFragment()

    }

    override fun onBackPressed() {
        if (fragmentVisibilityManager.backPressed())
            super.onBackPressed()
    }
}

fun Activity.fragmentVisibilityManager(): FragmentVisibilityManager {
    return (this as MainActivity).fragmentVisibilityManager
}

fun Activity.repo(): Repo {
    return (this as MainActivity).repo
}

