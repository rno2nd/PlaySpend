package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.PurchaseCategory
import com.example.data.model.PurchaseStatus

class Converters {
    @TypeConverter
    fun fromCategory(category: PurchaseCategory?): String? {
        return category?.name
    }

    @TypeConverter
    fun toCategory(value: String?): PurchaseCategory? {
        return value?.let {
            try {
                PurchaseCategory.valueOf(it)
            } catch (e: IllegalArgumentException) {
                PurchaseCategory.APP
            }
        }
    }

    @TypeConverter
    fun fromStatus(status: PurchaseStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toStatus(value: String?): PurchaseStatus? {
        return value?.let {
            try {
                PurchaseStatus.valueOf(it)
            } catch (e: IllegalArgumentException) {
                PurchaseStatus.COMPLETED
            }
        }
    }
}
