package com.example.calesports.ui.lol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.calesports.R
import com.example.calesports.adapter.MatchAdapter
import com.example.calesports.database.CryptonicDatabase
import com.example.calesports.databinding.FragmentLolBinding
import com.example.calesports.helpers.CryptonicApiStatus

class LolFragment : Fragment() {

    private lateinit var lolViewModel: LolViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val application = requireNotNull(this.activity).application
        val dataSource = CryptonicDatabase.getInstance(application).cryptonicDatabaseDao

        val viewModelFactory = LolViewmodelFactory(dataSource, application)
        lolViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LolViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentLolBinding>(
            inflater,
            R.layout.fragment_lol,
            container,
            false
        )

        binding.lolMatches!!.adapter = MatchAdapter(MatchAdapter.OnClickListener {
            if (null != it) {
                this.findNavController().navigate(LolFragmentDirections.actionLolFragmentToMatchInfoFragment(it))
            }
        })

        binding.setLifecycleOwner(this)

        binding.lolViewModel = this.lolViewModel

        (activity as AppCompatActivity).supportActionBar?.title = "Cryptonic - League of Legends"

        val statusObserver = Observer<CryptonicApiStatus> { newStatus ->
            if (newStatus != null) {
                if (newStatus == CryptonicApiStatus.LOADING) {
                    binding.progressBarLoading!!.visibility = View.VISIBLE
                }
                if (newStatus == CryptonicApiStatus.DONE) {
                    binding.progressBarLoading!!.visibility = View.GONE
                }
            }
        }

        lolViewModel.status.observe(this, statusObserver)

        return binding.root
    }
}