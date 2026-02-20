# XYXX

XYXX is a lightweight task and contact management application built in Java.  
It enables users to manage tasks, deadlines, events, and contacts through structured command input.

The application is designed for users who prefer keyboard-driven productivity with clear, validated command syntax.

---

# Sample User Interface

![XYXX User Interface](Ui.png)

The interface consists of a scrollable message display area and an input bar for command entry.

---

# Technology

- Language: Java
- Distribution: Executable `.jar` file
- Runtime Requirement: Java 17 (or later)

---

# Running the Application

## Option 1: Double Click

If Java is properly installed, you may run the application by double-clicking the provided `.jar` file.

## Option 2: Command Line

Navigate to the directory containing the `.jar` file and run:

```java -jar xyxx.jar```

Replace `xyxx.jar` with the actual release filename if different.

---

# Overview

XYXX supports:

- Task creation and management (todo, deadline, event)
- Marking and unmarking tasks
- Task search and detailed views
- Contact management
- Linking contacts directly within task descriptions
- Structured and validated date-time input

---

# Quick Start

1. Launch the application.
2. Enter commands into the input field.
3. View tasks and contacts in the interface.

Example:

```
todo Finish IS1108 assignment
deadline Submit report /by 2026-03-01 1800
cadd /name John Tan /number 91234567
```

---

# User Guide

## Date and Time Format

All date-time inputs must follow one of the formats below:

- `yyyy-mm-dd`
- `yyyy-mm-dd hhmm`

Examples:

- `2026-03-01`
- `2026-03-01 1800`

Time must be in 24-hour format (`hhmm`).

---

# Task Management

## `list`

Displays all tasks.

```
list
```


---

## `todo`

Creates a simple to-do task.
```
todo <description>
```

Example:
```
todo Finish IS1108 assignment
```

---

## `deadline`

Creates a task with a deadline.
```
deadline <description> /by <date>
```

Example:
```
deadline Submit report /by 2026-03-01 1800
```

Requirements:

- `/by` is mandatory.
- Date must follow the supported format.

---

## `event`

Creates an event with a start and end time.
```
event <description> /from <start> /to <end>
```

Example:
```
event Project meeting /from 2026-03-01 1400 /to 2026-03-01 1600
```

Requirements:

- `/from` is mandatory.
- `/to` is mandatory.
- Both must follow the supported date-time format.

---

## `mark`

Marks a task as completed.
```
mark <task index>
```

Example:
```
mark 3
```

---

## `unmark`

Marks a task as not completed.
```
unmark <task index>
```

---

## `delete`

Deletes a task.
```
delete <task index>
```

---

## `find`

Searches for tasks containing a keyword.
```
find <keyword>
```

---

## `details`

Displays detailed information about a task.
```
details <task index>
```
---

# Contact Management

## `cadd`

Adds a contact.
```
cadd /name <name> /number <number>
```

Example:
```
cadd /name John Tan /number 91234567
```

Requirements:

- `/name` is mandatory.
- `/number` is mandatory.

---

## `cdelete`

Deletes a contact by number.
```
cdelete /number <number>
```

Example:
```
cdelete /number 91234567
```

---

# Linking Contacts in Tasks

Contacts can be referenced inside task descriptions using square brackets.

Example:
```
todo Call [John Tan] about project update
```
```
deadline Submit draft to [Dr Lee] /by 2026-03-05
```

Guidelines:

- The name inside `[ ]` must match an existing contact.
- This allows tasks to be logically associated with contacts.

---

# Command Summary

| Command    | Description |
|------------|------------|
| `list`     | Display all tasks |
| `todo`     | Create a simple task |
| `deadline` | Create a task with a deadline |
| `event`    | Create a task with start and end time |
| `mark`     | Mark a task as completed |
| `unmark`   | Mark a task as incomplete |
| `delete`   | Delete a task |
| `find`     | Search for tasks |
| `details`  | View detailed task information |
| `cadd`     | Add a contact |
| `cdelete`  | Delete a contact |

---

# Notes

- Commands are case-insensitive.
- Task indices refer to the numbering shown in the `list` output.
- Date-time input must follow the specified format exactly.
- Contact references must be enclosed in square brackets `[ ]`.
