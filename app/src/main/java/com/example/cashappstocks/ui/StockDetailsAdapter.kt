package com.example.cashappstocks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashappstocks.databinding.ItemStocksBinding
import com.example.cashappstocks.network.domain.Stock
import java.text.NumberFormat
import java.util.*

class StockDetailsAdapter(
    private var storesList: List<Stock> = emptyList()
) : RecyclerView.Adapter<StockDetailsAdapter.StoresViewHolder>() {

    fun setData(storesList: List<Stock>) {
        this.storesList = storesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresViewHolder {
        val binding = ItemStocksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) {
        val stock = storesList[position]
        holder.stockName?.text = stock.ticker
        val numFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        val stockPriceInCents: String = numFormat.format((stock.currentPriceCents)?.div(100.0))
        holder.stockPrice?.text = stockPriceInCents
        if (stock.quantity == null) {
            holder.stockStatus?.text = "${"("} ${0} ${")"}"
        } else {
            holder.stockStatus?.text = "${"("} ${stock.quantity} ${")"}"
        }
    }

    override fun getItemCount(): Int = if (storesList.isNullOrEmpty()) 0 else storesList.size

    class StoresViewHolder(binding: ItemStocksBinding) : RecyclerView.ViewHolder(binding.root) {
        val stockName: TextView? = binding.stockName
        val stockPrice: TextView? = binding.stockPrice
        val stockStatus: TextView? = binding.stockStatus
    }
}