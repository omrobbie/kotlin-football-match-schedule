package com.omrobbie.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    fun setupLayout() {
        linearLayout {
            textView {
                text = "Hello World!"
            }
        }
    }
}
