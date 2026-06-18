# THE CAPITAL GURU - Architecture & Product Requirements Document

## 1. Product Requirements Document (PRD)
**Mission**: Create the world's best free trading discipline platform focused on journaling, accountability, risk management, and gamified consistency.
**Target Audience**: Traders (Primary: India. Secondary: Global) seeking psychological edge and robust tracking.
**Core Proposition**: Combine DuoLingo gamification loops (Streaks, XP, Rankings) with serious trading tools (Trade Logs, Discipline Scores, Mistake analysis). No payments involved. Free utility.

## 2. User Stories
### Core Flow
- As a trader, I want to view my Discipline Score and Streak on the dashboard, to feel motivated.
- As a trader, I want to quickly log a trade with structured fields (Entry, Exit, Target, PnL) to ensure I document my setups.
- As a trader, I want to view my trading history in a list to monitor performance.

### Gamification & Discipline
- As a trader, I earn XP when I complete a daily lesson or log a trade.
- As a trader, I lose Discipline Points if my risk is over 2% on a single setup.
- As a trader, my Streak increases for every consecutive day I interact with the platform or do a psychological reflection.

## 3. App Architecture (Android Native Adaptation)
Based on platform capabilities, the tech stack maps as follows:
- **UI Framework**: Jetpack Compose (Material Design 3 - Dark Theme focused).
- **Architecture**: MVVM + Clean Architecture principles.
- **Local Database**: Room DB for Trading Journals.
- **Local Key-Value**: Android DataStore for XP, Streaks, Discipline Score.
- **Asynchronous Coroutines**: Kotlin Coroutines & Flow.

## 4. Frontend Folder Structure Overview
```
/app/src/main/java/com/example/
├── data/              # Room DAOs, DataStore Preferences, Repositories
│   ├── AppDatabase.kt
│   ├── TradeEntry.kt
│   ├── TradeDao.kt
│   ├── UserStatsRepository.kt
├── ui/                # UI Layer (Screens & Nav)
│   ├── AppNavigation.kt
│   ├── DashboardScreen.kt
│   ├── JournalScreen.kt
│   ├── AddTradeScreen.kt
│   └── theme/         # Material 3 Theme Configuration
└── MainActivity.kt
```

## 5. Local Database Schema (Room)

**Table: `trades`**
| Field | Type | Description |
|-------|------|-------------|
| id | Int (PK) | Auto-generated ID |
| tradeDate | Long | Timestamp of the trade |
| market | String | E.g., Crypto, Forex, NSE |
| instrument | String | E.g., NIFTY, BTC/USD |
| direction | String | Long / Short |
| entryPrice | Double | Bought/Sold at |
| exitPrice | Double | Closed at |
| stopLoss / target | Double | Required Risk/Reward parameters |
| pnl | Double | Profit & Loss |
| setupType | String | User's system pattern |
| emotionBefore | String | Psychology tracking |
| emotionAfter | String | Psychology tracking |
| notes/mistakes | String | Post-session analysis |

**DataStore: `user_stats`**
| Key | Type | Description |
|-----|------|-------------|
| discipline_score | Int | Ranges from 0-100 (Formula: Lesson%, Journal%, Quiz%, Risk%, Streak%) |
| current_streak | Int | Number of consecutive active days |
| total_xp | Int | System progression |

## 6. Discipline Score Engine
The platform's proprietary score system (0-100%):
- **Lesson Completion (25%)**: Daily reads derived from local content logs.
- **Journal Consistency (20%)**: +5 points daily for logging a trade or reflecting.
- **Quiz Accuracy (15%)**: Based on weekly psychology checks.
- **Risk Compliance (15%)**: Loss of points if actual stop loss was bypassed.
- **Streak Health (15%)**: Requires min 3-day active pipeline.
- **Review Completion (10%)**: Weekend reviews of notes.

## 7. Scaling Roadmap (Firebase & Cloud Functions)
When migrating to the Cloud (Firebase):
1. **Firestore Schema**:
   - `/users/{uid}/`: Profile, overallXP, currentStreak, discipleScore.
   - `/users/{uid}/trades/{tradeId}`: Identical to Local Database Schema.
   - `/global_leaderboard/{rank}`: Aggregated top active traders.
2. **Cloud Functions**:
   - `calculateDiscipline()`: CRON job running nightly to deduct points for inactivity.
   - `streakManager()`: Resets streaks to 0 if a user misses a 48-hour activity window.
3. **AI Coach (Gemini API Integration)**:
   - Server-side invocation utilizing the user's weekly JSON trade export.
   - Given the trades, Gemini analyzes: "You traded 5 times outside your normal setup. Focus on patience."

## 8. UX/UI Specifications
- **Theme**: Forced Dark Mode to reduce eye strain.
- **Typography**: Clean, sans-serif readable metrics.
- **Palette**:
    - **Primary Accent**: Gold (Premium Trading feel).
    - **Positive State**: Muted Green (`#4CAF50`).
    - **Negative State**: Muted Red (`#E57373`).
- **Empty States**: Encouraging placeholders on the Journal screen ("No trades logged. Plan the trade, trade the plan.").
