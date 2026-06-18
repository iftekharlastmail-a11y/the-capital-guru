package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.TradeEntry
import com.example.data.TradeRepository
import com.example.data.UserStatsRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTradeScreen(
    tradeRepository: TradeRepository,
    userStatsRepository: UserStatsRepository,
    onNavigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var market by remember { mutableStateOf("") }
    var instrument by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("") }
    var entryPrice by remember { mutableStateOf("") }
    var exitPrice by remember { mutableStateOf("") }
    var stopLoss by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var pnl by remember { mutableStateOf("") }
    var setupType by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var emotionBefore by remember { mutableStateOf("") }
    var emotionAfter by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Trade") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = market,
                    onValueChange = { market = it },
                    label = { Text("Market (e.g. Forex, Crypto)") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))
                OutlinedTextField(
                    value = instrument,
                    onValueChange = { instrument = it },
                    label = { Text("Instrument (e.g. BTC/USD)") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            OutlinedTextField(
                value = direction,
                onValueChange = { direction = it },
                label = { Text("Direction (Long / Short)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = entryPrice,
                    onValueChange = { entryPrice = it },
                    label = { Text("Entry Price") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(Modifier.width(16.dp))
                OutlinedTextField(
                    value = exitPrice,
                    onValueChange = { exitPrice = it },
                    label = { Text("Exit Price") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            OutlinedTextField(
                value = pnl,
                onValueChange = { pnl = it },
                label = { Text("PnL ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = setupType,
                onValueChange = { setupType = it },
                label = { Text("Setup Type (e.g. Breakout)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = emotionBefore,
                    onValueChange = { emotionBefore = it },
                    label = { Text("Emotion Before") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))
                OutlinedTextField(
                    value = emotionAfter,
                    onValueChange = { emotionAfter = it },
                    label = { Text("Emotion After") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Trade Notes & Mistakes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        val trade = TradeEntry(
                            market = market,
                            instrument = instrument,
                            direction = direction,
                            entryPrice = entryPrice.toDoubleOrNull() ?: 0.0,
                            exitPrice = exitPrice.toDoubleOrNull() ?: 0.0,
                            stopLoss = stopLoss.toDoubleOrNull() ?: 0.0,
                            target = target.toDoubleOrNull() ?: 0.0,
                            pnl = pnl.toDoubleOrNull() ?: 0.0,
                            setupType = setupType,
                            emotionBefore = emotionBefore,
                            emotionAfter = emotionAfter,
                            mistakes = "",
                            notes = notes,
                            risk = 0.0,
                            reward = 0.0
                        )
                        tradeRepository.insert(trade)
                        
                        // Gamification: Add XP and try to bump discipline score
                        userStatsRepository.addXp(50)
                        userStatsRepository.incrementStreak()
                        
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Trade & Earn XP")
            }
        }
    }
}
