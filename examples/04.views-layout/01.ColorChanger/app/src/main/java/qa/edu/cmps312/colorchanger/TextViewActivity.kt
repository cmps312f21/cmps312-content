package qa.edu.cmps312.colorchanger

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_textview.*


class TextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textview)

        storyTv.movementMethod = ScrollingMovementMethod()
        supportActionBar?.title = "Qatar History"
    }
}