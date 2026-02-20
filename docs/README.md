# Cors User Guide

![Screenshot of Cors Ui showing multiple messages exchanged between user and Cors](/docs/Ui.png)

Welcome to Cors, the friendly chatbot who takes your tasks and helps track your deadlines.

Cors has the following features:
- [x] Add tasks
- [x] Delete tasks
- [x] Show all tasks
- [x] Find a task based on a keyword
- [ ] [Coming Soon!] Sort tasks based on priority


## Adding deadlines

For users with many deadlines to keep track of, Cors is the perfect match!

Add a deadline easily with one command.

Example: `deadline finish task /by 20-02-2026 1800`

Cors saves the date for you, and a new task is added to the list:

```
Got it, I've added this task:
[D] [ ] finish task (by: 20 Feb 2026, 6pm)
Now you have 1 tasks in your list
```

## Finding tasks

Users can also search for tasks they have previously added.
As long as your remember one keyword from the task you added, you can
search for that keyword

Example: `find hw`

Cors searches your tasks and shows the list of all tasks that have that keyword:

```
Here are the matching tasks in your list:
[D] [ ] finish 2103t hw
```

## Auto Saving

All tasks added are automatically saved to file once you are finished with
Cors.

Simply type the following:

Example: `bye`

Cors will exit and save all the important tasks you have added to the list.