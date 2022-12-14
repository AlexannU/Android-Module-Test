package ru.alexannu.modules.features.news

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText


class DetailNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstEditText =  view.findViewById<EditText>(R.id.first_edit_text)
        val secondEditText =  view.findViewById<EditText>(R.id.second_edit_text)
        val thirdEditText =  view.findViewById<EditText>(R.id.third_edit_text)
        val button = view.findViewById<Button>(R.id.hide_keyboard_button)
        button.setOnClickListener {
            val currFocus = requireActivity().currentFocus
            if (currFocus != null) {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

    }
}