package com.omrobbie.footballmatchschedule.mvp.match

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.*
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.adapter.MatchAdapter
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.model.LeagueResponse
import com.omrobbie.footballmatchschedule.model.LeaguesItem
import com.omrobbie.footballmatchschedule.mvp.detail.DetailActivity
import com.omrobbie.footballmatchschedule.mvp.detail.INTENT_DETAIL
import com.omrobbie.footballmatchschedule.utils.invisible
import com.omrobbie.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchActivity : AppCompatActivity(), MatchView {

    lateinit var presenter: MatchPresenter
    lateinit var adapter: MatchAdapter

    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var emptyDataView: LinearLayout

    lateinit var league: LeaguesItem

    var events: MutableList<EventsItem> = mutableListOf()

    private val ID_BNV = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
        setupEnv()
    }

    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
        emptyDataView.invisible()
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

                when (presenter.match) {
                    1 -> presenter.getEventsPrev(league.idLeague!!)
                    2 -> presenter.getEventsNext(league.idLeague!!)
                }
            }
        }
    }

    override fun showEventListPrev(data: List<EventsItem>) {
        showEventListData(data)
    }

    override fun showEventListNext(data: List<EventsItem>) {
        showEventListData(data)
    }

    fun itemClicked(item: EventsItem) {
        startActivity<DetailActivity>(INTENT_DETAIL to item)
    }

    fun setupLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            linearLayout {
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

                progressBar = progressBar {
                    indeterminateDrawable.setColorFilter(
                            ContextCompat.getColor(ctx, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                    )
                }.lparams {
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
                                    toast("Favorites")
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
        presenter = MatchPresenter(this)
        adapter = MatchAdapter(events, { item: EventsItem -> itemClicked(item) })

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
