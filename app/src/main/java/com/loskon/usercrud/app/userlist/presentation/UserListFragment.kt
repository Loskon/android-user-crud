package com.loskon.usercrud.app.userlist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.loskon.usercrud.R
import com.loskon.usercrud.base.extension.coroutines.observe
import com.loskon.usercrud.base.extension.view.setDebounceClickListener
import com.loskon.usercrud.base.extension.widget.BaseSnackbar
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.FragmentUserListBinding
import com.loskon.usercrud.util.AppPreference
import com.loskon.usercrud.util.NetworkUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val binding by viewBinding(FragmentUserListBinding::bind)
    private val viewModel by viewModel<UserListViewModel>()

    private val userListAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if (NetworkUtil.hasConnected(requireContext())) {
                viewModel.performUsersRequest()
            } else {
                viewModel.notifyNoInternet()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        installObserver()
        setupViewsListeners()
    }

    private fun configureRecyclerView() {
        with(binding.recyclerVIew) {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userListAdapter
            setHasFixedSize(true)
        }
    }

    private fun installObserver() {
        viewModel.userListFlow.observe(viewLifecycleOwner) {
            when (it) {
                is UserListUiState.Loading -> {
                    binding.indicator.isVisible = true
                }
                is UserListUiState.Success -> {
                    binding.indicator.isVisible = false
                    binding.tvNoInternetList.isVisible = false

                    userListAdapter.setItems(it.users)
                }
                is UserListUiState.NoInternet -> {
                    binding.indicator.isVisible = false
                    binding.tvNoInternetList.isVisible = true
                }
                is UserListUiState.Failure -> {
                    binding.tvNoInternetList.isVisible = false
                    binding.indicator.isVisible = false

                    showErrorMessageSnackbar(R.string.loading_error)
                }
            }
        }
    }

    private fun showErrorMessageSnackbar(messageId: Int) {
        BaseSnackbar().create {
            make(binding.root, getString(messageId))
            setBackgroundTintList(requireContext().getColor(R.color.error_color))
            setTopGravity()
            enableHideByClickSnackbar()
        }.show()
    }

    private fun setupViewsListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false

            if (NetworkUtil.hasConnected(requireContext())) {
                viewModel.performUsersRequest()
            } else {
                viewModel.notifyNoInternet()
            }
        }
        userListAdapter.setOnItemClickListener {
            val action = UserListFragmentDirections.actionOpenUserInfoFragment(1)
            findNavController().navigate(action)
        }
        binding.button.setDebounceClickListener {
            val action = UserListFragmentDirections.actionOpenUserInfoFragment(0)
            findNavController().navigate(action)
        }
        binding.bottomBar.setNavigationOnClickListener {
            AppPreference.setUserAuthenticated(requireContext(), authenticated = false)
            findNavController().navigate(R.id.actionOpenLoginFragment)
        }
    }
}