package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.PlayPurchase
import com.example.data.model.PriceTypeFilter
import com.example.data.model.PurchaseCategory
import com.example.data.model.PurchaseStatus
import com.example.data.model.SortOption
import com.example.data.model.TimeRangeFilter
import com.example.data.repository.PurchaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class CategorySpend(
    val category: PurchaseCategory,
    val totalAmount: Double,
    val count: Int,
    val percentage: Float
)

data class MonthlySpend(
    val monthLabel: String,
    val yearMonthKey: String,
    val totalAmount: Double,
    val count: Int
)

data class YearlySpend(
    val year: Int,
    val totalAmount: Double,
    val count: Int,
    val percentageOfLifetime: Float
)

data class SpendingOverview(
    val totalSpent: Double,
    val completedCount: Int,
    val refundedCount: Int,
    val activeSubscriptionsCount: Int,
    val monthlyRecurringEstimate: Double,
    val averagePurchasePrice: Double,
    val categorySpends: List<CategorySpend>,
    val monthlySpends: List<MonthlySpend>,
    val yearlySpends: List<YearlySpend>,
    val accountOriginYear: Int,
    val totalLifetimePurchasesCount: Int,
    val freeOrdersCount: Int,
    val paidOrdersCount: Int
)

class PurchaseViewModel(
    private val repository: PurchaseRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<PurchaseCategory?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortOption.DATE_DESC)
    val selectedSort = _selectedSort.asStateFlow()

    private val _timeRangeFilter = MutableStateFlow(TimeRangeFilter.ALL)
    val timeRangeFilter = _timeRangeFilter.asStateFlow()

    private val _priceTypeFilter = MutableStateFlow(PriceTypeFilter.ALL)
    val priceTypeFilter = _priceTypeFilter.asStateFlow()

    private val _selectedStatus = MutableStateFlow<PurchaseStatus?>(null)
    val selectedStatus = _selectedStatus.asStateFlow()

    private val _selectedCurrency = MutableStateFlow("$")
    val selectedCurrency = _selectedCurrency.asStateFlow()

    // Dialog & Detail states
    private val _selectedPurchaseForDetail = MutableStateFlow<PlayPurchase?>(null)
    val selectedPurchaseForDetail = _selectedPurchaseForDetail.asStateFlow()

    private val _showAddEditDialog = MutableStateFlow(false)
    val showAddEditDialog = _showAddEditDialog.asStateFlow()

    private val _purchaseToEdit = MutableStateFlow<PlayPurchase?>(null)
    val purchaseToEdit = _purchaseToEdit.asStateFlow()

    private val _showReceiptParser = MutableStateFlow(false)
    val showReceiptParser = _showReceiptParser.asStateFlow()

    private val _showExportSheet = MutableStateFlow(false)
    val showExportSheet = _showExportSheet.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage = _snackbarMessage.asStateFlow()

    private val allPurchasesFlow = repository.allPurchases

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    private data class FilterState(
        val query: String,
        val category: PurchaseCategory?,
        val sort: SortOption,
        val timeRange: TimeRangeFilter,
        val priceType: PriceTypeFilter,
        val status: PurchaseStatus?
    )

    private val criteriaFlow = combine(
        _searchQuery,
        _selectedCategory,
        _selectedSort
    ) { query, category, sort -> Triple(query, category, sort) }

    private val optionsFlow = combine(
        _timeRangeFilter,
        _priceTypeFilter,
        _selectedStatus
    ) { timeRange, priceType, status -> Triple(timeRange, priceType, status) }

    private val filterStateFlow = combine(
        criteriaFlow,
        optionsFlow
    ) { (query, category, sort), (timeRange, priceType, status) ->
        FilterState(query, category, sort, timeRange, priceType, status)
    }

    // Filtered and Sorted Purchases
    val filteredPurchases: StateFlow<List<PlayPurchase>> = combine(
        allPurchasesFlow,
        filterStateFlow
    ) { purchases, filter ->
        val now = System.currentTimeMillis()
        val cal = Calendar.getInstance()

        purchases.filter { p ->
            // Category filter
            val matchesCategory = filter.category == null || p.category == filter.category

            // Status filter
            val matchesStatus = filter.status == null || p.status == filter.status

            // Price type filter (All, Paid Only, Free $0.00 Only)
            val matchesPriceType = when (filter.priceType) {
                PriceTypeFilter.ALL -> true
                PriceTypeFilter.PAID_ONLY -> p.amount > 0.0
                PriceTypeFilter.FREE_ONLY -> p.amount == 0.0
            }

            // Comprehensive Search query filter (matching titles, dev, GPA, notes, and free/0/$0/trial)
            val matchesQuery = filter.query.isBlank() || run {
                val q = filter.query.trim()
                p.title.contains(q, ignoreCase = true) ||
                p.developer.contains(q, ignoreCase = true) ||
                p.orderId.contains(q, ignoreCase = true) ||
                p.notes.contains(q, ignoreCase = true) ||
                p.category.displayName.contains(q, ignoreCase = true) ||
                p.category.name.contains(q, ignoreCase = true) ||
                p.paymentMethod.contains(q, ignoreCase = true) ||
                (p.subscriptionPeriod != null && p.subscriptionPeriod.contains(q, ignoreCase = true)) ||
                p.status.label.contains(q, ignoreCase = true) ||
                (p.amount == 0.0 && (q.contains("free", ignoreCase = true) || q == "0" || q == "0.00" || q == "$0" || q == "$0.00" || q.contains("trial", ignoreCase = true) || q.contains("promo", ignoreCase = true))) ||
                String.format(Locale.US, "%.2f", p.amount).contains(q)
            }

            // Time range filter
            val matchesTime = when (filter.timeRange) {
                TimeRangeFilter.ALL -> true
                TimeRangeFilter.YEAR_2026 -> {
                    cal.timeInMillis = p.purchaseDate
                    cal.get(Calendar.YEAR) == 2026
                }
                TimeRangeFilter.YEAR_2025 -> {
                    cal.timeInMillis = p.purchaseDate
                    cal.get(Calendar.YEAR) == 2025
                }
                TimeRangeFilter.YEAR_2024 -> {
                    cal.timeInMillis = p.purchaseDate
                    cal.get(Calendar.YEAR) == 2024
                }
                TimeRangeFilter.YEAR_2023_PRIOR -> {
                    cal.timeInMillis = p.purchaseDate
                    cal.get(Calendar.YEAR) <= 2023
                }
                TimeRangeFilter.THIS_MONTH -> {
                    cal.timeInMillis = now
                    val currentMonth = cal.get(Calendar.MONTH)
                    val currentYear = cal.get(Calendar.YEAR)
                    cal.timeInMillis = p.purchaseDate
                    cal.get(Calendar.MONTH) == currentMonth && cal.get(Calendar.YEAR) == currentYear
                }
                TimeRangeFilter.LAST_30_DAYS -> {
                    val thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000)
                    p.purchaseDate >= thirtyDaysAgo
                }
            }

            matchesCategory && matchesStatus && matchesPriceType && matchesQuery && matchesTime
        }.let { filtered ->
            // Sorting
            when (filter.sort) {
                SortOption.DATE_DESC -> filtered.sortedByDescending { it.purchaseDate }
                SortOption.DATE_ASC -> filtered.sortedBy { it.purchaseDate }
                SortOption.AMOUNT_DESC -> filtered.sortedByDescending { it.amount }
                SortOption.AMOUNT_ASC -> filtered.sortedBy { it.amount }
                SortOption.TITLE_ASC -> filtered.sortedBy { it.title.lowercase(Locale.ROOT) }
                SortOption.CATEGORY -> filtered.sortedWith(compareBy({ it.category.name }, { -it.purchaseDate }))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Overall Spending Analytics calculated on all completed non-refunded purchases
    val spendingOverview: StateFlow<SpendingOverview> = allPurchasesFlow.combine(_selectedCurrency) { purchases, _ ->
        val completed = purchases.filter { it.status == PurchaseStatus.COMPLETED }
        val refunded = purchases.filter { it.status == PurchaseStatus.REFUNDED }
        val totalSpent = completed.sumOf { it.amount }

        // Subscriptions
        val activeSubs = completed.filter { it.isSubscription }
        val recurringBurn = activeSubs.sumOf { sub ->
            if (sub.subscriptionPeriod.equals("Annual", ignoreCase = true)) {
                sub.amount / 12.0
            } else {
                sub.amount
            }
        }

        // Category breakdown
        val catMap = completed.groupBy { it.category }
        val catSpends = PurchaseCategory.entries.map { cat ->
            val list = catMap[cat] ?: emptyList()
            val catTotal = list.sumOf { it.amount }
            val pct = if (totalSpent > 0) ((catTotal / totalSpent) * 100).toFloat() else 0f
            CategorySpend(
                category = cat,
                totalAmount = catTotal,
                count = list.size,
                percentage = pct
            )
        }.sortedByDescending { it.totalAmount }

        // Monthly Breakdown (Past 12 months)
        val keyFormat = SimpleDateFormat("yyyy-MM", Locale.US)
        val monthGroup = completed.groupBy { keyFormat.format(it.purchaseDate) }

        val monthlyList = mutableListOf<MonthlySpend>()
        for (i in 11 downTo 0) {
            val c = Calendar.getInstance()
            c.add(Calendar.MONTH, -i)
            val key = keyFormat.format(c.time)
            val label = SimpleDateFormat("MMM yy", Locale.US).format(c.time)
            val monthPurchases = monthGroup[key] ?: emptyList()
            monthlyList.add(
                MonthlySpend(
                    monthLabel = label,
                    yearMonthKey = key,
                    totalAmount = monthPurchases.sumOf { it.amount },
                    count = monthPurchases.size
                )
            )
        }

        // Yearly Breakdown across complete account history
        val yearGroup = completed.groupBy {
            val c = Calendar.getInstance()
            c.timeInMillis = it.purchaseDate
            c.get(Calendar.YEAR)
        }
        val sortedYears = yearGroup.keys.sortedDescending()
        val yearlyList = sortedYears.map { y ->
            val list = yearGroup[y] ?: emptyList()
            val yearTotal = list.sumOf { it.amount }
            val pct = if (totalSpent > 0) ((yearTotal / totalSpent) * 100).toFloat() else 0f
            YearlySpend(
                year = y,
                totalAmount = yearTotal,
                count = list.size,
                percentageOfLifetime = pct
            )
        }

        val originYear = if (completed.isNotEmpty()) {
            val c = Calendar.getInstance()
            c.timeInMillis = completed.minOf { it.purchaseDate }
            c.get(Calendar.YEAR)
        } else Calendar.getInstance().get(Calendar.YEAR)

        val freeOrders = completed.count { it.amount == 0.0 }
        val paidOrders = completed.count { it.amount > 0.0 }
        val avgPrice = if (paidOrders > 0) totalSpent / paidOrders else 0.0

        SpendingOverview(
            totalSpent = totalSpent,
            completedCount = completed.size,
            refundedCount = refunded.size,
            activeSubscriptionsCount = activeSubs.size,
            monthlyRecurringEstimate = recurringBurn,
            averagePurchasePrice = avgPrice,
            categorySpends = catSpends,
            monthlySpends = monthlyList,
            yearlySpends = yearlyList,
            accountOriginYear = originYear,
            totalLifetimePurchasesCount = purchases.size,
            freeOrdersCount = freeOrders,
            paidOrdersCount = paidOrders
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SpendingOverview(0.0, 0, 0, 0, 0.0, 0.0, emptyList(), emptyList(), emptyList(), 2021, 0, 0, 0)
    )

    // User Actions
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: PurchaseCategory?) {
        _selectedCategory.value = category
    }

    fun selectSort(sort: SortOption) {
        _selectedSort.value = sort
    }

    fun setTimeRange(range: TimeRangeFilter) {
        _timeRangeFilter.value = range
    }

    fun setPriceType(type: PriceTypeFilter) {
        _priceTypeFilter.value = type
    }

    fun setStatusFilter(status: PurchaseStatus?) {
        _selectedStatus.value = status
    }

    fun setCurrency(currency: String) {
        _selectedCurrency.value = currency
    }

    fun openPurchaseDetail(purchase: PlayPurchase) {
        _selectedPurchaseForDetail.value = purchase
    }

    fun closePurchaseDetail() {
        _selectedPurchaseForDetail.value = null
    }

    fun openAddPurchaseDialog() {
        _purchaseToEdit.value = null
        _showAddEditDialog.value = true
    }

    fun openEditPurchaseDialog(purchase: PlayPurchase) {
        _purchaseToEdit.value = purchase
        _showAddEditDialog.value = true
    }

    fun closeAddEditDialog() {
        _showAddEditDialog.value = false
        _purchaseToEdit.value = null
    }

    fun openReceiptParser() {
        _showReceiptParser.value = true
    }

    fun closeReceiptParser() {
        _showReceiptParser.value = false
    }

    fun openExportSheet() {
        _showExportSheet.value = true
    }

    fun closeExportSheet() {
        _showExportSheet.value = false
    }

    fun dismissSnackbar() {
        _snackbarMessage.value = null
    }

    fun savePurchase(purchase: PlayPurchase) {
        viewModelScope.launch {
            if (purchase.id == 0L) {
                repository.insertPurchase(purchase)
                _snackbarMessage.value = "Added purchase: ${purchase.title}"
            } else {
                repository.updatePurchase(purchase)
                _snackbarMessage.value = "Updated: ${purchase.title}"
                if (_selectedPurchaseForDetail.value?.id == purchase.id) {
                    _selectedPurchaseForDetail.value = purchase
                }
            }
            closeAddEditDialog()
        }
    }

    fun deletePurchase(purchase: PlayPurchase) {
        viewModelScope.launch {
            repository.deletePurchase(purchase)
            _snackbarMessage.value = "Deleted purchase: ${purchase.title}"
            closePurchaseDetail()
        }
    }

    fun parseAndAddReceipt(rawReceiptText: String): Boolean {
        val parsed = repository.parseReceiptText(rawReceiptText)
        if (parsed != null) {
            viewModelScope.launch {
                repository.insertPurchase(parsed)
                _snackbarMessage.value = "Parsed and logged: ${parsed.title}"
                closeReceiptParser()
            }
            return true
        } else {
            _snackbarMessage.value = "Could not parse purchase details from text"
            return false
        }
    }

    fun resetToDemoData() {
        viewModelScope.launch {
            repository.resetToSampleData()
            _snackbarMessage.value = "Reset to sample Google Play purchases"
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAll()
            _snackbarMessage.value = "Cleared all purchases"
        }
    }

    fun generateNewOrderId(): String = repository.generateOrderId()

    fun getExportCsv(): String {
        return repository.exportToCsv(filteredPurchases.value)
    }

    class Factory(private val repository: PurchaseRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PurchaseViewModel::class.java)) {
                return PurchaseViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
