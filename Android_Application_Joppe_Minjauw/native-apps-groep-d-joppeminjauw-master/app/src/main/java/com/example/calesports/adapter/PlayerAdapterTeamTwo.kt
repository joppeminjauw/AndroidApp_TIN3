package com.example.calesports.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calesports.database.entity.Player
import com.example.calesports.databinding.PlayerEntryTeamTwoBinding

class PlayerAdapterTeamTwo(val onClickListener: OnClickListener) : ListAdapter<Player, PlayerAdapterTeamTwo.PlayerViewHolder>(DiffCallback) {

    class PlayerViewHolder(private var binding: PlayerEntryTeamTwoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            binding.playerEntry = player
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerEntryTeamTwoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(player)
        }
        holder.bind(player)
    }

    class OnClickListener(val clickListener: (player: Player) -> Unit) {
        fun onClick(player: Player) = clickListener(player)
    }
}