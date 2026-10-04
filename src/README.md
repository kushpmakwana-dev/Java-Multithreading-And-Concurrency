# Threading & Concurrency — Part 2

## 1. What is a Thread?

A thread is the smallest unit of execution within a process that the operating system schedules to run.

In simple terms:

A thread is an execution path that actually carries out instructions of a program.

Imagine you have a program that performs some task. The program contains instructions, but those instructions need to be executed.

That execution is carried out by a thread.

### Mental Model: Baking Cookies 🍪

Imagine your family is baking cookies.

- The recipe represents the program.
- Baking cookies represents the task.
- Your mother represents the thread executing the task.

Your mother is the one actually carrying out the instructions: preparing dough, placing cookies in the oven, and so on.

Now imagine you, your mother, and your sister all join the work.

[Mom likes to get everyone involved!](https://images.openai.com/static-rsc-4/M9G9_d3Fpjz86CZmSdVqCJC5yiiVJC709rxzxEt4Y0xN5FEkLu8ZYDh0s_6A4Y5dKEe_9TVViSwU3elAauf8Nlmi4t9BCRJSEdjJdQ_4MPEBdz1ztIGIXQ2Cg52wRfhZbvI1Pixx343JUoOEaweg9fFR8Qp_ByUxXsXz_EYyJcQ89HZv54ACUvNG31icujza?purpose=inline)

Instead of one person handling everything, multiple people can work on different parts of the job.

This is the intuition behind multithreading: multiple execution paths working within a program.

## 2. Why Do We Need Multithreading?

The primary motivation is to allow an application to handle multiple tasks concurrently.

Consider a backend application:

- Processing incoming HTTP requests.
- Fetching data from a database.
- Performing calculations.
- Writing logs.
- Communicating with external APIs.

If an application could execute only one task at a time, other work might have to wait unnecessarily.

Threads allow different tasks to make progress without requiring the entire application to wait for one task to finish.

### Two major benefits

1\. Concurrency

Multiple tasks can make progress during overlapping periods of time.

2\. Parallelism

Multiple tasks can execute simultaneously on different CPU cores.

These are related, but they are NOT the same thing.

## 3. Understanding CPU Cores

Imagine your processor has four CPU cores.

## A 4-Core CPU

Core 1

Executing

Thread 1

Core 2

Executing

Thread 2

Core 3

Executing

Thread 3

Core 4

Executing

Thread 4

Conceptual representation: four threads executing in parallel on four cores.

At a given instant, four cores can execute four threads in parallel, assuming each core is available for one such execution.

But here's the interesting part.

### Does a 4-core CPU mean only 4 threads can exist?

Absolutely not!

You can have hundreds or thousands of threads in a program or across the system.

The operating system scheduler decides which runnable threads get CPU time.

For example:

- Thread 1 executes.
- Thread 2 executes.
- Thread 3 executes.
- Thread 4 executes.
- The scheduler switches execution to other threads.

This is where context switching comes into the picture.

### Mental Model: Four Chefs, Many Orders

Imagine a restaurant has four chefs but 100 orders.

Only four chefs can actively prepare one order each at a time, but they can switch between orders as they progress.

Similarly, a four-core CPU can schedule many threads over time.

Important distinction

- Number of threads: How many execution paths exist.
- Number of cores: How many threads can execute in parallel at a given instant.
- Context switching: Switching CPU execution from one thread to another.

## 4. Concurrency vs Parallelism

This distinction is extremely important for your upcoming Java Concurrency journey.

## Concurrency

One CPU core switching between multiple tasks

Multiple tasks make progress over the same period, but they are not necessarily executing at the exact same instant.

## Parallelism

Two CPU cores executing tasks simultaneously

Both threads execute at the same time.

Remember this:

Concurrency is about dealing with multiple tasks during overlapping periods.

Parallelism is about executing multiple tasks simultaneously.

## 5. The Performance Benefit

Let's return to the room-cleaning example.

Suppose cleaning the room involves:

1. Cleaning the ceiling.
2. Cleaning the table.
3. Cleaning the floor.
4. Cleaning the windows.

If one person performs everything sequentially, the total time can be high.

If multiple people work on independent tasks simultaneously, the work may finish sooner.

This is the intuition behind parallel processing.

However, more threads do not automatically mean better performance.

Thread creation, context switching, synchronization, and shared-resource contention introduce overhead.

We will explore those trade-offs later.

## 6. What We Should Carry Forward

| Concept           | Mental model                                       |
| ----------------- | -------------------------------------------------- |
| Thread            | An execution path                                  |
| Multithreading    | Multiple execution paths within a process          |
| CPU core          | Hardware execution resource                        |
| Concurrency       | Multiple tasks making progress                     |
| Parallelism       | Multiple tasks executing simultaneously            |
| Context switching | CPU switching between threads                      |
| Performance       | Potentially faster execution through parallel work |

### One important technical clarification

The instructor's four-core example is a useful starting point, but the exact number of simultaneously executing threads depends on the processor's hardware execution capabilities, including SMT/Hyper-Threading, and the operating system.

For our foundational mental model, four cores mean four available core-level execution resources.

# Java Multithreading & Concurrency

## 02. Process vs Thread

A process is an instance of an executing program with its own virtual address space and resources. A thread is an execution path within a process. Every process has at least one thread.

### Mental Model: Baking Cookies
The entire cookie-baking activity represents a process. Mother, you, and your sister represent threads performing different steps. One thread can perform all steps sequentially; multiple threads can work on independent steps concurrently.

### Key Differences
| Parameter | Process | Thread |
|---|---|---|
| Memory | Separate virtual address space | Shares process memory |
| Heap | Separate process heap | Shared within process |
| Stack | Has thread stacks | Each thread has its own stack |
| Creation | Generally heavier | Generally lightweight |
| Communication | Usually IPC | Shared memory possible |
| Isolation | Stronger | Lower |
| Context switching | Generally more expensive | Generally less expensive |
| Failure | Usually isolated from other processes | May affect shared state or entire process |

### Why Threads?
Threads allow multiple tasks to make progress concurrently. On multiple CPU cores, tasks may execute in parallel. Concurrency means overlapping progress; parallelism means simultaneous execution.

### Important Java Example
```java
class SharedCounter {
    int count = 0;
}

SharedCounter counter = new SharedCounter();
Thread t1 = new Thread(() -> counter.count++);
Thread t2 = new Thread(() -> counter.count++);
t1.start();
t2.start();
```
Both threads access the same heap object. This is efficient but introduces race-condition risks.

### Important Clarifications
- Threads have private stacks but share process heap/resources.
- Thread creation is not free; thread pools enable reuse.
- Shared memory does not guarantee thread safety.
- An uncaught exception in one Java thread does not automatically terminate all other threads.
- Process context switching is generally more expensive, but actual cost depends on system conditions.

### Final Mental Model
1. Process = execution environment.
2. Thread = execution path.
3. Shared memory = efficient communication with concurrency risks.


# Java Multithreading & Concurrency

## 02. Process vs Thread

A process is an instance of an executing program with its own virtual address space and resources. A thread is an execution path within a process. Every process has at least one thread.

### Mental Model: Baking Cookies
The entire cookie-baking activity represents a process. Mother, you, and your sister represent threads performing different steps. One thread can perform all steps sequentially; multiple threads can work on independent steps concurrently.

### Key Differences
| Parameter | Process | Thread |
|---|---|---|
| Memory | Separate virtual address space | Shares process memory |
| Heap | Separate process heap | Shared within process |
| Stack | Has thread stacks | Each thread has its own stack |
| Creation | Generally heavier | Generally lightweight |
| Communication | Usually IPC | Shared memory possible |
| Isolation | Stronger | Lower |
| Context switching | Generally more expensive | Generally less expensive |
| Failure | Usually isolated from other processes | May affect shared state or entire process |

### Why Threads?
Threads allow multiple tasks to make progress concurrently. On multiple CPU cores, tasks may execute in parallel. Concurrency means overlapping progress; parallelism means simultaneous execution.

### Important Java Example
```java
class SharedCounter {
    int count = 0;
}

SharedCounter counter = new SharedCounter();
Thread t1 = new Thread(() -> counter.count++);
Thread t2 = new Thread(() -> counter.count++);
t1.start();
t2.start();
```
Both threads access the same heap object. This is efficient but introduces race-condition risks.

### Important Clarifications
- Threads have private stacks but share process heap/resources.
- Thread creation is not free; thread pools enable reuse.
- Shared memory does not guarantee thread safety.
- An uncaught exception in one Java thread does not automatically terminate all other threads.
- Process context switching is generally more expensive, but actual cost depends on system conditions.

### Final Mental Model
1. Process = execution environment.
2. Thread = execution path.
3. Shared memory = efficient communication with concurrency risks.