package com.omrobbie.footballmatchschedule.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.omrobbie.footballmatchschedule.R
import com.omrobbie.footballmatchschedule.model.EventsItem
import com.omrobbie.footballmatchschedule.utils.DateTime
import org.jetbrains.anko.*

class MatchAdapter(val items: List<EventsItem>, val clickListener: (EventsItem) -> Unit) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val match_date: TextView = view.findViewById(ID_DATE)
        val match_home_team: TextView = view.findViewById(ID_HOME_TEAM)
        val match_home_score: TextView = view.findViewById(ID_HOME_SCORE)
        val match_away_team: TextView = view.findViewById(ID_AWAY_TEAM)
        val match_away_score: TextView = view.findViewById(ID_AWAY_SCORE)

        fun bind(item: EventsItem, clickListener: (EventsItem) -> Unit) {
            match_date.text = DateTime.getLongDate(item.dateEvent!!)
            match_home_team.text = item.strHomeTeam
            match_home_score.text = item.intHomeScore
            match_away_team.text = item.strAwayTeam
            match_away_score.text = item.intAwayScore

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    companion object {
        val ID_DATE = 1
        val ID_HOME_TEAM = 2
        val ID_HOME_SCORE = 3
        val ID_AWAY_TEAM = 4
        val ID_AWAY_SCORE = 5
    }

    inner class ItemUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = ID_DATE
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = ID_HOME_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "home"
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = ID_HOME_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                text = "vs"
                            }

                            textView {
                                id = ID_AWAY_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }
                        }

                        textView {
                            id = ID_AWAY_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "away"
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}
