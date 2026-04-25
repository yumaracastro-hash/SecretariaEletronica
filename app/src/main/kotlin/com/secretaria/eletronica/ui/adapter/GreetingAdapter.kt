package com.secretaria.eletronica.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.secretaria.eletronica.data.entity.GreetingEntity
import com.secretaria.eletronica.databinding.ItemGreetingBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GreetingAdapter(
    private val onPlayClick: (GreetingEntity) -> Unit,
    private val onRecordClick: (GreetingEntity) -> Unit,
    private val onSelectClick: (GreetingEntity) -> Unit,
    private val onDeleteClick: (GreetingEntity) -> Unit
) : ListAdapter<GreetingEntity, GreetingAdapter.GreetingViewHolder>(GreetingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreetingViewHolder {
        val binding = ItemGreetingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GreetingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GreetingViewHolder, position: Int) {
        val greeting = getItem(position)
        holder.bind(greeting)
    }

    inner class GreetingViewHolder(private val binding: ItemGreetingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(greeting: GreetingEntity) {
            binding.apply {
                greetingName.text = greeting.name
                greetingDescription.text = greeting.description
                greetingRadio.isChecked = greeting.isActive

                if (greeting.duration > 0) {
                    val seconds = greeting.duration / 1000
                    greetingDuration.text = "Duração: ${seconds}s"
                } else {
                    greetingDuration.text = "Sem gravação"
                }

                greetingRadio.setOnClickListener {
                    onSelectClick(greeting)
                }

                btnPlay.setOnClickListener {
                    onPlayClick(greeting)
                }

                btnRecord.setOnClickListener {
                    onRecordClick(greeting)
                }

                btnDelete.setOnClickListener {
                    onDeleteClick(greeting)
                }
            }
        }
    }

    private class GreetingDiffCallback : DiffUtil.ItemCallback<GreetingEntity>() {
        override fun areItemsTheSame(oldItem: GreetingEntity, newItem: GreetingEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GreetingEntity, newItem: GreetingEntity): Boolean {
            return oldItem == newItem
        }
    }
}
