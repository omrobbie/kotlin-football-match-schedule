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

class MatchAdapter(private val items: List<EventsItem>,
                   private val clickListener: (EventsItem) -> Unit) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val matchDate: TextView = view.findViewById(ID_DATE)
        private val matchHomeTeam: TextView = view.findViewById(ID_HOME_TEAM)
        private val matchHomeScore: TextView = view.findViewById(ID_HOME_SCORE)
        private val matchAwayTeam: TextView = view.findViewById(ID_AWAY_TEAM)
        private val matchAwayScore: TextView = view.findViewById(ID_AWAY_SCORE)

        fun bind(item: EventsItem, clickListener: (EventsItem) -> Unit) {
            matchDate.text = DateTime.getLongDate(item.dateEvent!!)
            matchHomeTeam.text = item.strHomeTeam
            matchHomeScore.text = item.intHomeScore
            matchAwayTeam.text = item.strAwayTeam
            matchAwayScore.text = item.intAwayScore

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    companion object {
        const val ID_DATE = 1
        const val ID_HOME_TEAM = 2
        const val ID_HOME_SCORE = 3
        const val ID_AWAY_TEAM = 4
        const val ID_AWAY_SCORE = 5
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
