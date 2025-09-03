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

            if (guess.length != 4) {
                Toast.makeText(this, "Please enter a 4 letter word", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = checkGuess(guess)

            when (guessCount) {
                0 -> {
                    findViewById<TextView>(R.id.textView7).text = guess
                    findViewById<TextView>(R.id.textView6).text = result
                }
                1 -> {
                    findViewById<TextView>(R.id.textView9).text = guess
                    findViewById<TextView>(R.id.textView8).text = result
                }
                2 -> {
                    findViewById<TextView>(R.id.textView11).text = guess
                    findViewById<TextView>(R.id.textView10).text = result
                }
            }

            guessCount++


            if (result == "OOOO") {
                Toast.makeText(this, "You Guessed Correctly!", Toast.LENGTH_LONG).show()
                finishGame(button, resetButton)
            } else if (guessCount >= 3) {
                Toast.makeText(this, "The word is $wordToGuess", Toast.LENGTH_LONG).show()
                finishGame(button, resetButton)
            }

            editText.text.clear()
        }

        resetButton.setOnClickListener {
            resetGame(button, resetButton)
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
            result += if (guess[i] == wordToGuess[i]) {
                "O"
            } else if (guess[i] in wordToGuess) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }

    fun finishGame(button: Button, resetButton: Button) {
        button.isEnabled = false
        resetButton.visibility = View.VISIBLE
        resetButton.isEnabled = true
    }

    fun resetGame(button: Button, resetButton: Button) {
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        guessCount = 0

        findViewById<TextView>(R.id.textView7).text = ""
        findViewById<TextView>(R.id.textView6).text = ""
        findViewById<TextView>(R.id.textView9).text = ""
        findViewById<TextView>(R.id.textView8).text = ""
        findViewById<TextView>(R.id.textView11).text = ""
        findViewById<TextView>(R.id.textView10).text = ""

        button.isEnabled = true
        resetButton.visibility = View.INVISIBLE
    }

}
