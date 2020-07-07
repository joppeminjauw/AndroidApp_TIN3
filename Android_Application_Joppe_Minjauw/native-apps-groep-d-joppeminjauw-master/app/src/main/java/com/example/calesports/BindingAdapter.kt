package com.example.calesports

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.calesports.adapter.MatchAdapter
import com.example.calesports.adapter.PlayerAdapterTeamOne
import com.example.calesports.adapter.PlayerAdapterTeamTwo
import com.example.calesports.database.entity.Match
import com.example.calesports.database.entity.Player
import com.example.calesports.helpers.CryptonicApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Match>?) {
    val adapter = recyclerView.adapter as MatchAdapter
    adapter.submitList(data)
}

@BindingAdapter("teamOneListData")
fun bindRecyclerViewTeamOne(recyclerView: RecyclerView, data: List<Player>?) {
    val adapter = recyclerView.adapter as PlayerAdapterTeamOne
    adapter.submitList(data)
}

@BindingAdapter("teamTwoListData")
fun bindRecyclerViewTeamTwo(recyclerView: RecyclerView, data: List<Player>?) {
    val adapter = recyclerView.adapter as PlayerAdapterTeamTwo
    adapter.submitList(data)
}

@BindingAdapter("cryptonicApiStatus")
fun bindStatus(statusImageView: ImageView, status: CryptonicApiStatus?) {
    when (status) {
        CryptonicApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        CryptonicApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        CryptonicApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {
        imgView.setImageResource(R.drawable.defaultplayer)
    } else {
        val imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.defaultplayer))
            .into(imgView)
    }
}

@BindingAdapter("imageTeamUrl")
fun bindTeam(imgView: ImageView, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {
        imgView.setImageResource(R.drawable.defaultteam)
    } else {
        val imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions().placeholder(R.drawable.defaultteam))
            .into(imgView)
    }
}