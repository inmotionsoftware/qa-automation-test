package com.inmotionsoftware.theautomator.fragment

import android.support.v4.app.Fragment

/**
 * Created by jhunt on 2/1/18.
 */

abstract class BaseFragment : Fragment() {

    private var cleanedUp = false

    abstract fun cleanUp()

    override fun onPause() {
        super.onPause()
        if (isRemoving || activity?.isChangingConfigurations == true) {
            cleanUpIfNotAlreadyDone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUpIfNotAlreadyDone()
    }

    private fun cleanUpIfNotAlreadyDone() {
        if (cleanedUp) return

        cleanUp()
        cleanedUp = true
    }
}