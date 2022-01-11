package hu.bme.aut.rapidquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import hu.bme.aut.rapidquiz.databinding.ActivityMainBinding
import hu.bme.aut.rapidquiz.databinding.LayoutQuizBinding
import hu.bme.aut.rapidquiz.model.QuizQuestion
import hu.bme.aut.rapidquiz.model.QuizResult
import hu.bme.aut.rapidquiz.ui.quiz.QuizViewModel
import hu.bme.aut.rapidquiz.ui.quiz.QuizViewState
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}