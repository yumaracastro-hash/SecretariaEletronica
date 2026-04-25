package com.secretaria.eletronica.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.secretaria.eletronica.databinding.FragmentGreetingsBinding
import com.secretaria.eletronica.ui.adapter.GreetingAdapter
import com.secretaria.eletronica.ui.viewmodel.GreetingViewModel
import com.secretaria.eletronica.util.Logger
import com.secretaria.eletronica.util.PermissionManager

class GreetingsFragment : Fragment() {

    private var _binding: FragmentGreetingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GreetingViewModel by viewModels()
    private lateinit var adapter: GreetingAdapter

    private val requestAudioPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Logger.i("GreetingsFragment: Audio permission granted")
        } else {
            Toast.makeText(
                requireContext(),
                "Permissão de áudio necessária",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.i("GreetingsFragment: onViewCreated")

        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        // Inicializar saudações padrão
        viewModel.initializeDefaultGreetings()
    }

    private fun setupRecyclerView() {
        adapter = GreetingAdapter(
            onPlayClick = { greeting ->
                Logger.i("GreetingsFragment: Playing greeting - ${greeting.name}")
                // Implementar reprodução
            },
            onRecordClick = { greeting ->
                Logger.i("GreetingsFragment: Recording greeting - ${greeting.name}")
                // Verificar permissão de áudio
                if (PermissionManager.hasPermission(
                        requireContext(),
                        Manifest.permission.RECORD_AUDIO
                    )
                ) {
                    // Implementar gravação
                } else {
                    requestAudioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            onSelectClick = { greeting ->
                Logger.i("GreetingsFragment: Selecting greeting - ${greeting.name}")
                viewModel.setActiveGreeting(greeting.id)
            },
            onDeleteClick = { greeting ->
                Logger.i("GreetingsFragment: Deleting greeting - ${greeting.name}")
                viewModel.deleteGreeting(greeting)
            }
        )

        binding.greetingsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@GreetingsFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.allGreetings.observe(viewLifecycleOwner) { greetings ->
            Logger.i("GreetingsFragment: Greetings updated - ${greetings.size} items")
            adapter.submitList(greetings)
        }

        viewModel.activeGreeting.observe(viewLifecycleOwner) { greeting ->
            if (greeting != null) {
                binding.activeGreetingName.text = greeting.name
            } else {
                binding.activeGreetingName.text = "Nenhuma saudação ativa"
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        binding.btnPlayActive.setOnClickListener {
            Logger.i("GreetingsFragment: Playing active greeting")
            // Implementar reprodução da saudação ativa
        }

        binding.btnRecordActive.setOnClickListener {
            Logger.i("GreetingsFragment: Recording active greeting")
            // Implementar gravação da saudação ativa
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
