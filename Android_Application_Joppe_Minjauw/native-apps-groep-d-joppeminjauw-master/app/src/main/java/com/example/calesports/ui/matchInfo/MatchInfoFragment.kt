package com.example.calesports.ui.matchInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.calesports.NetManager
import com.example.calesports.R
import com.example.calesports.adapter.PlayerAdapterTeamOne
import com.example.calesports.adapter.PlayerAdapterTeamTwo
import com.example.calesports.database.CryptonicDatabase
import com.example.calesports.databinding.FragmentMatchInfoBinding
import com.example.calesports.helpers.CryptonicApiStatus

class MatchInfoFragment : Fragment() {

    private lateinit var matchInfoViewModel: MatchInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        setHasOptionsMenu(true)

        val args: MatchInfoFragmentArgs by navArgs()
        val application = requireNotNull(this.activity).application
        val dataSource = CryptonicDatabase.getInstance(application).cryptonicDatabaseDao

        val binding = DataBindingUtil.inflate<FragmentMatchInfoBinding>(
            inflater,
            R.layout.fragment_match_info,
            container,
            false
        )

        val netManager = NetManager(context!!)

        if (!netManager.isConnectedToInternet!!) {
            binding.errorImage.visibility = View.VISIBLE
            binding.matchVs.visibility = View.GONE
        } else {
            binding.errorImage.visibility = View.GONE
            binding.matchVs.visibility = View.VISIBLE

            val viewModelFactory = MatchInfoViewModelFactory(args.match, application, dataSource)
            matchInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MatchInfoViewModel::class.java)

            binding.viewModel = matchInfoViewModel

            binding.teamOnePlayers!!.adapter = PlayerAdapterTeamOne(PlayerAdapterTeamOne.OnClickListener {})
            binding.teamTwoPlayers!!.adapter = PlayerAdapterTeamTwo(PlayerAdapterTeamTwo.OnClickListener {})

            val observer = Observer<Int> { newStatus ->
                if (newStatus == 1) {
                    matchInfoViewModel.getTeamsFromDb()
                }
            }

            matchInfoViewModel.testStatus.observe(this, observer)

            val statusObserver = Observer<CryptonicApiStatus> { newStatus ->
                if (newStatus != null) {
                    if (newStatus == CryptonicApiStatus.LOADING) {
                        binding.progressBarLoading!!.visibility = View.VISIBLE
                        binding.matchVs.visibility = View.GONE
                    }
                    if (newStatus == CryptonicApiStatus.DONE) {
                        binding.progressBarLoading!!.visibility = View.GONE
                        binding.matchVs.visibility = View.VISIBLE

                        binding.viewModel = matchInfoViewModel

                        binding.teamOnePlayers!!.adapter = PlayerAdapterTeamOne(PlayerAdapterTeamOne.OnClickListener {})
                        binding.teamTwoPlayers!!.adapter = PlayerAdapterTeamTwo(PlayerAdapterTeamTwo.OnClickListener {})
                    }
                }
            }
            matchInfoViewModel.status.observe(this, statusObserver)
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Cryptonic - Match info"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            if (matchInfoViewModel.selectedMatch.value?.videogame?.id === 3) {
                view!!.findNavController().navigate(R.id.action_matchInfoFragment_to_csgoFragment)
                return true
            }
            if (matchInfoViewModel.selectedMatch.value?.videogame?.id === 1) {
                view!!.findNavController().navigate(R.id.action_matchInfoFragment_to_lolFragment)
                return true
            }
            if (matchInfoViewModel.selectedMatch.value?.videogame?.id === 4) {
                view!!.findNavController().navigate(R.id.action_matchInfoFragment_to_dotaFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}