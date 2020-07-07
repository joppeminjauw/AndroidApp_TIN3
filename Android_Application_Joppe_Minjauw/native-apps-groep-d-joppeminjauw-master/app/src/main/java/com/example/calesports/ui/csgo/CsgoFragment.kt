package com.example.calesports.ui.csgo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.calesports.R
import com.example.calesports.adapter.MatchAdapter
import com.example.calesports.database.CryptonicDatabase
import com.example.calesports.databinding.FragmentCsgoBinding
import com.example.calesports.helpers.CryptonicApiStatus

class CsgoFragment : Fragment() {

    private lateinit var csgoViewModel: CsgoViewModel

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

        val viewModelFactory = CsgoViewmodelFactory(dataSource, application)
        csgoViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CsgoViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentCsgoBinding>(
            inflater,
            R.layout.fragment_csgo,
            container,
            false
        )

        binding.csgoMatches!!.adapter = MatchAdapter(MatchAdapter.OnClickListener {
            if (null != it) {
                this.findNavController().navigate(CsgoFragmentDirections.actionCsgoFragmentToMatchInfoFragment(it))
            }
        })

        binding.setLifecycleOwner(this)

        binding.csgoViewModel = csgoViewModel

        (activity as AppCompatActivity).supportActionBar?.title = "Cryptonic - CS:GO"

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
        csgoViewModel.status.observe(this, statusObserver)

        return binding.root
    }
}
