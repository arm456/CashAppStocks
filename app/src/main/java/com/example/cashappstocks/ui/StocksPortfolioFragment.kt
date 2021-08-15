package com.example.cashappstocks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashappstocks.dagger2.CashStocksApplication
import com.example.cashappstocks.databinding.FragmentStocksDetailsBinding
import com.example.cashappstocks.repository.CashStocksRepository
import com.example.cashappstocks.viewmodel.StocksPortfolioViewModel
import javax.inject.Inject

class StocksPortfolioFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private lateinit var portfolioViewModel: StocksPortfolioViewModel
    private lateinit var storesAdapter: StockDetailsAdapter

    @Inject
    lateinit var cashStocksRepository: CashStocksRepository

    private var _binding: FragmentStocksDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStocksDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as CashStocksApplication).appComponent.inject(this)

        portfolioViewModel = ViewModelProvider(
            this,
            StocksPortfolioViewModel.Factory(
                repository = cashStocksRepository,
                context = this.context
            )
        ).get(StocksPortfolioViewModel::class.java)
        arguments?.getString(PORTFOLIO_ID)?.let { portfolioViewModel.getStockDetails(it) }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.storesRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(binding.storesRecyclerView.context, layoutManager.orientation)
        binding.storesRecyclerView.addItemDecoration(dividerItemDecoration)

        storesAdapter = StockDetailsAdapter()
        binding.storesRecyclerView.adapter = storesAdapter

        portfolioViewModel.cashStocksLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.loadingView.loadingSpinner.visibility = View.GONE
                if (it != null)
                    storesAdapter.setData(it)
                if (it.size == 0)
                    binding.emptyText.visibility = View.VISIBLE
            }
        )

        portfolioViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingView.loadingSpinner.visibility = View.GONE
            binding.emptyText.text = it.errorMessage.toString()
            binding.emptyText.visibility = View.VISIBLE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PORTFOLIO_ID = "PORTFOLIO_ID"

        @JvmStatic
        fun newInstance(id: String, contentLayoutId: Int): StocksPortfolioFragment {
            return StocksPortfolioFragment(contentLayoutId).apply {
                arguments = Bundle().apply {
                    putString(PORTFOLIO_ID, id)
                }
            }
        }
    }

}