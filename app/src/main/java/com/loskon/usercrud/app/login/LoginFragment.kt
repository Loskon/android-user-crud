package com.loskon.usercrud.app.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.loskon.usercrud.R
import com.loskon.usercrud.base.extension.setDebounceClickListener
import com.loskon.usercrud.base.extension.setDisabledSpaceFilter
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewsParameters()
        setupViewsListeners()
    }

    private fun configureViewsParameters() {
        binding.inputEditTextLogin.setDisabledSpaceFilter()
        binding.inputEditTextPassword.setDisabledSpaceFilter()
    }

    private fun setupViewsListeners() {
        binding.inputEditTextLogin.doOnTextChanged { _, _, _, _ ->
            if (binding.inputLayoutLogin.error != null) binding.inputLayoutLogin.isErrorEnabled = false
        }
        binding.inputEditTextPassword.doOnTextChanged { _, _, _, _ ->
            if (binding.inputLayoutPassword.error != null) binding.inputLayoutPassword.isErrorEnabled = false
        }
        binding.btnLogin.setDebounceClickListener {
            val login = binding.inputEditTextLogin.editableText.toString()
            val password = binding.inputEditTextPassword.editableText.toString()

            if (login.isEmpty()) binding.inputLayoutLogin.error = getString(R.string.enter_login_error)
            if (password.isEmpty()) binding.inputLayoutPassword.error = getString(R.string.enter_password_error)

            if (login.isNotEmpty() && password.isNotEmpty()) checkLoginData(login, password)
        }
        binding.btnForgot.setDebounceClickListener {
            Toast.makeText(requireContext(), getString(R.string.ss), Toast.LENGTH_LONG).show()
        }
    }

    private fun checkLoginData(login: String, password: String) {
        if (login == LOGIN && password == PASSWORD) {
            // AppPreference.setLoggedUser(requireContext(), logged = true)
        } else {
            binding.inputLayoutLogin.error = getString(R.string.invalid_login_password_error)
            binding.inputLayoutPassword.error = getString(R.string.invalid_login_password_error)
        }
    }

    companion object {
        private const val LOGIN = "admin"
        private const val PASSWORD = "admin"
    }
}