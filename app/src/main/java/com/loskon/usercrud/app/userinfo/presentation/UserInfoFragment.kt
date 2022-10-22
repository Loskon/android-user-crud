package com.loskon.usercrud.app.userinfo.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.loskon.usercrud.R
import com.loskon.usercrud.base.extension.coroutines.observe
import com.loskon.usercrud.base.extension.fragment.hideKeyboard
import com.loskon.usercrud.base.extension.view.setDebounceClickListener
import com.loskon.usercrud.base.extension.view.setDebounceMenuItemClickListener
import com.loskon.usercrud.base.extension.view.setMenuItemVisibility
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.FragmentUserInfoBinding
import com.loskon.usercrud.util.ImageLoader
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private val binding by viewBinding(FragmentUserInfoBinding::bind)
    private val viewModel by viewModel<UserInfoViewModel>()
    private val args by navArgs<UserInfoFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if (args.id == NEW_USER_ID) {
                viewModel.performAddUser()
            } else {
                viewModel.performUserRequest(args.id)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installObserver()
        setupViewsListeners()
    }

    private fun installObserver() {
        viewModel.userInfoFlow.observe(viewLifecycleOwner) {
            when (it) {
                is UserInfoState.Loading -> {
                    binding.indicatorInfo.isVisible = true
                }
                is UserInfoState.Success -> {
                    binding.indicatorInfo.isVisible = false
                    binding.scrollViewInfo.isVisible = true
                    binding.btnInfo.text = getString(R.string.save_changes)

                    ImageLoader.load(binding.ivPhotoInfo, it.user.photoUrl)
                    binding.inputEditTextLastName.setText(it.user.lastName)
                    binding.inputEditTextFirstName.setText(it.user.firstName)
                    binding.inputEditTextMiddleName.setText(it.user.middleName)
                    binding.inputEditTextBirthDate.setText(it.user.birthDate)
                    binding.inputEditTextPhone.setText(it.user.phone)
                    binding.inputEditTextEmail.setText(it.user.email)
                }
                is UserInfoState.AddUser -> {
                    binding.indicatorInfo.isVisible = false
                    binding.scrollViewInfo.isVisible = true
                    binding.btnInfo.text = getString(R.string.add_user)
                    binding.bottomBarInfo.setMenuItemVisibility(R.id.action_delete, false)
                }
                is UserInfoState.Error -> {
                    binding.indicatorInfo.isVisible = false
                }
            }
        }
    }

    private fun setupViewsListeners() {
        binding.btnInfo.setDebounceClickListener {
            checkEmptyEditTextFields()
        }
        binding.bottomBarInfo.setDebounceMenuItemClickListener(R.id.action_delete) {
            deleteUser()
            findNavController().popBackStack()
        }
        binding.bottomBarInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    // TODO BAD
    private fun checkEmptyEditTextFields() {
        val lastName = binding.inputEditTextLastName.editableText.toString()
        val firstName = binding.inputEditTextFirstName.editableText.toString()
        val middleName = binding.inputEditTextMiddleName.editableText.toString()
        val birthDate = binding.inputEditTextBirthDate.editableText.toString()
        val phone = binding.inputEditTextPhone.editableText.toString()
        val email = binding.inputEditTextEmail.editableText.toString()

        if (lastName.isEmpty()) binding.inputEditTextLastName.error = getString(R.string.enter_data_error)
        if (firstName.isEmpty()) binding.inputEditTextFirstName.error = getString(R.string.enter_data_error)
        if (middleName.isEmpty()) binding.inputEditTextMiddleName.error = getString(R.string.enter_data_error)
        if (birthDate.isEmpty()) binding.inputEditTextBirthDate.error = getString(R.string.enter_data_error)
        if (phone.isEmpty()) binding.inputEditTextPhone.error = getString(R.string.enter_data_error)
        if (email.isEmpty()) binding.inputEditTextEmail.error = getString(R.string.enter_data_error)

        if (lastName.isNotEmpty() &&
            firstName.isNotEmpty() &&
            middleName.isNotEmpty() &&
            birthDate.isNotEmpty() &&
            phone.isNotEmpty() &&
            email.isNotEmpty()
        ) {
            val user = viewModel.userFlow.value

            user.lastName = lastName
            user.firstName = firstName
            user.middleName = middleName
            user.birthDate = birthDate
            user.phone = phone
            user.email = email

            if (args.id == NEW_USER_ID) {
                //viewModel.addUser(user)
            } else {
                //viewModel.updateUser(user)
            }

            findNavController().popBackStack()
        }
    }

    private fun deleteUser() {
        val user = viewModel.userFlow.value
        //viewModel.deleteUser(user)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    companion object {
        private const val NEW_USER_ID: Int = 0
    }
}