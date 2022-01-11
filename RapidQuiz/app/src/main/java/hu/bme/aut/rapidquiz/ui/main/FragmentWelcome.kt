package hu.bme.aut.rapidquiz.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import hu.bme.aut.rapidquiz.R
import hu.bme.aut.rapidquiz.databinding.FragmentWelcomeBinding
import hu.bme.aut.rapidquiz.model.QuizConfig

class FragmentWelcome : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnGo.setOnClickListener {

            val category = when (binding.radioCategory.checkedRadioButtonId) {
                R.id.rbtnHistory -> "23"
                R.id.rbtnSport ->"21"
                else -> "18"
            }

            binding.root.findNavController().navigate(
                FragmentWelcomeDirections.actionFragmentWelcomeToFragmentQuiz(
                    QuizConfig(category, binding.etQuestions.text.toString())
                )
            )

        }
    }

}