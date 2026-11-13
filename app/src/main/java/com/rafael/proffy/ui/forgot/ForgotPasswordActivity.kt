package com.rafael.proffy.ui.forgot

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.rafael.proffy.R
import com.rafael.proffy.databinding.ActivityForgotPasswordBinding
import com.rafael.proffy.ui.finallyComponent.FinallyActivity
import com.rafael.proffy.utils.ValidationUtils

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)

            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                binding.main.post {
                    binding.main.fullScroll(View.FOCUS_DOWN)
                }
            }
            insets
        }

        val enabledButtonColor = ContextCompat.getColor(this, R.color.green)
        val disabledButtonColor = ContextCompat.getColor(this, R.color.shape_disable)

        setButtonState(false, enabledButtonColor, disabledButtonColor)

        binding.buttonGoBack.setOnClickListener {
            goBack()
        }

        binding.textInputEditEmail.addTextChangedListener {
            val email = binding.textInputEditEmail.text?.toString()?.trim() ?: ""
            val isValid = ValidationUtils.isValidEmail(email)

            if (email.isEmpty()) {
                clearEmailError()
            } else if (!isValid) {
                showEmailError(getString(R.string.error_invalid_email))
            } else {
                clearEmailError()
            }

            setButtonState(isValid, enabledButtonColor, disabledButtonColor)
        }

        binding.textInputEditEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.textInputEditEmail.text?.toString()?.trim() ?: ""
                if (email.isNotEmpty() && !ValidationUtils.isValidEmail(email)) {
                    showEmailError(getString(R.string.error_invalid_email))
                }
            }
        }

        binding.buttonSend.setOnClickListener {
            handleSendResetEmail()
        }
    }

    private fun showEmailError(message: String) {
        binding.textInputLayoutEmail.isErrorEnabled = true
        binding.textInputLayoutEmail.error = message
        binding.textInputLayoutEmail.boxStrokeWidth = 2
    }

    private fun clearEmailError() {
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutEmail.isErrorEnabled = false
        binding.textInputLayoutEmail.boxStrokeWidth = 0
    }

    private fun setButtonState(enabled: Boolean, enabledColor: Int, disabledColor: Int) {
        binding.buttonSend.isEnabled = enabled
        val color = if (enabled) enabledColor else disabledColor
        binding.buttonSend.backgroundTintList = ColorStateList.valueOf(color)

        val textColor = if (enabled) {
            ContextCompat.getColor(this, R.color.shape_01_white)
        } else {
            ContextCompat.getColor(this, R.color.text_complement)
        }
        binding.buttonSend.setTextColor(textColor)

        binding.buttonSend.text = if (enabled) {
            getString(R.string.button_send)
        } else {
            getString(R.string.button_enter)
        }
    }

    private fun handleSendResetEmail() {
        val email = binding.textInputEditEmail.text?.toString()?.trim() ?: ""

        if (!ValidationUtils.isValidEmail(email)) {
            val errorMsg = if (email.isEmpty()) {
                getString(R.string.error_empty_email)
            } else {
                getString(R.string.error_invalid_email)
            }
            showEmailError(errorMsg)
            return
        }

        clearEmailError()
        val intent = Intent(this, FinallyActivity::class.java).apply {
            putExtra("textViewTitleFinally", getString(R.string.reset_password_sent_title))
            putExtra("textViewSubtitleFinally", getString(R.string.reset_password_sent_subtitle))
            putExtra("titleButton", getString(R.string.button_back_to_login))
        }
        startActivity(intent)
        finish()
    }

    private fun goBack() {
        finish()
    }
}