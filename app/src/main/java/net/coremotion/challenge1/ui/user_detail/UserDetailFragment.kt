package net.coremotion.challenge1.ui.user_detail

import dagger.hilt.android.AndroidEntryPoint
import net.coremotion.challenge1.common.Resource
import net.coremotion.challenge1.databinding.FragmentUserDetailBinding
import net.coremotion.challenge1.extensions.setImage
import net.coremotion.challenge1.ui.base.BaseFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment :
    BaseFragment<FragmentUserDetailBinding>(FragmentUserDetailBinding::inflate) {

    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()


    override fun start() {
        viewModel.getUserDetail(args.userId)
        setListeners()
        observes()
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            getUserDetails(args.userId)
        }
    }

    private fun getUserDetails(userId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserDetail(userId = userId)
        }
    }

    private fun observes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userDetailFlow.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.swipeRefresh.isRefreshing = false
                        binding.tvName.text = it.data?.data?.firstName.plus(" ").plus(it.data?.data?.lastName)
                        binding.profileImage.setImage(it.data?.data?.avatar)
                    }
                    Resource.Status.ERROR -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    Resource.Status.LOADING -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                }
            }
        }
    }


}