# Personal Finance App

A personal finance management app built with Kotlin for Android.  
This app allows users to track their transactions, manage balances, and monitor their financial activity in an intuitive way.

---

## Features
- Splash Screen
- User authentication (Sign up, Log in, Log out)
- Send and receive transactions with purpose
- Automatic balance updates for sender and receiver
- Offline support
- Transaction history with dates and details
- Profile management
- Dialogs

---

## Technologies Used
- Kotlin
- Android Jetpack (ViewModel, LiveData, Navigation Component)
- Firebase Firestore (Remote database)
- Room Database (Local storage)
- Coroutines & Flow (Asynchronous operations)
- Git & GitHub
- MVI architecture

---

## Project Structure
```text
PersonalFinanceApp/
  data/
    local/        # Room entities and DAO
    remote/       # Firebase models and services
    repository/   # Data handling classes
    mapper/       # DTO ↔ Domain mapping
  domain/          # Business logic and use cases
  presentation/    # Activities, Fragments, ViewModels
