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
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.FragmentUserListBinding
import com.loskon.usercrud.util.AppPreference
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val binding by viewBinding(FragmentUserListBinding::bind)
    private val viewModel by viewModel<UserListViewModel>()

    private val userListAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) viewModel.performUsersRequest()
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
                is UserListState.Loading -> {
                    binding.indicator.isVisible = true
                }
                is UserListState.Success -> {
                    binding.indicator.isVisible = false
                    userListAdapter.setItems(it.users)
                }
                is UserListState.Failure -> {
                    binding.indicator.isVisible = false
                }
            }
        }
    }

    private fun setupViewsListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.performUsersRequest()
        }
        userListAdapter.setOnItemClickListener {
            val action = UserListFragmentDirections.actionOpenUserInfoFragment(1)
            findNavController().navigate(action)
        }
        binding.fab.setDebounceClickListener {
            val action = UserListFragmentDirections.actionOpenUserInfoFragment(0)
            findNavController().navigate(action)
        }
        binding.bottomBar.setNavigationOnClickListener {
            AppPreference.setUserAuthenticated(requireContext(), authenticated = false)
            findNavController().navigate(R.id.actionOpenLoginFragment)
        }
    }
}