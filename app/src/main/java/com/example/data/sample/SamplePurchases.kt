package com.example.data.sample

import com.example.data.model.PlayPurchase
import com.example.data.model.PurchaseCategory
import com.example.data.model.PurchaseStatus
import java.util.Calendar

object SamplePurchases {

    private fun time(year: Int, month: Int, day: Int, hour: Int = 14, minute: Int = 20): Long {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day, hour, minute, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun getInitialPurchases(): List<PlayPurchase> {
        return listOf(
            // ==========================================
            // 2026 PURCHASES & $0.00 FREE CLAIMS
            // ==========================================
            PlayPurchase(
                orderId = "GPA.3382-7491-0192-38472",
                title = "YouTube Premium (Individual)",
                developer = "Google LLC",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 13.99,
                purchaseDate = time(2026, 10, 27, 9, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "Monthly",
                notes = "Ad-free videos, background play, and YouTube Music"
            ),
            PlayPurchase(
                orderId = "GPA.3370-1192-4482-90123",
                title = "YouTube Premium (1-Month Free Trial)",
                developer = "Google LLC",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 0.00,
                purchaseDate = time(2026, 10, 26, 8, 30),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "1-Month Free Trial",
                notes = "Introductory free trial period ($0.00 charged)"
            ),
            PlayPurchase(
                orderId = "GPA.3318-9942-1748-55829",
                title = "Marvel Snap - Season Pass",
                developer = "Nuverse",
                category = PurchaseCategory.IN_APP,
                amount = 9.99,
                purchaseDate = time(2026, 10, 24, 18, 42),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Symbiote Invasion Premium Pass"
            ),
            PlayPurchase(
                orderId = "GPA.3308-4419-8821-33104",
                title = "Google One 100 GB (3-Month Promo Trial)",
                developer = "Google LLC",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 0.00,
                purchaseDate = time(2026, 10, 16, 14, 10),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "3-Month Free Trial",
                notes = "Pixel Device Promotion - 3 months at $0.00"
            ),
            PlayPurchase(
                orderId = "GPA.3304-1824-7731-90241",
                title = "Google One 100 GB Cloud Storage",
                developer = "Google LLC",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 1.99,
                purchaseDate = time(2026, 10, 15, 12, 0),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "Monthly",
                notes = "Backup for Photos and Drive"
            ),
            PlayPurchase(
                orderId = "GPA.3299-4412-8823-11943",
                title = "Dune: Part Two (4K UHD)",
                developer = "Warner Bros. Entertainment",
                category = PurchaseCategory.MOVIE,
                amount = 19.99,
                purchaseDate = time(2026, 10, 10, 20, 30),
                paymentMethod = "PayPal",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Digital Movie Purchase with HDR10+"
            ),
            PlayPurchase(
                orderId = "GPA.3287-6523-9014-34512",
                title = "Minecraft",
                developer = "Mojang",
                category = PurchaseCategory.GAME,
                amount = 6.99,
                purchaseDate = time(2026, 9, 28, 15, 11),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Full game license"
            ),
            PlayPurchase(
                orderId = "GPA.3274-1184-5542-88192",
                title = "Genshin Impact - Blessing of the Welkin Moon",
                developer = "Cognosphere PTE. LTD.",
                category = PurchaseCategory.IN_APP,
                amount = 4.99,
                purchaseDate = time(2026, 9, 15, 11, 4),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "30-day daily Primogems supply"
            ),
            PlayPurchase(
                orderId = "GPA.3268-9921-3310-44821",
                title = "Sky: Children of the Light - Starter Pack Promo",
                developer = "thatgamecompany",
                category = PurchaseCategory.IN_APP,
                amount = 0.00,
                purchaseDate = time(2026, 9, 2, 16, 20),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Free promotional welcome gift ($0.00 invoice)"
            ),
            PlayPurchase(
                orderId = "GPA.3265-8831-2390-12847",
                title = "Project Hail Mary (Unabridged)",
                developer = "Audible / Andy Weir",
                category = PurchaseCategory.BOOK,
                amount = 14.99,
                purchaseDate = time(2026, 8, 22, 17, 35),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Narrated by Ray Porter"
            ),
            PlayPurchase(
                orderId = "GPA.3251-4091-6623-74190",
                title = "Duolingo Super (Annual Plan)",
                developer = "Duolingo",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 83.99,
                purchaseDate = time(2026, 8, 2, 10, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "Annual",
                notes = "Unlimited Hearts, Mistake Review, No ads"
            ),
            PlayPurchase(
                orderId = "GPA.3240-7712-3341-99521",
                title = "Nova Launcher Prime",
                developer = "Nova Launcher",
                category = PurchaseCategory.APP,
                amount = 4.99,
                purchaseDate = time(2026, 7, 19, 16, 50),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Prime license unlock for gestures and custom drawer"
            ),
            PlayPurchase(
                orderId = "GPA.3231-1928-4482-60193",
                title = "Stardew Valley",
                developer = "ConcernedApe",
                category = PurchaseCategory.GAME,
                amount = 4.99,
                purchaseDate = time(2026, 6, 12, 21, 5),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Relaxing farming RPG"
            ),
            PlayPurchase(
                orderId = "GPA.3220-6641-8834-21950",
                title = "Subway Surfers - 500 Keys Bundle",
                developer = "SYBO Games",
                category = PurchaseCategory.IN_APP,
                amount = 4.99,
                purchaseDate = time(2026, 5, 20, 13, 20),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.REFUNDED,
                isSubscription = false,
                notes = "Accidental purchase - Refund granted within 48h"
            ),
            PlayPurchase(
                orderId = "GPA.3211-5509-2218-77341",
                title = "Spider-Man: Across the Spider-Verse",
                developer = "Sony Pictures",
                category = PurchaseCategory.MOVIE,
                amount = 14.99,
                purchaseDate = time(2026, 4, 14, 19, 45),
                paymentMethod = "PayPal",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Digital 4K UHD purchase"
            ),
            PlayPurchase(
                orderId = "GPA.3198-3321-7764-90234",
                title = "Tasker",
                developer = "joaomgcd",
                category = PurchaseCategory.APP,
                amount = 3.49,
                purchaseDate = time(2026, 3, 5, 11, 10),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Total automation for Android"
            ),
            PlayPurchase(
                orderId = "GPA.3184-9912-4431-88512",
                title = "Atomic Habits: An Easy & Proven Way",
                developer = "James Clear / Penguin",
                category = PurchaseCategory.BOOK,
                amount = 11.99,
                purchaseDate = time(2026, 2, 18, 14, 30),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "eBook on Google Play Books"
            ),
            PlayPurchase(
                orderId = "GPA.3172-8841-3329-11029",
                title = "Dead Cells",
                developer = "Playdigious",
                category = PurchaseCategory.GAME,
                amount = 8.99,
                purchaseDate = time(2026, 1, 25, 22, 10),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Rogue-lite action platformer"
            ),

            // ==========================================
            // 2025 PURCHASES & $0.00 FREE PROMOS
            // ==========================================
            PlayPurchase(
                orderId = "GPA.3162-8819-4412-77590",
                title = "Monument Valley - Free 10th Anniversary Promo",
                developer = "ustwo games",
                category = PurchaseCategory.GAME,
                amount = 0.00,
                purchaseDate = time(2025, 12, 24, 11, 0),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "100% discount holiday promo claimed ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.3150-4419-7721-00213",
                title = "Disney+ Monthly Subscription",
                developer = "Disney",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 13.99,
                purchaseDate = time(2025, 12, 15, 10, 0),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "Monthly",
                notes = "Star Wars & Marvel holiday streaming"
            ),
            PlayPurchase(
                orderId = "GPA.3142-6612-8840-33215",
                title = "WolframAlpha",
                developer = "Wolfram Group",
                category = PurchaseCategory.APP,
                amount = 2.99,
                purchaseDate = time(2025, 11, 20, 16, 40),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Computational mathematics engine"
            ),
            PlayPurchase(
                orderId = "GPA.3135-1123-9944-55612",
                title = "Brawl Stars - 170 Gems Pack",
                developer = "Supercell",
                category = PurchaseCategory.IN_APP,
                amount = 9.99,
                purchaseDate = time(2025, 10, 31, 18, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Halloween Season Brawl Pass unlock"
            ),
            PlayPurchase(
                orderId = "GPA.3128-4491-7720-11823",
                title = "Infinity Loop: Premium Edition ($0.00 Promo)",
                developer = "Webelinx OOD",
                category = PurchaseCategory.GAME,
                amount = 0.00,
                purchaseDate = time(2025, 10, 5, 13, 20),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "App of the Week 100% off promotional sale ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.3120-7741-2290-66418",
                title = "Monument Valley 2",
                developer = "ustwo games",
                category = PurchaseCategory.GAME,
                amount = 3.99,
                purchaseDate = time(2025, 9, 14, 21, 30),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Architectural illusion puzzle sequel"
            ),
            PlayPurchase(
                orderId = "GPA.3110-3392-1184-77519",
                title = "Oppenheimer (4K UHD)",
                developer = "Universal Studios",
                category = PurchaseCategory.MOVIE,
                amount = 19.99,
                purchaseDate = time(2025, 8, 25, 20, 15),
                paymentMethod = "PayPal",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Christopher Nolan cinematic masterpiece"
            ),
            PlayPurchase(
                orderId = "GPA.3102-6619-3342-88190",
                title = "Adobe Lightroom - Free Starter License",
                developer = "Adobe",
                category = PurchaseCategory.APP,
                amount = 0.00,
                purchaseDate = time(2025, 7, 28, 10, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Free registered mobile editing license ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.3098-4421-9983-11204",
                title = "Clash of Clans - Gold Pass",
                developer = "Supercell",
                category = PurchaseCategory.IN_APP,
                amount = 6.99,
                purchaseDate = time(2025, 7, 5, 12, 10),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Season perks and hero skin"
            ),
            PlayPurchase(
                orderId = "GPA.3085-6612-3349-88710",
                title = "Sapiens: A Brief History of Humankind",
                developer = "Yuval Noah Harari / Harper",
                category = PurchaseCategory.BOOK,
                amount = 13.99,
                purchaseDate = time(2025, 5, 18, 15, 20),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Non-fiction eBook"
            ),
            PlayPurchase(
                orderId = "GPA.3072-1190-4482-55319",
                title = "Terraria",
                developer = "505 Games Srl",
                category = PurchaseCategory.GAME,
                amount = 4.99,
                purchaseDate = time(2025, 3, 29, 17, 45),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "2D action-adventure sandbox"
            ),
            PlayPurchase(
                orderId = "GPA.3060-8823-7711-22940",
                title = "Sleep as Android: Smart Alarm Unlock",
                developer = "Urbandroid",
                category = PurchaseCategory.APP,
                amount = 9.99,
                purchaseDate = time(2025, 2, 10, 8, 30),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Lifetime sleep tracking unlock"
            ),

            // ==========================================
            // 2024 PURCHASES & $0.00 FREE PROMOS
            // ==========================================
            PlayPurchase(
                orderId = "GPA.3045-3312-9984-11029",
                title = "Spotify Premium Individual",
                developer = "Spotify AB",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 10.99,
                purchaseDate = time(2024, 11, 10, 10, 0),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "Monthly",
                notes = "Monthly music streaming"
            ),
            PlayPurchase(
                orderId = "GPA.3038-7712-4419-88302",
                title = "The Fast and the Furious - Play Free Movie Gift",
                developer = "Universal Studios",
                category = PurchaseCategory.MOVIE,
                amount = 0.00,
                purchaseDate = time(2024, 10, 18, 20, 30),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Google Play Anniversary promotional free movie gift ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.3032-7719-2248-66190",
                title = "Bloons TD 6",
                developer = "ninja kiwi",
                category = PurchaseCategory.GAME,
                amount = 6.99,
                purchaseDate = time(2024, 9, 21, 14, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Tower defense classic"
            ),
            PlayPurchase(
                orderId = "GPA.3021-9984-1132-77402",
                title = "Interstellar (4K UHD)",
                developer = "Paramount Pictures",
                category = PurchaseCategory.MOVIE,
                amount = 14.99,
                purchaseDate = time(2024, 8, 14, 21, 0),
                paymentMethod = "PayPal",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Sci-fi epic in Dolby Vision"
            ),
            PlayPurchase(
                orderId = "GPA.3015-8821-3390-11248",
                title = "Alice's Adventures in Wonderland (Classic eBook)",
                developer = "Lewis Carroll / Public Domain",
                category = PurchaseCategory.BOOK,
                amount = 0.00,
                purchaseDate = time(2024, 8, 1, 11, 0),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Google Play Books Free Classics library edition ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.3010-4423-8891-33215",
                title = "Pokémon GO - 1,200 PokéCoins",
                developer = "Niantic, Inc.",
                category = PurchaseCategory.IN_APP,
                amount = 9.99,
                purchaseDate = time(2024, 7, 13, 13, 40),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "GO Fest 2024 Global raid passes bundle"
            ),
            PlayPurchase(
                orderId = "GPA.2995-1182-4439-00128",
                title = "The Hobbit (eBook)",
                developer = "J.R.R. Tolkien / HarperCollins",
                category = PurchaseCategory.BOOK,
                amount = 9.99,
                purchaseDate = time(2024, 5, 4, 16, 25),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Illustrated edition"
            ),
            PlayPurchase(
                orderId = "GPA.2980-6641-7712-33901",
                title = "Poweramp Full Version Unlocker",
                developer = "Max MP",
                category = PurchaseCategory.APP,
                amount = 5.99,
                purchaseDate = time(2024, 3, 17, 19, 10),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "High-res audiophile player unlock"
            ),
            PlayPurchase(
                orderId = "GPA.2965-8823-1194-55420",
                title = "Grand Theft Auto: San Andreas",
                developer = "Rockstar Games",
                category = PurchaseCategory.GAME,
                amount = 6.99,
                purchaseDate = time(2024, 1, 9, 23, 15),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Definitive mobile port"
            ),

            // ==========================================
            // 2023 PURCHASES & $0.00 FREE PROMOS
            // ==========================================
            PlayPurchase(
                orderId = "GPA.2940-3319-8874-22019",
                title = "Solid Explorer File Manager (Full)",
                developer = "NeatBytes",
                category = PurchaseCategory.APP,
                amount = 2.99,
                purchaseDate = time(2023, 11, 28, 14, 50),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Dual-pane material file manager license"
            ),
            PlayPurchase(
                orderId = "GPA.2932-5519-7740-11209",
                title = "Prune - Indie Games Festival Free Claim",
                developer = "Joel McDonald",
                category = PurchaseCategory.GAME,
                amount = 0.00,
                purchaseDate = time(2023, 10, 12, 17, 30),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Google Play Indie Corner celebration 100% off sale ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.2925-7781-4412-99304",
                title = "Plague Inc.",
                developer = "Miniclip.com",
                category = PurchaseCategory.GAME,
                amount = 0.99,
                purchaseDate = time(2023, 9, 19, 18, 30),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Global simulation strategy game"
            ),
            PlayPurchase(
                orderId = "GPA.2918-4491-2210-88391",
                title = "Duolingo Plus (14-Day Free Trial)",
                developer = "Duolingo",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 0.00,
                purchaseDate = time(2023, 8, 15, 10, 0),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "14-Day Free Trial",
                notes = "Free trial invoice with $0.00 initial charge"
            ),
            PlayPurchase(
                orderId = "GPA.2910-1142-6639-88215",
                title = "Avengers: Endgame (4K)",
                developer = "Marvel Studios",
                category = PurchaseCategory.MOVIE,
                amount = 19.99,
                purchaseDate = time(2023, 7, 22, 21, 15),
                paymentMethod = "PayPal",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Marvel Cinematic Universe culmination"
            ),
            PlayPurchase(
                orderId = "GPA.2895-5590-2218-77401",
                title = "Clash Royale - Pass Royale",
                developer = "Supercell",
                category = PurchaseCategory.IN_APP,
                amount = 5.99,
                purchaseDate = time(2023, 5, 12, 12, 45),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Season tower skin & emotes"
            ),
            PlayPurchase(
                orderId = "GPA.2888-7712-4419-33201",
                title = "Pride and Prejudice (Complete Audiobook)",
                developer = "Jane Austen / LibriVox",
                category = PurchaseCategory.BOOK,
                amount = 0.00,
                purchaseDate = time(2023, 4, 10, 15, 0),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Promotional free classic audiobook ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.2880-9923-4410-66329",
                title = "The Witcher: The Last Wish",
                developer = "Andrzej Sapkowski / Orbit",
                category = PurchaseCategory.BOOK,
                amount = 8.99,
                purchaseDate = time(2023, 3, 3, 16, 20),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Geralt of Rivia fantasy book"
            ),

            // ==========================================
            // 2022 & EARLIER ACCOUNT ORIGIN (Paid & $0.00)
            // ==========================================
            PlayPurchase(
                orderId = "GPA.2840-1192-7741-33019",
                title = "Monument Valley",
                developer = "ustwo games",
                category = PurchaseCategory.GAME,
                amount = 3.99,
                purchaseDate = time(2022, 10, 14, 19, 10),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "First paid puzzle game on the account"
            ),
            PlayPurchase(
                orderId = "GPA.2825-3319-7740-99124",
                title = "Busuu Language Learning (7-Day Trial)",
                developer = "Busuu",
                category = PurchaseCategory.SUBSCRIPTION,
                amount = 0.00,
                purchaseDate = time(2022, 8, 1, 9, 30),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = true,
                subscriptionPeriod = "7-Day Free Trial",
                notes = "Trial start $0.00 Google Play transaction"
            ),
            PlayPurchase(
                orderId = "GPA.2815-6632-9910-44821",
                title = "The Dark Knight",
                developer = "Warner Bros.",
                category = PurchaseCategory.MOVIE,
                amount = 9.99,
                purchaseDate = time(2022, 6, 20, 21, 30),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Christopher Nolan classic"
            ),
            PlayPurchase(
                orderId = "GPA.2790-4418-2293-88102",
                title = "Titanium Backup PRO Key",
                developer = "Titanium Track",
                category = PurchaseCategory.APP,
                amount = 5.99,
                purchaseDate = time(2021, 9, 8, 11, 20),
                paymentMethod = "Google Pay •••• 4821",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Account origin era root utility purchase"
            ),
            PlayPurchase(
                orderId = "GPA.2775-4491-1120-66392",
                title = "Leo's Fortune (Promo Sale)",
                developer = "1337 & Senri LLC",
                category = PurchaseCategory.GAME,
                amount = 0.00,
                purchaseDate = time(2021, 7, 14, 16, 15),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Play Store special event free promotional claim ($0.00)"
            ),
            PlayPurchase(
                orderId = "GPA.2760-8819-3341-55209",
                title = "SwiftKey Keyboard - Cobalt Theme Pack",
                developer = "TouchType Ltd.",
                category = PurchaseCategory.IN_APP,
                amount = 1.99,
                purchaseDate = time(2021, 4, 18, 15, 45),
                paymentMethod = "Play Store Balance",
                status = PurchaseStatus.COMPLETED,
                isSubscription = false,
                notes = "Classic keyboard customization pack"
            )
        )
    }
}
