package com.inmotionsoftware.theautomator.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.inmotionsoftware.models.Cities
import com.inmotionsoftware.models.City
import kotlinx.android.synthetic.main.fragment_city_list.*
import kotlinx.android.synthetic.main.view_holder_city.view.*
import android.view.MenuInflater
import com.inmotionsoftware.theautomator.*


/**
 * Created by jhunt on 2/1/18.
 */
class CityListFragment : BaseFragment() {
    private var isSorted = false
    private var filterBy = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.cities)
        recycler.layoutManager = LinearLayoutManager(context)

        activity?.repo()?.cities?.let {
            recycler.adapter = CityAdapter(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.filter, menu)
        activity?.menuInflater?.inflate(R.menu.sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.filter_texas -> filterBy = "TX"
            R.id.filter_new_york -> filterBy = "NY"
            R.id.filter_washington -> filterBy = "WA"
            R.id.filter_none -> filterBy = ""

            R.id.sort -> isSorted = !isSorted

        }

        val cities = com.inmotionsoftware.nobugs.filterAndSortCities(activity?.repo()?.cities?.cities!!, filterBy, isSorted)
        recycler.adapter = CityAdapter(cities)

        return super.onOptionsItemSelected(item)
    }

    override fun cleanUp() {

    }
}

class CityAdapter(private val cities: Cities) : RecyclerView.Adapter<CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_city, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int = cities.cities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindData(cities.cities[position], position)
    }

}

class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindData(city: City, position: Int) {
        itemView.cityNameTextView.text = city.name
        itemView.cityNameTextView.contentDescription = position.toString()
        itemView.setOnClickListener {
            (itemView.context as Activity).repo().selectedCityDetailUrl = city.details
            (itemView.context as Activity).fragmentVisibilityManager().showLoadingFragment()
        }
    }
}

