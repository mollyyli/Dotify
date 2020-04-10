package com.mollyyli.dotify

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(1000, 10000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberOfPlayTimes.text = "${randomNumber.toString()} plays"

        albumCover.setOnLongClickListener { view: View? ->
            var color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            for (i in 0..clParent.childCount) {
                var child = clParent.getChildAt(i)
                if (child is AppCompatTextView) {
                    child.setTextColor(color)
                }
            }
            return@setOnLongClickListener true
        }
    }

    fun prevClicked(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    fun nextClicked(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    fun playClicked(view: View) {
        randomNumber++
        numberOfPlayTimes.text = "${randomNumber.toString()} plays"
    }

    fun changeUserClicked(view: View) {
        if(changeUserButton.text == "CHANGE USER") {
            etUser.visibility = View.VISIBLE
            tvUser.visibility = View.INVISIBLE
            changeUserButton.text = "APPLY"
        } else {
            if(etUser.text.isEmpty()) {
                Toast.makeText(this, "User Name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }
            tvUser.text = etUser.text
            etUser.visibility = View.INVISIBLE
            tvUser.visibility = View.VISIBLE
            changeUserButton.text = "CHANGE USER"
        }
    }

}
