# Task Distributor

A lightweight, customizable system that distributes tasks over multiple threads efficiently. 

## 📋 Table of Contents
- [Features](#-features)
- [Installation](#-installation)
- [Usage](#-usage)
- [Tech Stack](#-tech-stack)
- [Contributing](#-contributing)
- [License](#-license)

## Features
  * **Dynamic Worker Management:** Scale active threads dynamically between core and max capacity.
  * **Custom Rejection Handlers:** Built-in strategies for task rejection.
  * **Thread Lifecycle Hooks:** Pre and post-execution hooks for task metrics and monitoring.
  * **Graceful Shutdown:** Implements orderly teardown waiting for active tasks to complete.

## Installation

Download the JAR from the releases tab. It is also recommended to download the JavaDoc zip. 

Move the JAR into the lib folder of your java project. 

Then include the jar into your project using your IDE. 


## Usage

Now your project should be setup to use the Task Distributor. 

Here is some starter testing code to ensure there are no issues with the jar

```Java
import com.threadpool.ThreadPool;
import com.threadpool.ThreadSafeTaskQueue;
import com.threadpool.Task;

public class Main {
    public static void main(String[] args) {
        ThreadSafeTaskQueue queue = new ThreadSafeTaskQueue();
        ThreadPool pool = new ThreadPool(5, queue); // the 5 refers to the number of threads that you would like in the ThreadPool. 
        
        System.out.println("Custom JAR loaded via Maven!");
    }
}
```

Then you can add tasks to the queue. 

```Java
Runnable w = () -> {
  //whatever task you want to do. 
}

Task t = new Task("Task 1", "Type 1", w); // (TASK_NAME, TASK_TYPE, WORK)
pool.executeTask(t);
```

It is recommended that you create a List instance to track all of the tasks. 


It is recommended that you create a List instance to track all of the tasks. 

To shut down the thread pool
```Java
ThreadSafeTaskQueue queue = new ThreadSafeTaskQueue();
        ThreadPool pool = new ThreadPool(5, queue); // the 5 refers to the number of threads that you would like in the ThreadPool. 
List<Task> addedTasks = new ArrayList<Task>();
Runnable w = () -> {};
Task t = new Task("task 1", " task type 1", w);
addedTasks.add(t);// adding tasks to list. 
pool.executeTask(t);
bool allTasksComplete = false;

while(!allTasksComplete){
  allTasksComplete = true;
  for(Task b : addedTasks){
      if(!b.isCompleted){
        allTasksComplete= false;
      }
  }
}

if (allTasksComplete)
   pool.shutdown();

```

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
