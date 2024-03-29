package com.artemissoftware.blindcoffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val coffeeRepo = CoffeeRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showCount()



        coffeeLimitValue.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = showCount()
        })

        addCoffee.setOnClickListener {
            coffeeRepo.increment()
            showCount()

            amountConsumed.announceForAccessibility(getString(R.string.count_updated, consumedString()))
        }
    }

    private fun showCount() {
        amountConsumed.text = consumedString()
        coffeeProgress.max = limit().toInt()
        coffeeProgress.progress = coffeeRepo.count
    }

    private fun consumedString() =
        getString(R.string.consumed_format, coffeeRepo.count, limit())

    private fun limit(): String {
        val limitString = coffeeLimitValue.text.toString()
        return if (limitString.isNotEmpty()) limitString else "0"
    }
}

