# Distributed GPX Activity Tracker

## Overview

This project is a **distributed activity tracking system** that allows users to upload **GPX files** via an Android mobile application. The backend processes the activity data using a **Master-Worker architecture** based on the **MapReduce model**, allowing parallel processing of the GPX data. Users can view their personal activity statistics (e.g., total distance, total climb, average speed) and compare them with global averages, which are calculated across all users in the system.

The project demonstrates a full-stack integration of a mobile frontend (Android) with a distributed backend (Java) that communicates over TCP sockets.

## Features

### Backend (Java)
- **Master-Worker Architecture**: The backend processes GPX files in parallel using multiple Worker nodes.
- **MapReduce Framework**: The system splits GPX data into chunks, sends them to Worker nodes, and aggregates the results to generate final activity stats.
- **Multi-threaded Server**: The Master node serves multiple users concurrently, handling multiple TCP connections.
- **TCP Socket Communication**: The Master communicates with both Worker nodes and the Android app using TCP sockets.
- **User Statistics**: Keeps track of personal and global statistics, including total exercise time, total distance, and total climb for each user and all users combined.

### Frontend (Android)
- **GPX File Upload**: Users can select and upload GPX files from their Android device to the backend for processing.
- **Profile Statistics**: Displays personal activity stats such as total distance, total climb, and total time.
- **Global Averages Comparison**: Users can compare their performance to global averages to see how they rank against others.
- **Interactive User Interface**: A simple, user-friendly Android app with multiple screens to navigate between file uploads, stats viewing, and comparisons.

## Project Structure

### Backend (Java)

- **`Master.java`**: The core server that handles user requests and distributes GPX file chunks to Worker nodes. Combines intermediate results from Workers and sends the final results back to the client.
- **`Worker.java`**: Processes assigned chunks of GPX data, calculating partial statistics and sending the results back to the Master.
- **`ActivityResult.java`**: Represents the final processed activity data, such as total distance, average speed, total climb, and total time.
- **`GPXReader.java`**: A utility class for parsing GPX files and extracting the waypoints (latitude, longitude, elevation, and time).
- **`IntermediateResult.java`**: Holds intermediate statistics (distance, time, climb) calculated by Workers.
- **`Profile.java`**: Stores and manages user-specific statistics.
- **`Statistics.java`**: Manages and calculates both user-specific and global statistics.

### Frontend (Android)

- **`Welcome.java`**: A welcome screen for entering user information or skipping directly to the app.
- **`MainActivity.java`**: The main screen of the app where users can upload a GPX file and view their stats.
- **`ProfileStatsActivity.java`**: Displays the user's personal statistics (total distance, total time, total climb).
- **`AverageStatsActivity.java`**: Displays the global average statistics, comparing the user’s performance with other users.

### Layout Files (Android XML)

- **`activity_welcome.xml`**: Welcome screen layout for user input and navigation.
- **`activity_main.xml`**: Layout for the main activity, providing buttons for uploading GPX files and viewing stats.
- **`activity_profile_stats.xml`**: Layout for displaying the user's personal statistics.
- **`activity_average_stats.xml`**: Layout for showing the global average statistics and user comparison.

## How It Works

1. **Frontend**: 
   - Users upload a GPX file through the Android app.
   - The file is sent to the backend system via a TCP socket connection.
   - Users can view their personal stats or compare them with global averages after processing is complete.

2. **Backend**:
   - The Master node receives the GPX file and splits the waypoints into chunks.
   - Each chunk is processed by a Worker node to calculate partial statistics (distance, time, climb).
   - The Master node aggregates the intermediate results and sends the final statistics back to the Android app.


