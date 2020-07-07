package com.example.calesports.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calesports.database.entity.Match
import com.example.calesports.databinding.MatchEntryBinding

class MatchAdapter(val onClickListener: OnClickListener) : ListAdapter<Match, MatchAdapter.MatchViewHolder>(DiffCallback) {

    class MatchViewHolder(private var binding: MatchEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.matchEntry = match
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(MatchEntryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(match)
        }
        holder.bind(match)
    }

    class OnClickListener(val clickListener: (match: Match) -> Unit) {
        fun onClick(match: Match) = clickListener(match)
    }
}