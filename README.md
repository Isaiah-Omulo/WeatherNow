# WeatherNow - Real-Time Local Weather App

**WeatherNow** is a native Android application built in **Kotlin** that fetches and displays real-time weather information based on the user's current location. The app is designed with a modern, **offline-first architecture**, ensuring a seamless user experience even without an active internet connection.

This project was developed as a take-home assignment to demonstrate proficiency in modern Android development best practices.

---

## Features

- **Geolocation-Based Weather**: Automatically detects the user's location and fetches the latest weather data.
- **Graceful Permissions**: Politely requests location permissions upon first launch.
- **Rich Weather Display**: Shows current temperature, condition (e.g., Clear, Clouds), a dynamic icon, humidity, and wind speed.
- **Offline Caching**: Stores the most recent weather data locally. If the device is offline, it displays the last known data with a "Last Updated" timestamp, ensuring the app is always functional.
- **Clean, Modern UI**: A simple, user-friendly interface built with Jetpack Compose and Material 3.

---

## How to Set Up and Run the Project

### Prerequisites

- Android Studio (latest stable version recommended, e.g., Iguana or newer)
- An Android Emulator or a physical Android device (with Location/GPS enabled)

### Steps

1. **Clone the Repository**

    ```bash
    git clone https://github.com/Isaiah-Omulo/WeatherNow.git
    ```

2. **Open in Android Studio**

    - Launch Android Studio.
    - Select `File > Open...` or `Open an existing project`.
    - Navigate to the cloned `WeatherNow` directory and open it.

3. **Sync Gradle**

    - Android Studio will automatically start syncing the project's dependencies.
    - Wait for this process to complete.

4. **Run the App**

    - Select a run configuration (an emulator or your connected device).
    - Click the **Run** button.
    - The app will ask for location permissions. You must grant permission for the app to function correctly.

---

## Libraries Used

This project leverages a modern tech stack based on Kotlin and Jetpack:

- **Kotlin**: First-party and official language for Android development.
- **Kotlin Coroutines & Flow**: For managing asynchronous operations and handling streams of data, ensuring the UI remains responsive.
- **Jetpack Compose**: Android’s modern toolkit for building native UI declaratively.
- **Material 3**: The latest version of Google's design system for a modern, dynamic UI.
- **Hilt**: For dependency injection, which simplifies managing dependencies and improves testability.
- **Retrofit**: A type-safe HTTP client for making network requests to the OpenWeather API.
- **Room**: A persistence library for local database caching, providing an abstraction layer over SQLite.
- **ViewModel**: To store and manage UI-related data in a lifecycle-conscious way.
- **Google Play Services (Location)**: To efficiently and accurately determine the user's current location.
- **Coil**: An image loading library for Kotlin and Compose, used here to load weather icons from a URL.

---

## Architecture and Key Decisions

This application is built following the principles of **Clean Architecture** with an **MVVM (Model-View-ViewModel)** pattern in the presentation layer.

### Project Structure

#### Data Layer

- Responsible for all data operations: contains the Retrofit API service, Room database, and the `WeatherRepositoryImpl`.
- **Repository Pattern**:  
  - The `WeatherRepository` acts as the single source of truth.
  - It emits cached data from Room first, then attempts a network call to update the data.
  - If the network call fails, the old data is still displayed, supporting the offline-first strategy.
- **Mappers**:  
  - DTOs (Data Transfer Objects for the API) and Entities (for the database) are kept separate from core models.
  - Mappers are used to convert between these models to prevent the API/database structure from leaking into other parts of the app.

#### Domain Layer

- Contains core business logic and clean `Weather` model.
- Pure Kotlin with no Android dependencies — highly testable and reusable.
- Defines the `WeatherRepository` interface (the contract), which the data layer implements.  
  (Follows the Dependency Inversion Principle.)

#### UI (Presentation) Layer

- Built using Jetpack Compose and follows the MVVM pattern.
- **ViewModel**:
  - Requests weather data from the repository.
  - Handles location tracking.
  - Exposes UI state via a `StateFlow` (`WeatherState`).
- **UI State Class**:  
  - A single `WeatherState` data class represents all UI states (loading, success, error), making the UI predictable.
- **Composables**:  
  - The `WeatherScreen` is stateless and observes the state from the ViewModel to render the UI accordingly.

---

### Key Decisions

- **Offline-First Strategy**:  
  - Query the Room database first, then update from the network to provide immediate feedback even on slow networks.

- **Kotlin Flow**:  
  - Used from the DAO to ViewModel, creating a reactive stream that updates the UI automatically whenever local data changes.

- **Resource Wrapper**:  
  - A generic `Resource<T>` sealed class is used to wrap repository responses (`Loading`, `Success`, `Error`) for clean state management in the ViewModel and UI.

---

## Known Limitations & Future Improvements

| Feature                 | Status / Suggestion                                                |
|-------------------------|---------------------------------------------------------------------|
| Manual Refresh          | Not implemented. Add a pull-to-refresh or manual refresh button.   |
| Location Search         | Not implemented. Add a search bar for querying other cities.       |
| Error Handling          | Basic error UI. Could be improved with retry options and clearer messages. |
| Background Sync         | Weather updates not running in the background. Use WorkManager.    |
| Unit Testing            | Not implemented. Add tests for ViewModel, Repository, and UseCases. |

---

## Screenshots

*(Replace this section with screenshots of the app in action)*

---

## Author

**Isaiah Omulo**  
[GitHub](https://github.com/Isaiah-Omulo/WeatherNow.git) · [Email](mailto:omulodeveloper@gmail.com)

---

Made with Kotlin, Jetpack Compose, and Clean Architecture

