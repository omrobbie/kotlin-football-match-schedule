package com.omrobbie.footballmatchschedule.mvp.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.omrobbie.footballmatchschedule.model.EventsItem
import org.jetbrains.anko.centerInParent
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.textView

const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout(intent.getParcelableExtra<EventsItem>(INTENT_DETAIL))
        setupEnv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun setupLayout(item: EventsItem) {
        relativeLayout {
            textView {
                text = item.strEvent
            }.lparams {
                centerInParent()
            }
        }
    }

    fun setupEnv() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"
    }
}
