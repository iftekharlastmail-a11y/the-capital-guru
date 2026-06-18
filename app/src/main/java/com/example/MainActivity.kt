package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.CapitalGuruTheme
import com.example.ui.MainAppScreen
import com.example.data.AppDatabase
import com.example.data.UserStatsRepository

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = AppDatabase.getDatabase(this)
    val userStatsRepo = UserStatsRepository(this)

    setContent {
      CapitalGuruTheme {
        MainAppScreen(
            database = database,
            userStatsRepository = userStatsRepo
        )
      }
    }
  }
}
