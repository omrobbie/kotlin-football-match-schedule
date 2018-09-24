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

    val ID_PROGRESSBAR = 1
    val ID_BNV = 2

    lateinit var presenter: MatchPresenter
    lateinit var adapter: MatchAdapter

    lateinit var spinnerLayout: LinearLayout
    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var emptyDataView: LinearLayout

    lateinit var league: LeaguesItem

    var events: MutableList<EventsItem> = mutableListOf()

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

    fun setupLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            spinnerLayout = linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
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
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(ID_BNV)
                }

                progressBar(ID_PROGRESSBAR).lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = ID_BNV
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add("Prev. Match")
                                .setIcon(R.drawable.ic_trophy)
                                .setOnMenuItemClickListener {
                                    presenter.getEventsPrev(league.idLeague!!)
                                    false
                                }

                        add("Next Match")
                                .setIcon(R.drawable.ic_event)
                                .setOnMenuItemClickListener {
                                    presenter.getEventsNext(league.idLeague!!)
                                    false
                                }

                        add("Favorites")
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

    fun setupEnv() {
        progressBar = find(ID_PROGRESSBAR)

        presenter = MatchPresenter(this, ApiRepository(), Gson())
        adapter = MatchAdapter(events, {
            startActivity<DetailActivity>(INTENT_DETAIL to it)
        })

        presenter.getLeagueAll()
        recyclerView.adapter = adapter
    }

    fun showEventListData(data: List<EventsItem>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
    }
}
