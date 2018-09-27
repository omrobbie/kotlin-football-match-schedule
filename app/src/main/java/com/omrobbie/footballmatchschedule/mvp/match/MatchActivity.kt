package com.omrobbie.footballmatchschedule.mvp.match

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.adapter.MatchAdapter
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.model.LeaguesItem
import com.omrobbie.footballmatchschedule.mvp.detail.DetailActivity
import com.omrobbie.footballmatchschedule.mvp.detail.INTENT_DETAIL
import com.omrobbie.footballmatchschedule.network.ApiRepository
import com.omrobbie.footballmatchschedule.utils.gone
import com.omrobbie.footballmatchschedule.utils.invisible
import com.omrobbie.footballmatchschedule.utils.progressBar
import com.omrobbie.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchActivity : AppCompatActivity(), MatchView {

    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter

    private lateinit var spinnerLayout: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyDataView: LinearLayout

    private lateinit var league: LeaguesItem

    private var events: MutableList<EventsItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
        setupEnv()
    }

    override fun onResume() {
        super.onResume()

        if (presenter.menu == 3) presenter.getFavoritesAll(ctx)
    }

    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
        emptyDataView.invisible()

        if (presenter.menu == 3) spinnerLayout.gone()
        else spinnerLayout.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        recyclerView.visible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        recyclerView.invisible()
        emptyDataView.visible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                when (presenter.menu) {
                    1 -> presenter.getEventsPrev(league.idLeague!!)
                    2 -> presenter.getEventsNext(league.idLeague!!)
                }
            }
        }
    }

    override fun showEventList(data: List<EventsItem>) {
        showEventListData(data)
    }

    private fun setupLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            spinnerLayout = linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
                    id = R.id.spinner
                    padding = dip(16)
                    minimumHeight = dip(80)
                }
            }

            relativeLayout {
                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        setImageResource(R.drawable.ic_no_data)
                    }

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = "No Data Provided"
                    }
                }.lparams {
                    centerInParent()
                }

                recyclerView = recyclerView {
                    id = R.id.recycler_view
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(R.id.bottom_navigation_view)
                }

                progressBar(R.id.progress_bar).lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = R.id.bottom_navigation_view
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add(0, R.id.bnv_match_prev, 0, "Prev. Match")
                                .setIcon(R.drawable.ic_trophy)
                                .setOnMenuItemClickListener {
                                    presenter.getEventsPrev(league.idLeague!!)
                                    false
                                }

                        add(0, R.id.bnv_match_next, 0, "Next Match")
                                .setIcon(R.drawable.ic_event)
                                .setOnMenuItemClickListener {
                                    presenter.getEventsNext(league.idLeague!!)
                                    false
                                }

                        add(0, R.id.bnv_favorites, 0, "Favorites")
                                .setIcon(R.drawable.ic_favorites)
                                .setOnMenuItemClickListener {
                                    presenter.getFavoritesAll(ctx)
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }
        }
    }

    private fun setupEnv() {
        progressBar = find(R.id.progress_bar)

        presenter = MatchPresenter(this, ApiRepository(), Gson())
        adapter = MatchAdapter(events) {
            startActivity<DetailActivity>(INTENT_DETAIL to it)
        }

        presenter.getLeagueAll()
        recyclerView.adapter = adapter
    }

    private fun showEventListData(data: List<EventsItem>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
    }
}
