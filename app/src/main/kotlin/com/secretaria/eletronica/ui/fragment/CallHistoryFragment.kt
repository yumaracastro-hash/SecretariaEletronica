package com.secretaria.eletronica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.secretaria.eletronica.databinding.FragmentCallHistoryBinding
import com.secretaria.eletronica.ui.adapter.CallLogAdapter
import com.secretaria.eletronica.ui.viewmodel.CallLogViewModel
import com.secretaria.eletronica.util.Logger

class CallHistoryFragment : Fragment() {

    private var _binding: FragmentCallHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CallLogViewModel by viewModels()
    private lateinit var adapter: CallLogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCallHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.i("CallHistoryFragment: onViewCreated")

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = CallLogAdapter(
            onDeleteClick = { callLog ->
                Logger.i("CallHistoryFragment: Deleting call log - ${callLog.phoneNumber}")
                viewModel.deleteCallLog(callLog)
            }
        )

        binding.callHistoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CallHistoryFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.allCallLogs.observe(viewLifecycleOwner) { callLogs ->
            Logger.i("CallHistoryFragment: Call logs updated - ${callLogs.size} items")
            adapter.submitList(callLogs)
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        binding.btnClearHistory.setOnClickListener {
            Logger.i("CallHistoryFragment: Clearing history")
            viewModel.deleteAllCallLogs()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
