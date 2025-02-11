# JourneyTracker20_Compose

## Overview
JourneyTracker20_Compose is an Android app built using Jetpack Compose. It helps users track their journey by listing stops, showing distances covered and remaining, and handling visa requirements. The app reads journey data from an input file and provides an interactive interface to navigate through stops. Users can easily switch between kilometers and miles, visualize progress using a progress bar, and advance through stops with a button click.

## Features
- Displays a detailed list of journey stops, including distances and visa requirements.
- Dynamically tracks the distance covered and remaining with real-time updates.
- Supports both kilometers and miles with an easy toggle option.
- Features an interactive progress bar showing journey completion status.
- Reads journey data from an external file (`input1.txt`) located in the `res/raw` directory.
- Provides a **Next Stop** button to move forward in the journey sequentially.


## Installation
1. **Clone the Repository**: Download or clone the source code from the repository.
2. **Open in Android Studio**: Launch the project using Android Studio.
3. **Prepare Input File**: Ensure the `input1.txt` file is placed in the `res/raw` directory.
4. **Build and Run**: Compile and execute the app on an emulator or a physical device.

## File Format (`input1.txt`)
The input file should contain journey data in the following format:
```
StopName, Distance (in km), VisaRequirement
```
Example:
```
Delhi, 0, Not Required
Mumbai, 1200, Not Required
Bangalore, 1700, Not Required
Dubai, 2200, Required
London, 6700, Required
New York, 12000, Required
Paris, 6500, Required
Tokyo, 5800, Required
```
Each line represents a journey stop, its distance from the previous stop, and visa requirements for that location.

## Usage Instructions
1. **Launch the App**: The app automatically loads and displays the journey details.
2. **View Stops**: Scroll through the list of completed stops with distance and visa details.
3. **Advance in Journey**: Tap the **Next Stop** button to move to the next stop.
4. **Toggle Distance Units**: Use the **Switch to Miles** button to convert distances to miles.
5. **Monitor Progress**: Observe the journey completion status via the progress bar.

## Code Explanation
### `MainActivity.kt`
The entry point of the app. It initializes the UI by calling `JourneyTrackerApp()` inside `setContent()`.

### `JourneyTrackerApp()` (Main UI Logic)
This function defines the primary UI components and handles state management using `remember` and `mutableStateOf`:
- **State Variables:** 
  - `isKm`: Boolean for distance unit toggle.
  - `currentStopIndex`: Tracks the current stop.
  - `stops`, `distances`, `visaRequirements`: Lists storing journey data.
  - `totalDistance`: Stores total journey distance.
- **Data Initialization:** Uses `LaunchedEffect` to read data from `input1.txt` and calculate `totalDistance`.
- **Progress Calculation:** Computes `distanceCovered` and `distanceLeft`.
- **UI Components:**
  - **Stops Card:** Displays visited stops using `LazyColumn`.
  - **Distance Covered & Left Cards:** Show progress dynamically.
  - **Buttons:** 
    - Toggle between kilometers and miles.
    - Navigate to the next stop.
  - **Progress Bar:** Indicates journey completion.

### `StopItem()` (Reusable Component)
Displays stop details including:
- Name
- Distance (converted if needed)
- Visa requirement

### `kmToMiles()` (Utility Function)
Converts kilometers to miles using `km * 0.621371`.

### `readStopsFromFile()` (Data Parsing)
Reads the journey data from `input1.txt`, splits it into `stops`, `distances`, and `visaRequirements`, and stores them in `MutableLists`.

## Dependencies
- **Jetpack Compose**: For UI components.
- **Material 3 Components**: For modern UI styling and interaction.
- **Kotlin**: Primary language for development.
- **Android SDK**: Ensures compatibility with Android devices.

