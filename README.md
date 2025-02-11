# Journey Tracker App

## Overview
The **Journey Tracker App** is an Android application designed to help users track their journey progress, including stops, distances covered, distances left, and visa requirements. The app provides a user-friendly interface to visualize the journey's progress, switch between kilometers and miles, and navigate through stops sequentially.

---

## Features
1. **Journey Progress Tracking**:
   - Displays the total distance covered and the distance left to reach the final destination.
   - Progress bar visually represents the journey's completion percentage.

2. **Stop Management**:
   - Lists all stops along the journey with their respective distances and visa requirements.
   - Supports dynamic updates as the user progresses through the journey.

3. **Unit Conversion**:
   - Allows users to switch between kilometers and miles for distance measurements.

4. **Dynamic UI Updates**:
   - Automatically updates the UI when the user moves to the next stop or switches units.

5. **File-Based Data Input**:
   - Reads journey data (stops, distances, and visa requirements) from a text file (`input1.txt`).

---

## How It Works

### 1. **Data Input**
The app reads journey data from a file named `input1.txt` located in the `res/raw` directory. The file contains comma-separated values in the following format:
```
Stop Name, Distance (in km), Visa Requirement
```
Example:
```
Delhi, 0, Not Required
Mumbai, 1200, Not Required
Bangalore, 1700, Not Required
Dubai, 2200, Required
```

### 2. **UI Components**
- **Stops Card**: Displays a list of stops with their names, distances, and visa requirements.
- **Distance Covered Card**: Shows the total distance covered so far.
- **Distance Left Card**: Displays the remaining distance to the final destination.
- **Progress Bar**: Visualizes the journey's progress as a percentage.
- **Buttons**:
  - **Switch Unit**: Toggles between kilometers and miles.
  - **Next Stop**: Moves to the next stop in the journey.

### 3. **Functionality**
- **Unit Conversion**: Converts distances between kilometers and miles using the formula:
  ```
  1 km = 0.621371 miles
  ```
- **Dynamic Updates**: The UI updates automatically when the user:
  - Moves to the next stop.
  - Switches between kilometers and miles.
- **Progress Bar Animation**: The progress bar animates smoothly when the journey progresses.

---

## Code Structure

### 1. **MainActivity.kt**
The main activity handles the app's logic, including:
- Reading data from `input1.txt`.
- Managing the UI updates.
- Handling button clicks for unit conversion and next stop navigation.

#### Key Methods:
- `readStopsFromFile()`: Reads stops, distances, and visa requirements from the file.
- `updateUI()`: Updates the UI based on the current state of the journey.
- `animateProgressBar()`: Animates the progress bar to the current progress.
- `kmToMiles()`: Converts kilometers to miles.

### 2. **StopAdapter.kt**
A RecyclerView adapter for displaying stops in a list. Each item in the list shows:
- Stop name.
- Distance (in km or miles).
- Visa requirement.

#### Key Methods:
- `onCreateViewHolder()`: Inflates the layout for each stop item.
- `onBindViewHolder()`: Binds data to the view holder.
- `kmToMiles()`: Converts kilometers to miles for display.

### 3. **Layout Files**
- **activity_main.xml**: Defines the main layout with cards for stops, distance covered, distance left, buttons, and a progress bar.
- **item_stop.xml**: Defines the layout for each stop item in the RecyclerView.

---
