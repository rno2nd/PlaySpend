package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.PlayPurchase
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import com.example.ui.components.AddEditPurchaseDialog
import com.example.ui.components.CategoryBreakdownCard
import com.example.ui.components.ExportImportDialog
import com.example.ui.components.LifetimeAccountCard
import com.example.ui.components.MonthlySpendingChart
import com.example.ui.components.PasteReceiptDialog
import com.example.ui.components.PlaySearchBar
import com.example.ui.components.PurchaseDetailBottomSheet
import com.example.ui.components.PurchaseItemCard
import com.example.ui.components.SortAndFilterBar
import com.example.ui.components.SpendingSummaryCard
import com.example.ui.viewmodel.PurchaseViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: PurchaseViewModel,
    modifier: Modifier = Modifier
) {
    val filteredPurchases by viewModel.filteredPurchases.collectAsStateWithLifecycle()
    val spendingOverview by viewModel.spendingOverview.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedSort by viewModel.selectedSort.collectAsStateWithLifecycle()
    val timeRange by viewModel.timeRangeFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val currency by viewModel.selectedCurrency.collectAsStateWithLifecycle()
    val priceType by viewModel.priceTypeFilter.collectAsStateWithLifecycle()

    val selectedDetail by viewModel.selectedPurchaseForDetail.collectAsStateWithLifecycle()
    val showAddEditDialog by viewModel.showAddEditDialog.collectAsStateWithLifecycle()
    val purchaseToEdit by viewModel.purchaseToEdit.collectAsStateWithLifecycle()
    val showReceiptParser by viewModel.showReceiptParser.collectAsStateWithLifecycle()
    val showExportSheet by viewModel.showExportSheet.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    var currentTab by remember { mutableIntStateOf(0) }
    var menuExpanded by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissSnackbar()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingBag,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "PlaySpend",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                currentTab = 0
                                if (searchQuery.isNotEmpty()) {
                                    viewModel.setSearchQuery("")
                                }
                            },
                            modifier = Modifier.testTag("toggle_search_button")
                        ) {
                            Icon(
                                imageVector = if (searchQuery.isNotEmpty()) Icons.Default.Clear else Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }

                        IconButton(
                            onClick = { viewModel.openReceiptParser() },
                            modifier = Modifier.testTag("open_receipt_parser_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PostAdd,
                                contentDescription = "Import Receipt",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Box {
                            IconButton(
                                onClick = { menuExpanded = true },
                                modifier = Modifier.testTag("top_menu_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More Options"
                                )
                            }

                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Export & Settings") },
                                    onClick = {
                                        menuExpanded = false
                                        viewModel.openExportSheet()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Paste Play Receipt") },
                                    onClick = {
                                        menuExpanded = false
                                        viewModel.openReceiptParser()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Reset Sample Data") },
                                    onClick = {
                                        menuExpanded = false
                                        viewModel.resetToDemoData()
                                    }
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("main_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Default.ReceiptLong, contentDescription = "Purchases") },
                    label = { Text("Purchases") },
                    modifier = Modifier.testTag("tab_purchases")
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = "Overview") },
                    label = { Text("Analytics") },
                    modifier = Modifier.testTag("tab_analytics")
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Default.Autorenew, contentDescription = "Subscriptions") },
                    label = { Text("Subscriptions") },
                    modifier = Modifier.testTag("tab_subscriptions")
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openAddPurchaseDialog() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag("fab_add_purchase")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Purchase")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentTab) {
                0 -> PurchasesTabContent(
                    purchases = filteredPurchases,
                    currency = currency,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.setSearchQuery(it) },
                    selectedCategory = selectedCategory,
                    onSelectCategory = { viewModel.selectCategory(it) },
                    selectedSort = selectedSort,
                    onSelectSort = { viewModel.selectSort(it) },
                    timeRange = timeRange,
                    onSelectTimeRange = { viewModel.setTimeRange(it) },
                    priceType = priceType,
                    onSelectPriceType = { viewModel.setPriceType(it) },
                    onPurchaseClick = { viewModel.openPurchaseDetail(it) }
                )
                1 -> AnalyticsTabContent(
                    overview = spendingOverview,
                    currency = currency,
                    selectedCategory = selectedCategory,
                    onCategoryClick = {
                        viewModel.selectCategory(it)
                        currentTab = 0 // Switch to purchases list filtered
                    },
                    onYearSelect = { year ->
                        when (year) {
                            2026 -> viewModel.setTimeRange(com.example.data.model.TimeRangeFilter.YEAR_2026)
                            2025 -> viewModel.setTimeRange(com.example.data.model.TimeRangeFilter.YEAR_2025)
                            2024 -> viewModel.setTimeRange(com.example.data.model.TimeRangeFilter.YEAR_2024)
                            else -> viewModel.setTimeRange(com.example.data.model.TimeRangeFilter.YEAR_2023_PRIOR)
                        }
                        currentTab = 0
                    }
                )
                2 -> SubscriptionsTabContent(
                    overview = spendingOverview,
                    purchases = filteredPurchases.filter { it.isSubscription },
                    currency = currency,
                    onPurchaseClick = { viewModel.openPurchaseDetail(it) }
                )
            }
        }

        // Dialogs & Sheets
        selectedDetail?.let { purchase ->
            PurchaseDetailBottomSheet(
                purchase = purchase,
                currency = currency,
                onDismiss = { viewModel.closePurchaseDetail() },
                onEdit = {
                    viewModel.closePurchaseDetail()
                    viewModel.openEditPurchaseDialog(it)
                },
                onDelete = { viewModel.deletePurchase(it) }
            )
        }

        if (showAddEditDialog) {
            AddEditPurchaseDialog(
                initialPurchase = purchaseToEdit,
                currency = currency,
                onGenerateOrderId = { viewModel.generateNewOrderId() },
                onSave = { viewModel.savePurchase(it) },
                onDismiss = { viewModel.closeAddEditDialog() }
            )
        }

        if (showReceiptParser) {
            PasteReceiptDialog(
                onParseAndAdd = { viewModel.parseAndAddReceipt(it) },
                onDismiss = { viewModel.closeReceiptParser() }
            )
        }

        if (showExportSheet) {
            ExportImportDialog(
                exportCsvText = viewModel.getExportCsv(),
                currentCurrency = currency,
                onCurrencyChange = { viewModel.setCurrency(it) },
                onResetDemoData = { viewModel.resetToDemoData() },
                onClearAll = { viewModel.clearAllData() },
                onDismiss = { viewModel.closeExportSheet() }
            )
        }
    }
}

@Composable
private fun PurchasesTabContent(
    purchases: List<PlayPurchase>,
    currency: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: com.example.data.model.PurchaseCategory?,
    onSelectCategory: (com.example.data.model.PurchaseCategory?) -> Unit,
    selectedSort: com.example.data.model.SortOption,
    onSelectSort: (com.example.data.model.SortOption) -> Unit,
    timeRange: com.example.data.model.TimeRangeFilter,
    onSelectTimeRange: (com.example.data.model.TimeRangeFilter) -> Unit,
    priceType: com.example.data.model.PriceTypeFilter,
    onSelectPriceType: (com.example.data.model.PriceTypeFilter) -> Unit,
    onPurchaseClick: (PlayPurchase) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("purchases_list"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Prominent Play Store styled Search Bar
        item {
            PlaySearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Sort & Filter Controls Header
        item {
            SortAndFilterBar(
                selectedCategory = selectedCategory,
                onSelectCategory = onSelectCategory,
                selectedSort = selectedSort,
                onSelectSort = onSelectSort,
                timeRange = timeRange,
                onSelectTimeRange = onSelectTimeRange,
                priceType = priceType,
                onSelectPriceType = onSelectPriceType,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        // Active search filter notification chip
        if (searchQuery.isNotBlank()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Results for \"$searchQuery\"",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    TextButton(
                        onClick = { onSearchQueryChange("") },
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "Clear search",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Summary bar: count and subtotal of currently visible filtered list
        item {
            val totalFiltered = purchases.filter { it.status == com.example.data.model.PurchaseStatus.COMPLETED }.sumOf { it.amount }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${purchases.size} ${if (purchases.size == 1) "purchase" else "purchases"} found",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Subtotal: " + String.format(Locale.US, "%s%.2f", currency, totalFiltered),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // List items
        if (purchases.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchQuery.isNotBlank()) "No purchases match \"$searchQuery\"" else "No purchases match your filters",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (searchQuery.isNotBlank())
                                "Check your spelling or search by publisher, category, or order ID GPA."
                            else
                                "Try clearing filters or selecting another category or time range.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (searchQuery.isNotBlank()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { onSearchQueryChange("") },
                                modifier = Modifier.testTag("empty_state_clear_search_button")
                            ) {
                                Text("Clear Search")
                            }
                        }
                    }
                }
            }
        } else {
            items(purchases, key = { it.id }) { item ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    PurchaseItemCard(
                        purchase = item,
                        currency = currency,
                        onClick = { onPurchaseClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnalyticsTabContent(
    overview: com.example.ui.viewmodel.SpendingOverview,
    currency: String,
    selectedCategory: com.example.data.model.PurchaseCategory?,
    onCategoryClick: (com.example.data.model.PurchaseCategory?) -> Unit,
    onYearSelect: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("analytics_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SpendingSummaryCard(
                overview = overview,
                currency = currency
            )
        }

        item {
            LifetimeAccountCard(
                overview = overview,
                currency = currency,
                onYearSelect = onYearSelect
            )
        }

        item {
            MonthlySpendingChart(
                monthlySpends = overview.monthlySpends,
                currency = currency
            )
        }

        item {
            CategoryBreakdownCard(
                categorySpends = overview.categorySpends,
                currency = currency,
                selectedCategory = selectedCategory,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
private fun SubscriptionsTabContent(
    overview: com.example.ui.viewmodel.SpendingOverview,
    purchases: List<PlayPurchase>,
    currency: String,
    onPurchaseClick: (PlayPurchase) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("subscriptions_list"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Subscription Burn Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Recurring Monthly Cost",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${overview.activeSubscriptionsCount} Active",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = String.format(Locale.US, "%s%.2f / month", currency, overview.monthlyRecurringEstimate),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estimated recurring charges from active Play subscriptions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your Subscriptions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (purchases.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No active Play Store subscriptions found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(purchases, key = { it.id }) { item ->
                PurchaseItemCard(
                    purchase = item,
                    currency = currency,
                    onClick = { onPurchaseClick(item) }
                )
            }
        }
    }
}
