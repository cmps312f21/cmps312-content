package qa.edu.cmps312.guessinggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_success.*
import qa.edu.cmps312.guessinggame.R

class Success : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val randomNumber = intent.getStringExtra("number")

        textCorrectNumberMsg.text = "Correct number is: $randomNumber"

        buttonPlayAgain.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
