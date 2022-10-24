package com.loskon.usercrud.app.userinfo.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.loskon.usercrud.R
import com.loskon.usercrud.base.extension.coroutines.observe
import com.loskon.usercrud.base.extension.fragment.hideKeyboard
import com.loskon.usercrud.base.extension.view.setDebounceClickListener
import com.loskon.usercrud.base.extension.view.setDebounceMenuItemClickListener
import com.loskon.usercrud.base.extension.view.setMenuItemVisibility
import com.loskon.usercrud.base.extension.widget.BaseSnackbar
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.FragmentUserInfoBinding
import com.loskon.usercrud.domain.UserModel
import com.loskon.usercrud.util.ImageLoader
import com.loskon.usercrud.util.NetworkUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private val binding by viewBinding(FragmentUserInfoBinding::bind)
    private val viewModel by viewModel<UserInfoViewModel>()
    private val args by navArgs<UserInfoFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if (NetworkUtil.hasConnected(requireContext())) {
                if (args.id == NEW_USER_ID) {
                    viewModel.prepareAddUser()
                } else {
                    viewModel.performUserRequest(args.id)
                }
            } else {
                viewModel.notifyNoInternet()
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
                is UserInfoUiState.Loading -> {
                    binding.indicatorUserInfo.isVisible = true
                }
                is UserInfoUiState.Success -> {
                    binding.indicatorUserInfo.isVisible = false
                    binding.scrollViewUserInfo.isVisible = true
                    binding.btnUserInfo.isVisible = true
                    binding.tvNoInternetUserInfo.isVisible = false
                    binding.btnUserInfo.text = getString(R.string.save_changes)
                    binding.bottomBarUserInfo.setMenuItemVisibility(R.id.action_delete, true)

                    ImageLoader.load(binding.ivPhotoUserInfo, it.user.photoUrl)
                    binding.inputEditTextLastName.setText(it.user.lastName)
                    binding.inputEditTextFirstName.setText(it.user.firstName)
                    binding.inputEditTextMiddleName.setText(it.user.middleName)
                    binding.inputEditTextBirthDate.setText(it.user.birthDate)
                    binding.inputEditTextPhone.setText(it.user.phone)
                    binding.inputEditTextEmail.setText(it.user.email)
                }
                is UserInfoUiState.AddUser -> {
                    binding.indicatorUserInfo.isVisible = false
                    binding.scrollViewUserInfo.isVisible = true
                    binding.btnUserInfo.isVisible = true
                    binding.tvNoInternetUserInfo.isVisible = false
                    binding.btnUserInfo.text = getString(R.string.add_user)
                    binding.bottomBarUserInfo.setMenuItemVisibility(R.id.action_delete, false)
                }
                is UserInfoUiState.NoInternet -> {
                    binding.indicatorUserInfo.isVisible = false
                    binding.btnUserInfo.isVisible = false
                    binding.tvNoInternetUserInfo.isVisible = true
                    binding.bottomBarUserInfo.setMenuItemVisibility(R.id.action_delete, false)
                }
                is UserInfoUiState.Error -> {
                    binding.indicatorUserInfo.isVisible = false
                    binding.tvNoInternetUserInfo.isVisible = false

                    showErrorMessageSnackbar(R.string.loading_error)
                }
            }
        }
    }

    private fun setupViewsListeners() {
        binding.btnUserInfo.setDebounceClickListener {
            val user = getUpdatedUser()

            if (user != null) {
                if (NetworkUtil.hasConnected(requireContext())) {
                    if (args.id == NEW_USER_ID) {
                        // viewModel.addUser(user)
                    } else {
                        // viewModel.updateUser(user.id, user)
                    }
                    findNavController().popBackStack()
                } else {
                    viewModel.notifyNoInternet()
                }
            } else {
                showErrorMessageSnackbar(R.string.fill_all_fields_error)
            }
        }
        binding.bottomBarUserInfo.setDebounceMenuItemClickListener(R.id.action_delete) {
            if (NetworkUtil.hasConnected(requireContext())) {
                // val user = viewModel.userFlow.value
                // viewModel.deleteUser(user.id)
                findNavController().popBackStack()
            } else {
                viewModel.notifyNoInternet()
            }
        }
        binding.bottomBarUserInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    @Suppress("ComplexCondition")
    private fun getUpdatedUser(): UserModel? {
        val lastName = binding.inputEditTextLastName.getCheckedText()
        val firstName = binding.inputEditTextFirstName.getCheckedText()
        val middleName = binding.inputEditTextMiddleName.getCheckedText()
        val birthDate = binding.inputEditTextBirthDate.getCheckedText()
        val phone = binding.inputEditTextPhone.getCheckedText()
        val email = binding.inputEditTextEmail.getCheckedText()

        return if (
            lastName != null &&
            firstName != null &&
            middleName != null &&
            birthDate != null &&
            phone != null &&
            email != null
        ) {
            val user = viewModel.userFlow.value

            user.lastName = lastName
            user.firstName = firstName
            user.middleName = middleName
            user.birthDate = birthDate
            user.phone = phone
            user.email = email

            user
        } else {
            null
        }
    }

    private fun TextInputEditText.getCheckedText(): String? {
        return editableText.toString().ifEmpty {
            error = getString(R.string.enter_data_error)
            null
        }?.trim()
    }

    private fun showErrorMessageSnackbar(messageId: Int) {
        BaseSnackbar().create {
            make(binding.root, getString(messageId))
            setBackgroundTintList(requireContext().getColor(R.color.error_color))
            setTopGravity()
            enableHideByClickSnackbar()
        }.show()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    companion object {
        private const val NEW_USER_ID: Int = 0
    }
}