package com.example.wordlegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.user_guess)
        val resetButton = findViewById<Button>(R.id.reset)

        resetButton.visibility = View.INVISIBLE

        button.setOnClickListener {
            val guess = editText.text.toString().uppercase()
            val result = checkGuess(guess)

            val bigView = findViewById<TextView>(R.id.main_guess)
            bigView.text = guess
            bigView.visibility = View.VISIBLE

            if (guessCount == 0) {
                findViewById<TextView>(R.id.textView7).text = guess
                findViewById<TextView>(R.id.textView6).text = result
            }
            else if (guessCount == 1) {
                findViewById<TextView>(R.id.textView9).text = guess
                findViewById<TextView>(R.id.textView8).text = result
            }
            else if (guessCount == 2) {
                findViewById<TextView>(R.id.textView11).text = guess
                findViewById<TextView>(R.id.textView10).text = result
            }

            guessCount+=1


            if (result == "OOOO") {
                Toast.makeText(this, "You Guessed Correctly!", Toast.LENGTH_LONG).show()
                button.isEnabled = false
            } else if (guessCount >= 3) {
                Toast.makeText(this, "The word is $wordToGuess", Toast.LENGTH_LONG).show()
                button.isEnabled = false
            }

            editText.text.clear()
        }

        resetButton.setOnClickListener {
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            guessCount = 0

            findViewById<TextView>(R.id.textView7).text = ""
            findViewById<TextView>(R.id.textView6).text = ""
            findViewById<TextView>(R.id.textView9).text = ""
            findViewById<TextView>(R.id.textView8).text = ""
            findViewById<TextView>(R.id.textView11).text = ""
            findViewById<TextView>(R.id.textView10).text = ""
            findViewById<TextView>(R.id.main_guess).visibility = View.INVISIBLE

            button.isEnabled = true
            resetButton.isEnabled = false
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""

        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

}