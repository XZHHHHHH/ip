# Zhbot User Guide

Zhbot helps you manage tasks using a simple command format.

## Features

### Add todo
Command:
`todo <description>`

Example:
`todo clean room`

### Add deadline
Command:
`deadline <description> /by <yyyy-MM-dd>`

Example:
`deadline submit report /by 2026-02-25`

### Add event
Command:
`event <description> /from <start> /to <end>`

Example:
`event project meeting /from 2pm /to 4pm`

### List tasks
Command:
`list`

### Mark / unmark
Commands:
`mark <task-number>`
`unmark <task-number>`

### Delete task
Command:
`delete <task-number>`

### Find tasks
Command:
`find <keyword>`

### Remind upcoming deadlines
Commands:
`remind`
`remind <days>`

Behavior:
- `remind` defaults to 7 days.
- Shows upcoming unfinished deadline tasks due from today to today + days.

Examples:
- `remind`
- `remind 3`
