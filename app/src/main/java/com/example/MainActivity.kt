package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.local.AppDatabase
import com.example.data.repository.PurchaseRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.PlaySpendTheme
import com.example.ui.viewmodel.PurchaseViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PurchaseViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PurchaseRepository(database.purchaseDao())
        PurchaseViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaySpendTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
