package com.secretaria.eletronica.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.secretaria.eletronica.data.entity.CallLogEntity
import com.secretaria.eletronica.databinding.ItemCallLogBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallLogAdapter(
    private val onDeleteClick: (CallLogEntity) -> Unit
) : ListAdapter<CallLogEntity, CallLogAdapter.CallLogViewHolder>(CallLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val binding = ItemCallLogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CallLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        val callLog = getItem(position)
        holder.bind(callLog)
    }

    inner class CallLogViewHolder(private val binding: ItemCallLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(callLog: CallLogEntity) {
            binding.apply {
                callContactName.text = callLog.contactName ?: "Desconhecido"
                callPhoneNumber.text = callLog.phoneNumber

                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val dateString = dateFormat.format(Date(callLog.callDate))
                callDate.text = dateString

                callSim.text = "SIM ${callLog.simSlot}"

                btnDeleteCall.setOnClickListener {
                    onDeleteClick(callLog)
                }
            }
        }
    }

    private class CallLogDiffCallback : DiffUtil.ItemCallback<CallLogEntity>() {
        override fun areItemsTheSame(oldItem: CallLogEntity, newItem: CallLogEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CallLogEntity, newItem: CallLogEntity): Boolean {
            return oldItem == newItem
        }
    }
}
