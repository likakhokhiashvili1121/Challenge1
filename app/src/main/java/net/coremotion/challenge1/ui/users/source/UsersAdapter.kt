package net.coremotion.challenge1.ui.users.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.coremotion.challenge1.databinding.ItemUserBinding
import net.coremotion.challenge1.domain.model.Users
import net.coremotion.challenge1.extensions.setImage

typealias UserItemOnClick = (id: Int) -> Unit

class UsersAdapter() : PagingDataAdapter<Users.Data, UsersAdapter.UserViewHolder>(
    DiffCallback()
) {

    var userItemOnClick: UserItemOnClick? = null
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
        private var user: Users.Data? = null
        fun onBind() {
            this.user = getItem(bindingAdapterPosition)
            binding.profileImage.setImage(user?.avatar)
            binding.tvEmail.text = user?.email
            binding.tvName.text = user?.firstName.plus(" ").plus(user?.lastName)
            binding.root.setOnClickListener {
                userItemOnClick?.invoke(user?.id ?: -1)
            }
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Users.Data>() {
    override fun areItemsTheSame(oldItem: Users.Data, newItem: Users.Data) = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Users.Data, newItem: Users.Data) = oldItem == newItem

}