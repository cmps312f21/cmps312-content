package qa.edu.cmps312.colorchanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeColorBtn.setOnClickListener {
            greetingTv.setTextColor(getRandomColor())
            greetingTv.text = "Salam"

        }
    }
}