package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PlayPurchase
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases ORDER BY purchaseDate DESC")
    fun getAllPurchases(): Flow<List<PlayPurchase>>

    @Query("SELECT * FROM purchases WHERE id = :id")
    fun getPurchaseById(id: Long): Flow<PlayPurchase?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(purchase: PlayPurchase): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(purchases: List<PlayPurchase>)

    @Update
    suspend fun update(purchase: PlayPurchase)

    @Delete
    suspend fun delete(purchase: PlayPurchase)

    @Query("DELETE FROM purchases WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM purchases")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM purchases")
    suspend fun getCount(): Int
}
