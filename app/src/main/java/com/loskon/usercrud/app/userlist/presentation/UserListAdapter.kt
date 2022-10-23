package com.loskon.usercrud.app.userlist.presentation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loskon.usercrud.base.extension.view.setDebounceClickListener
import com.loskon.usercrud.base.viewbinding.viewBinding
import com.loskon.usercrud.databinding.ItemUserCardBinding
import com.loskon.usercrud.domain.UserModel
import com.loskon.usercrud.util.ImageLoader

@SuppressLint("NotifyDataSetChanged")
class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var list: List<UserModel> = emptyList()

    private var onItemClick: ((UserModel) -> Unit)? = null

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = list[position]

        with(holder.binding) {
            user.apply {
                ImageLoader.load(ivPhotoUserCard, photoUrl)
                tvFullnameUserCard.text = fullName
                tvPhoneUserCard.text = phone
                tvEmailUserCard.text = email
                root.setDebounceClickListener { onItemClick?.invoke(this) }
            }
        }
    }

    fun setItems(list: List<UserModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClick: ((UserModel) -> Unit)?) {
        this.onItemClick = onItemClick
    }

    class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}