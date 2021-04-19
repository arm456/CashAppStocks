package com.example.cashappstocks.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cashappstocks.R
import com.example.cashappstocks.dagger2.CashStocksApplication
import com.example.cashappstocks.repository.CashStocksRepository
import javax.inject.Inject

class CashStockOptionsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    @Inject
    lateinit var stocksRepository: CashStocksRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as CashStocksApplication).appComponent.inject(this)

        val validStockOptions = view.findViewById<Button>(R.id.option_1)
        val invalidStockOptions = view.findViewById<Button>(R.id.option_2)
        val emptyStockOptions = view.findViewById<Button>(R.id.option_3)

        validStockOptions.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
        invalidStockOptions.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio_malformed.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
        emptyStockOptions.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio_empty.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
    }
}