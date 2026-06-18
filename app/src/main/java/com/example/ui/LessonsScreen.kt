package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.UserStatsRepository
import kotlinx.coroutines.launch

val initialLessons = listOf(
    "Day 1: The Myth of the Holy Grail Setup",
    "Day 2: Accepting Randomness in Markets",
    "Day 3: Identifying Your Overtrading Triggers",
    "Day 4: Creating a Bulletproof Trading Plan",
    "Day 5: The Math of Risk of Ruin",
    "Day 6: Handling Drawdowns Without Panic",
    "Day 7: Weekly Review and Accountability"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsScreen(userStatsRepository: UserStatsRepository) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discipline Challenge", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                "Complete lessons to earn XP and increase your Discipline Score.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.padding(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(initialLessons) { lessonTitle ->
                    LessonCard(title = lessonTitle, onComplete = {
                        coroutineScope.launch {
                            userStatsRepository.addXp(100)
                            // Increase score by 5 points for completing a lesson, capped at 100
                            // In a real app this would track WHICH lessons are read to not double-count
                            userStatsRepository.updateDisciplineScore(85) // Simplified for prototype
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun LessonCard(title: String, onComplete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("+100 XP", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onComplete) {
                Icon(
                    Icons.Default.PlayCircleFilled,
                    contentDescription = "Complete Lesson",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
