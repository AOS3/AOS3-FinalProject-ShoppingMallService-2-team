package com.nemodream.bangkkujaengi.customer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nemodream.bangkkujaengi.R
import com.nemodream.bangkkujaengi.customer.data.model.Product
import com.nemodream.bangkkujaengi.customer.data.model.SortType
import com.nemodream.bangkkujaengi.customer.ui.adapter.ProductClickListener
import com.nemodream.bangkkujaengi.customer.ui.adapter.ProductGridAdapter
import com.nemodream.bangkkujaengi.customer.ui.viewmodel.PromotionViewModel
import com.nemodream.bangkkujaengi.databinding.FragmentPromotionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromotionFragment: Fragment(), ProductClickListener {
    private var _binding: FragmentPromotionBinding? = null
    private val binding get() = _binding!!

    // argument로 받은 title 프로퍼티
    private val title: String by lazy { arguments?.getString(ARG_TITLE) ?: "" }

    private val adapter: ProductGridAdapter by lazy { ProductGridAdapter(this) }
    private val viewModel: PromotionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromotionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        setupBackPress()
        viewModel.getPromotionByTitle(title)
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding) {
            chipPromotionSort.setOnClickListener {
                showSortPopup(it)
            }

            toolbarPromotion.setNavigationOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.customer_container, HomeFragment())
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.promotion.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.rvPromotionList.visibility = View.GONE
                binding.layoutResultEmpty.root.visibility = View.VISIBLE
            } else {
                binding.rvPromotionList.visibility = View.VISIBLE
                binding.layoutResultEmpty.root.visibility = View.GONE
            }
            adapter.submitList(it)
            binding.rvPromotionList.adapter = adapter
        }

        viewModel.sortText.observe(viewLifecycleOwner) { text ->
            binding.chipPromotionSort.text = text
        }
    }

    private fun setupUI() {
        binding.tvPromotionTitle.text = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.commit {
                replace(R.id.customer_container, HomeFragment())
            }
        }
    }

    private fun showSortPopup(view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.product_sort_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sort_purchase -> {
                        viewModel.updateSort(SortType.PURCHASE)
                        true
                    }
                    R.id.sort_review -> {
                        viewModel.updateSort(SortType.REVIEW)
                        true
                    }
                    R.id.sort_price_high -> {
                        viewModel.updateSort(SortType.PRICE_HIGH)
                        true
                    }
                    R.id.sort_price_low -> {
                        viewModel.updateSort(SortType.PRICE_LOW)
                        true
                    }
                    R.id.sort_discount -> {
                        viewModel.updateSort(SortType.DISCOUNT)
                        true
                    }
                    R.id.sort_views -> {
                        viewModel.updateSort(SortType.VIEWS)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }

    companion object {
        private const val ARG_TITLE = "title"
        fun newInstance(title: String): PromotionFragment {
            return PromotionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
        }
    }

    override fun onProductClick(product: Product) {
        val action = PromotionFragmentDirections.actionNavigationPromotionToNavigationProductDetail(product.productId)
        findNavController().navigate(action)
    }
}