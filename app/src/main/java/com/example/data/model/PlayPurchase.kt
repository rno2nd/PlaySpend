package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ui.theme.CategoryAppColor
import com.example.ui.theme.CategoryBookColor
import com.example.ui.theme.CategoryGameColor
import com.example.ui.theme.CategoryInAppColor
import com.example.ui.theme.CategoryMovieColor
import com.example.ui.theme.CategorySubscriptionColor

enum class PurchaseCategory(val displayName: String) {
    SUBSCRIPTION("Subscriptions"),
    GAME("Games"),
    IN_APP("In-App Purchases"),
    APP("Apps & Tools"),
    MOVIE("Movies & TV"),
    BOOK("Books & Audio");

    val icon: ImageVector
        get() = when (this) {
            SUBSCRIPTION -> Icons.Default.Refresh
            GAME -> Icons.Default.SportsEsports
            IN_APP -> Icons.Default.Paid
            APP -> Icons.Default.Apps
            MOVIE -> Icons.Default.Movie
            BOOK -> Icons.Default.Book
        }

    val badgeColor: Color
        get() = when (this) {
            SUBSCRIPTION -> CategorySubscriptionColor
            GAME -> CategoryGameColor
            IN_APP -> CategoryInAppColor
            APP -> CategoryAppColor
            MOVIE -> CategoryMovieColor
            BOOK -> CategoryBookColor
        }
}

enum class PurchaseStatus(val label: String) {
    COMPLETED("Completed"),
    REFUNDED("Refunded"),
    CANCELLED("Cancelled")
}

enum class SortOption(val title: String) {
    DATE_DESC("Newest first"),
    DATE_ASC("Oldest first"),
    AMOUNT_DESC("Highest amount"),
    AMOUNT_ASC("Lowest amount"),
    TITLE_ASC("Name (A-Z)"),
    CATEGORY("Category")
}

enum class TimeRangeFilter(val label: String) {
    ALL("All Time"),
    YEAR_2026("2026"),
    YEAR_2025("2025"),
    YEAR_2024("2024"),
    YEAR_2023_PRIOR("2023 & Prior"),
    THIS_MONTH("This Month"),
    LAST_30_DAYS("Last 30 Days")
}

enum class PriceTypeFilter(val label: String) {
    ALL("All (Paid & Free)"),
    PAID_ONLY("Paid Only"),
    FREE_ONLY("Free ($0.00)")
}

@Entity(tableName = "purchases")
data class PlayPurchase(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: String,
    val title: String,
    val developer: String,
    val category: PurchaseCategory,
    val amount: Double,
    val currency: String = "$",
    val purchaseDate: Long,
    val paymentMethod: String = "Google Pay",
    val status: PurchaseStatus = PurchaseStatus.COMPLETED,
    val isSubscription: Boolean = false,
    val subscriptionPeriod: String? = null,
    val notes: String = ""
)
