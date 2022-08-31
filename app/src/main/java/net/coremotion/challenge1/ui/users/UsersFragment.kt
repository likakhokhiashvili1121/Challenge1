package net.coremotion.challenge1.ui.users


import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.coremotion.challenge1.databinding.UsersFragmentBinding
import net.coremotion.challenge1.ui.base.BaseFragment
import net.coremotion.challenge1.ui.users.source.UsersAdapter

@AndroidEntryPoint
class UsersFragment : BaseFragment<UsersFragmentBinding>(UsersFragmentBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var usersAdapter: UsersAdapter

    override fun start() {
        binding.swipeRefresh.isRefreshing = true
        initUsersRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            usersAdapter.refresh()
        }
    }

    private fun initUsersRecyclerView() {
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            usersAdapter = UsersAdapter().apply {
                userItemOnClick = {
                    openUserDetail(it)
                }
            }
            adapter = usersAdapter
        }
    }

    private fun openUserDetail(userId: Int) {
        UsersFragmentDirections.actionNewsFragmentToUserDetailFragment(
            userId
        )
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersFlow().collectLatest { pagingData ->
                binding.swipeRefresh.isRefreshing = false
                usersAdapter.submitData(pagingData)
            }
        }
    }
}