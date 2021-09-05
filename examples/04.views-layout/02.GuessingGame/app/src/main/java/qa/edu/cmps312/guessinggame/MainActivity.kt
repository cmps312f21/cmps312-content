package qa.edu.cmps312.guessinggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import qa.edu.cmps312.guessinggame.R
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomNumber : Int = Random.nextInt(1, 20)
        var triesLeft = 3;
        guessesLeft.text = "You have $triesLeft Guesses Left"

        buttonGuess.setOnClickListener{

            if (userInput.text.toString().toIntOrNull() != null) {
                    triesLeft--
                    val userInput = userInput.text.toString().toInt()

                    if (userInput == randomNumber ){
                        val intent = Intent(this, Success::class.java)
                        intent.putExtra("number", randomNumber.toString())
                        startActivity(intent)
                    } else if (triesLeft>0){
                        Toast.makeText(this, "Please try again. You have $triesLeft Guesses Left", Toast.LENGTH_SHORT).show()
                        guessesLeft.text = "You have $triesLeft Guesses Left"
                    }else{
                        val intent = Intent(this, GameOver::class.java)
                        intent.putExtra("number", randomNumber.toString())
                        startActivity(intent)
                    }
            }else{
                Toast.makeText(this, "Incorrect Input. Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
