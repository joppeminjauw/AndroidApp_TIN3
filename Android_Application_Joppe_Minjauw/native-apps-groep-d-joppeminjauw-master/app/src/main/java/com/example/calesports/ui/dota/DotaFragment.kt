package com.example.calesports.ui.dota

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
import com.example.calesports.databinding.FragmentDotaBinding
import com.example.calesports.helpers.CryptonicApiStatus

class DotaFragment : Fragment() {

    private lateinit var dotaViewModel: DotaViewModel

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

        val viewModelFactory = DotaViewModelFactory(dataSource, application)
        dotaViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DotaViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentDotaBinding>(
            inflater,
            R.layout.fragment_dota,
            container,
            false
        )

        binding.dotaMatches!!.adapter = MatchAdapter(MatchAdapter.OnClickListener {
            if (null != it) {
                this.findNavController().navigate(DotaFragmentDirections.actionDotaFragmentToMatchInfoFragment(it))
            }
        })

        binding.setLifecycleOwner(this)

        binding.dotaViewModel = this.dotaViewModel

        (activity as AppCompatActivity).supportActionBar?.title = "Cryptonic - Dota 2"

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
        dotaViewModel.status.observe(this, statusObserver)

        return binding.root
    }
}