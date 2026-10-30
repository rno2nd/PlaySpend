package com.example.data.repository

import com.example.data.local.PurchaseDao
import com.example.data.model.PlayPurchase
import com.example.data.model.PurchaseCategory
import com.example.data.model.PurchaseStatus
import com.example.data.sample.SamplePurchases
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

class PurchaseRepository(private val purchaseDao: PurchaseDao) {

    val allPurchases: Flow<List<PlayPurchase>> = purchaseDao.getAllPurchases()

    suspend fun insertPurchase(purchase: PlayPurchase): Long {
        return purchaseDao.insert(purchase)
    }

    suspend fun updatePurchase(purchase: PlayPurchase) {
        purchaseDao.update(purchase)
    }

    suspend fun deletePurchase(purchase: PlayPurchase) {
        purchaseDao.delete(purchase)
    }

    suspend fun deleteById(id: Long) {
        purchaseDao.deleteById(id)
    }

    suspend fun resetToSampleData() {
        purchaseDao.clearAll()
        purchaseDao.insertAll(SamplePurchases.getInitialPurchases())
    }

    suspend fun clearAll() {
        purchaseDao.clearAll()
    }

    suspend fun ensureInitialized() {
        if (purchaseDao.getCount() < 40) {
            purchaseDao.clearAll()
            purchaseDao.insertAll(SamplePurchases.getInitialPurchases())
        }
    }

    fun generateOrderId(): String {
        val random = Random()
        val p1 = 3000 + random.nextInt(900)
        val p2 = 1000 + random.nextInt(9000)
        val p3 = 1000 + random.nextInt(9000)
        val p4 = 10000 + random.nextInt(90000)
        return "GPA.$p1-$p2-$p3-$p4"
    }

    /**
     * Parse text pasted from a Google Play confirmation email or receipt.
     * Looks for Order number (GPA.xxxx...), Item/Title, Price/Amount, etc.
     */
    fun parseReceiptText(text: String): PlayPurchase? {
        if (text.isBlank()) return null

        // Try to find GPA order number
        val gpaRegex = Regex("""(GPA\.\d{4}-\d{4}-\d{4}-\d{5})""", RegexOption.IGNORE_CASE)
        val orderMatch = gpaRegex.find(text)
        val orderId = orderMatch?.value?.uppercase() ?: generateOrderId()

        // Try to find price e.g. $14.99 or 14.99 USD
        val priceRegex = Regex("""([$€£¥₹])\s*([0-9]+[.,][0-9]{2})|([0-9]+[.,][0-9]{2})\s*([$€£¥₹]|USD|EUR|GBP)""")
        val priceMatch = priceRegex.find(text)
        var currency = "$"
        var amount = 4.99

        if (priceMatch != null) {
            val matchStr = priceMatch.value
            val numRegex = Regex("""[0-9]+[.,][0-9]{2}""")
            val numMatch = numRegex.find(matchStr)
            if (numMatch != null) {
                amount = numMatch.value.replace(',', '.').toDoubleOrNull() ?: 4.99
            }
            if (matchStr.contains("€")) currency = "€"
            else if (matchStr.contains("£")) currency = "£"
            else if (matchStr.contains("¥")) currency = "¥"
            else if (matchStr.contains("₹")) currency = "₹"
        }

        // Try to find item name / title
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        var detectedTitle = ""
        var detectedDev = "Google Play Developer"

        for (line in lines) {
            if (line.startsWith("Item:", ignoreCase = true) || line.startsWith("Order item:", ignoreCase = true)) {
                detectedTitle = line.substringAfter(":").trim()
                break
            } else if (line.startsWith("Title:", ignoreCase = true)) {
                detectedTitle = line.substringAfter(":").trim()
                break
            }
        }

        if (detectedTitle.isBlank()) {
            // Find the first line that is not metadata
            val candidate = lines.firstOrNull { l ->
                !l.contains("GPA.", ignoreCase = true) &&
                !l.contains("Google Play", ignoreCase = true) &&
                !l.contains("Order date", ignoreCase = true) &&
                !l.contains("Total:", ignoreCase = true) &&
                !l.contains("Price:", ignoreCase = true) &&
                l.length > 2
            }
            detectedTitle = candidate ?: "Google Play Purchase"
        }

        // Detect category
        val lower = text.lowercase()
        val category = when {
            lower.contains("subscription") || lower.contains("monthly") || lower.contains("renew") -> PurchaseCategory.SUBSCRIPTION
            lower.contains("movie") || lower.contains("film") || lower.contains("4k") || lower.contains("hd") -> PurchaseCategory.MOVIE
            lower.contains("book") || lower.contains("audiobook") || lower.contains("ebook") -> PurchaseCategory.BOOK
            lower.contains("gems") || lower.contains("coins") || lower.contains("pass") || lower.contains("pack") || lower.contains("in-app") -> PurchaseCategory.IN_APP
            lower.contains("game") -> PurchaseCategory.GAME
            else -> PurchaseCategory.APP
        }

        val isSub = category == PurchaseCategory.SUBSCRIPTION

        return PlayPurchase(
            orderId = orderId,
            title = detectedTitle.take(60),
            developer = detectedDev,
            category = category,
            amount = amount,
            currency = currency,
            purchaseDate = System.currentTimeMillis(),
            paymentMethod = "Google Pay",
            status = PurchaseStatus.COMPLETED,
            isSubscription = isSub,
            subscriptionPeriod = if (isSub) "Monthly" else null,
            notes = "Parsed from receipt text"
        )
    }

    fun exportToCsv(purchases: List<PlayPurchase>): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val sb = StringBuilder()
        sb.append("Order ID,Title,Developer,Category,Amount,Currency,Date,Status,Payment Method,Is Subscription\n")
        purchases.forEach { p ->
            val cleanTitle = p.title.replace("\"", "\"\"")
            val cleanDev = p.developer.replace("\"", "\"\"")
            val dateStr = dateFormat.format(p.purchaseDate)
            sb.append("\"${p.orderId}\",\"$cleanTitle\",\"$cleanDev\",\"${p.category.name}\",${p.amount},\"${p.currency}\",\"$dateStr\",\"${p.status.name}\",\"${p.paymentMethod}\",${p.isSubscription}\n")
        }
        return sb.toString()
    }
}
