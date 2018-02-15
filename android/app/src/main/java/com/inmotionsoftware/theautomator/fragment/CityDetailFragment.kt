package com.inmotionsoftware.theautomator.fragment

import android.os.Bundle
import android.view.*
import com.inmotionsoftware.theautomator.*
import kotlinx.android.synthetic.main.fragment_city_detail.*

/**
 * Created by jhunt on 2/2/18.
 */
class CityDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.title = getString(R.string.details)

        activity?.repo()?.selectedCity?.let {

            val stateString = com.inmotionsoftware.nobugs.formatState(it)

            cityStateTextView.text = "${it.name}, $stateString"
            urlTextView.text = it.url
            descriptionTextView.text = it.description
            populationTextView.text = getString(R.string.population).format(populationFormatter(it.population))

        }
    }

    private fun populationFormatter(population: Int): String {
        return com.inmotionsoftware.nobugs.formatPopulation(population)
    }

    override fun cleanUp() {

    }
}