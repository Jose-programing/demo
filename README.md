# Horizon Planner

A JavaFX desktop application by Team 'The Planners' for student task and schedule management, built for the CS 3443 course at UTSA.
Contributors: Carlos Vargas, Jose Ibarra, Alexander Hernandez-Carney, Victor Pasalagua

## Features

- **Main Menu** — Dashboard showing today's tasks and a rotating motivational quote
- **Calendar View** — Visual calendar for browsing and planning tasks by date
- **Task Management (CRUD)** — Add, update, and delete tasks with a name, type, subject, and due time
- **Study Sessions** — Timed 30-minute and 60-minute study session modes, plus a custom topic session
- **Progress Tracking** — View your completed and upcoming tasks

## Tech Stack

- **Language:** Java 25
- **UI Framework:** JavaFX 21.0.6 (FXML + CSS)
- **Build Tool:** Maven (with Maven Wrapper)
- **Testing:** JUnit Jupiter 5.12.1


## Getting Started

### Prerequisites

- Java 25 or later
- Maven (or use the included `mvnw` wrapper)

### Running the App

```bash
# Using Maven Wrapper (no Maven install required)
./mvnw clean javafx:run

# Or with Maven installed
mvn clean javafx:run
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

## Data Format

Tasks are stored in a data file with the following columns:

```
date, type, subject, due_time
```

Example:
```
2026-05-04,Lab,Systems Prog,11:59pm
2026-05-04,Homework,Linear Algebra,11:59pm
```
