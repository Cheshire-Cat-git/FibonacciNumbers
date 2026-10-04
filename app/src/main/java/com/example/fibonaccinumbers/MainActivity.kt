package com.example.fibonaccinumbers

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fibonaccinumbers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.button.setOnClickListener {
            viewModel.onStateButtonClick(binding.editText.text.toString())
        }

        viewModel.isCalculating.observe(this) { isCalculating ->
            binding.editText.isEnabled = !isCalculating
            binding.button.text = if (isCalculating) getString(R.string.close_txt)
            else getString(R.string.start_txt)
        }

        viewModel.counter.observe(this) { text ->
            binding.textView.text = text
        }
    }
}