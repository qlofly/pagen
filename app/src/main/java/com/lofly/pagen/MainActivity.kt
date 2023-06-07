package com.lofly.pagen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.lofly.pagen.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seekBar = findViewById<SeekBar>(binding.lengthPasswd.id)
        seekBar.max = 32

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.lenNumber.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Начало изменения
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.genButton.setOnClickListener{
            val lengthBarNumber = binding.lenNumber.text.toString().toInt()
            val allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val numberChars = "0123456789"
            val specialChars = "!@#$%^&*()_-+=<>?/{}~|"

            var charsToUse = allChars
            if (binding.numBox.isChecked) {charsToUse += numberChars}
            if (binding.specBox.isChecked) {charsToUse += specialChars}
            if (binding.numBox.isChecked && binding.specBox.isChecked){charsToUse += numberChars + specialChars}
            val finalPasswd = generatePassword(lengthBarNumber, charsToUse)
            binding.genPasswordText.text = finalPasswd
        }
        binding.genPasswordText.setOnClickListener {
            copyTextToClipboard(binding.genPasswordText)
        }

    }
    fun generatePassword(length: Int, charsToUse: String): String {
        val random = Random()

        return (1..length)
            .map { charsToUse[random.nextInt(charsToUse.length)] }
            .joinToString("")
    }
    fun copyTextToClipboard(textView: TextView) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Text", textView.text)
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}