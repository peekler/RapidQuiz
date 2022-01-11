package hu.bme.aut.rapidquiz.ui.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import hu.bme.aut.rapidquiz.R
import hu.bme.aut.rapidquiz.databinding.FragmentQuizBinding
import hu.bme.aut.rapidquiz.databinding.LayoutQuizBinding
import hu.bme.aut.rapidquiz.model.QuizConfig
import hu.bme.aut.rapidquiz.model.QuizQuestion
import hu.bme.aut.rapidquiz.model.QuizResult
import hu.bme.aut.rapidquiz.ui.main.FragmentWelcomeDirections
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

class FragmentQuiz : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    val fragArgs: FragmentQuizArgs by navArgs()

    private val moneyViewModel: QuizViewModel by viewModels()

    var questionsNum = 0
    var currentQuestion = 0
    lateinit var questions: List<QuizQuestion>
    var selectedAnswer = ""
    var score = 0

    val party = Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
        position = Position.Relative(0.5, 0.3)
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()
        moneyViewModel.getQuizQuestions(fragArgs.quizConfig.category, fragArgs.quizConfig.amount)?.observe(this,
            { moneyViewState -> render(moneyViewState) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNext.setOnClickListener {
            if (selectedAnswer == questions[currentQuestion].correct_answer) {
                binding.konfettiView.start(party)
                score++
                binding.tvScore.text = "$score / $questionsNum"
            }

            currentQuestion++
            if (currentQuestion >= questionsNum) {
                MaterialDialog(requireContext()).show {
                    title(text = "Congratulations")
                    message(text = "Score: $score / $questionsNum")
                    positiveButton(text="Close") {
                        binding.root.findNavController().navigate(
                            FragmentQuizDirections.actionFragmentQuizToFragmentWelcome()
                        )
                    }
                }

                binding.btnNext.isEnabled = false

            } else {
                if (currentQuestion == questionsNum-1) {
                    binding.btnNext.text = "Finish"
                }
                revealQuestion()
                showQuestion(questions[currentQuestion])
            }
        }
    }


    private fun render(result: QuizViewState) {
        when (result) {
            is QuizViewState.InProgress -> {
                binding.tvScore.text = "Loading questions..."
            }
            is QuizViewState.QuizQueryResponseSuccess -> {
                val decodedResultData = decodeResult(result.data)
                questions = decodedResultData.results
                resetQuizValues()

                showQuestion(questions[currentQuestion])
            }
            is QuizViewState.QuizQueryResponseError -> {
                Toast.makeText(activity, result.exceptionMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetQuizValues() {
        currentQuestion = 0
        questionsNum = fragArgs.quizConfig.amount.toInt()
        selectedAnswer = ""
        score = 0
        binding.tvScore.text = "0 / $questionsNum"
        binding.btnNext.text = "Next"
        binding.btnNext.isEnabled = true
    }

    private fun decodeResult(data: QuizResult): QuizResult {
        for (q in data.results) {
            q.question = URLDecoder.decode(q.question, "UTF-8")
            q.correct_answer = URLDecoder.decode(q.correct_answer, "UTF-8")

            q.incorrect_answers = listOf(
                URLDecoder.decode(q.incorrect_answers[0], "UTF-8"),
                URLDecoder.decode(q.incorrect_answers[1], "UTF-8"),
                URLDecoder.decode(q.incorrect_answers[2], "UTF-8")
            )
        }

        return data
    }

    private fun cardClicked(cardView: View) {
        val cardsRoot = cardView.parent as LinearLayout
        for (card in cardsRoot.children) {
            if (card is CardView) {
                card.setBackgroundColor(Color.parseColor("#fff6a0"))
            }
        }
        cardView.setBackgroundColor(Color.GREEN)

        selectedAnswer = cardView.tag.toString()
    }

    private fun showQuestion(question: QuizQuestion) {
        val quizBinding: LayoutQuizBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.layout_quiz, null, false)
        quizBinding.quizQuestion = question
        quizBinding.cardAnswer1.setOnClickListener(::cardClicked)
        quizBinding.cardAnswer2.setOnClickListener(::cardClicked)
        quizBinding.cardAnswer3.setOnClickListener(::cardClicked)
        quizBinding.cardAnswer4.setOnClickListener(::cardClicked)

        binding.layoutContent.removeAllViews()
        binding.layoutContent.addView(quizBinding.root)
    }

    private fun revealQuestion() {
        val x = binding.layoutContent.getRight()
        val y = binding.layoutContent.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(
            binding.layoutContent.getWidth().toDouble(),
            binding.layoutContent.getHeight().toDouble()
        ).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.layoutContent,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        binding.layoutContent.setVisibility(View.VISIBLE)
        anim.start()
    }

}