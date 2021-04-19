package com.example.cashappstocks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cashappstocks.R
import com.example.cashappstocks.dagger2.CashStocksApplication
import com.example.cashappstocks.databinding.FragmentStocksOptionsBinding
import com.example.cashappstocks.repository.CashStocksRepository
import javax.inject.Inject

class CashStockOptionsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    @Inject
    lateinit var stocksRepository: CashStocksRepository

    private var _binding: FragmentStocksOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStocksOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as CashStocksApplication).appComponent.inject(this)

        binding.validStockOption.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
        binding.invalidStockOption.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio_malformed.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
        binding.emptyStockOption.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.container,
                StocksPortfolioFragment.newInstance(
                    "portfolio_empty.json",
                    R.layout.fragment_stocks_details
                )
            )?.addToBackStack(StocksPortfolioFragment::class.java.canonicalName)?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}