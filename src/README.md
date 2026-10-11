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


Got it. This is the **bridge from “what is a thread?” to “why do we actually use threads?”** and then into implementation.

I’ll add this as the next section of the same README rather than treating it as a new unrelated topic.

# 03. Why Do We Use Threads?

The course is going to focus primarily on **threads and concurrency**, not inter-process communication.

The reason is practical: backend applications commonly need multiple independent tasks to make progress concurrently, and threads provide the execution mechanism for doing that within a process.

---

## 1. Concurrent Execution

The first major benefit of threads is allowing multiple tasks to make progress concurrently.

Think about a web browser.

While you are using a webpage:

- One execution path can handle your scrolling and clicking.
- Another can load data from the network.
- Another can perform background work.

If everything had to happen sequentially, a long-running operation could block the user interface.

For example:

```text
User clicks
    ↓
Download data
    ↓
Process data
    ↓
Update UI
```

If the download takes 5 seconds, the application could feel frozen if the same execution path has to wait for it.

With concurrent execution, another thread can continue handling user interaction while the download is happening.

### Mental Model

Think of a restaurant.

If one waiter has to:

1. Take an order.
2. Go to the kitchen.
3. Wait for the food.
4. Bring the food.
5. Take the next customer's order.

The restaurant becomes slow.

Instead, different workers can handle different responsibilities concurrently.

**The goal is not “make everything parallel.”**

The goal is:

> **Don't unnecessarily block one task while another independent task is making progress.**

---

# 2. Resource Sharing

Threads belonging to the same process share process resources, particularly memory.

This is one of the biggest advantages of threads.

Imagine a text editor such as Notepad or Word.

Different tasks might need to happen around the same time:

```text
                Text Editor
                     │
        ┌────────────┼────────────┐
        ↓            ↓            ↓
   User Input    Spell Check   Auto Save
```

One thread can process user interaction.

Another can perform background checking.

Another can save the document.

Because these threads belong to the same process, they can work with the same application state.

This makes communication between them efficient.

### But remember the trade-off

Shared memory is both:

**A strength**

> Threads can efficiently share data.

**A danger**

> Multiple threads modifying the same data can create concurrency problems.

For example:

```java
balance = balance - 100;
```

If two threads perform this operation simultaneously, things can go wrong.

This is where we will eventually reach:

- Race conditions
- `synchronized`
- Locks
- Atomic variables
- Visibility
- Thread safety

So don't think:

> "Threads share memory, therefore everything is easy."

Think:

> **Threads share memory, therefore communication is easier—but coordination becomes our responsibility.**

---

# 3. Threads Are Lightweight

Compared with processes, threads generally require fewer resources because they share many resources belonging to their process.

Think back to our cookie analogy.

Creating another process is like opening another completely independent kitchen.

You need another environment.

Creating another thread is more like asking another family member to help in the **same kitchen**.

The kitchen already exists.

The ingredients already exist.

The environment already exists.

The new worker simply joins the existing work.

Therefore:

```text
Process
   ↓
Separate execution environment
   ↓
More resources

Thread
   ↓
Shares existing process resources
   ↓
Generally fewer resources
```

### Important

"Lightweight" does **not** mean:

> Threads are free.

Threads still consume memory and CPU resources, and scheduling/context switching has overhead.

This becomes important later when we discuss why production systems generally don't keep creating and destroying threads unnecessarily.

That leads naturally toward **thread pools and `ExecutorService`**.

---

# 4. Real-World Examples

The same idea appears everywhere.

### Browser

```text
User interaction
        +
Network operations
        +
Background processing
```

### Text Editor

```text
User input
     +
Spell checking
     +
Auto-save
```

### Game

```text
Player movement
      +
Background music
      +
Network communication
      +
Game processing
```

### Backend Server

A backend application may simultaneously need to handle:

```text
HTTP Request A
HTTP Request B
HTTP Request C
Database work
External API calls
Background tasks
```

Threads allow these independent tasks to make progress concurrently.

---

# 5. The Big Picture

So far, our mental model should be:

```text
                    PROCESS
                       │
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       Thread 1     Thread 2     Thread 3
          │            │            │
          └────────────┼────────────┘
                       ↓
               Shared Process Memory
```

The **process provides the environment**.

The **threads provide the execution paths**.

The **shared memory allows efficient communication**.

And the **scheduler decides when runnable threads get CPU time**.

---

# 6. Why This Matters for Our Course

The rest of the course will essentially explore this question:

> **How do we safely and efficiently make multiple threads work together?**

Because creating a thread is the easy part.

The difficult questions are:

- What happens when two threads access the same variable?
- What happens when two threads modify the same object?
- How do we prevent race conditions?
- How does one thread communicate with another?
- How do we make changes visible between threads?
- How do we coordinate threads?
- How do we avoid unnecessary blocking?
- How do we manage many threads efficiently?

Those questions lead us into the actual world of **Java concurrency**.

---

## Final Mental Model

Keep this in your head:

> **Threads exist because applications often have multiple independent pieces of work that shouldn't unnecessarily block one another.**

And there are three major reasons we use them:

**1. Concurrent execution**  
Multiple tasks can make progress without unnecessarily waiting for one another.

**2. Resource sharing**  
Threads within the same process can efficiently access shared resources.

**3. Lightweight execution**  
Threads generally require fewer resources than separate processes because they share the process environment.

Absolutely. Since you specifically want these notes for your GitHub README, I'll make this section **revision-friendly but intuition-first**, and I'll preserve the instructor's important emphasis rather than dumping every sentence into the notes.

# 04. Creating Threads in Java — Extending `Thread`

Java provides multiple ways to create threads.

The first approach is:

> **Create a class that extends `Thread` and override its `run()` method.**

---

## 1. The Basic Idea

The simplest mental model is:

```text
extends Thread
       ↓
override run()
       ↓
create object
       ↓
call start()
       ↓
new thread begins execution
       ↓
run() executes on that thread
```

The important parts to remember are:

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // Code executed by this thread
    }
}
```

That's the core of this approach.

The code inside `run()` represents the work that the new thread is supposed to perform.

---

# 2. Why `extends Thread`?

`Thread` is a Java class that represents a thread of execution.

When our class extends `Thread`, our class becomes a specialized type of `Thread`.

```java
class MyThread extends Thread {
    
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}
```

Now:

```java
MyThread t1 = new MyThread();
```

creates an object of `MyThread`.

Because:

```java
MyThread extends Thread
```

the object is also a `Thread`.

### OOP connection

This is simply inheritance:

```text
Thread
   ↑
   |
MyThread
```

So the `MyThread` class inherits the functionality provided by `Thread` and provides its own implementation of `run()`.

---

# 3. The `run()` Method

The `run()` method contains the work that the thread should execute.

```java
class MyThread extends Thread {

    @Override
    public void run() {

        System.out.println("Thread is running");

        // Other work
    }
}
```

You can put whatever appropriate task you want inside `run()`.

For example:

```java
@Override
public void run() {

    for (int i = 0; i < 5; i++) {
        System.out.println("Running: " + i);
    }
}
```

### Mental Model

Think of a thread as a worker.

```text
Thread = Worker

run() = Work assigned to that worker
```

Creating the thread gives you the worker.

`run()` describes the work that worker performs.

---

# 4. The `main` Thread Already Exists

This is **very important**.

When a Java application starts, execution doesn't begin with some magical absence of threads.

The JVM starts with a thread that executes the `main()` method.

For example:

```java
public static void main(String[] args) {

    System.out.println("Hello");

}
```

That statement is executed by the **main thread**.

So conceptually:

```text
JVM
 |
 └── Main Thread
       |
       └── main()
```

You don't have to manually create the main thread.

It already exists.

---

# 5. Creating Multiple Threads

We can create multiple `Thread` objects.

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}
```

Then:

```java
public static void main(String[] args) {

    MyThread t1 = new MyThread();
    MyThread t2 = new MyThread();

    t1.start();
    t2.start();
}
```

Now conceptually we have:

```text
                 JVM Process
                      |
          ┌───────────┼───────────┐
          ↓           ↓           ↓
      Main Thread   Thread 1   Thread 2
          |           |           |
        main()      run()       run()
```

---

# 6. `start()` — The Important Part

This is one of the most important concepts in this section.

We don't normally do:

```java
t1.run();
```

to start a new thread.

We do:

```java
t1.start();
```

### What does `start()` mean?

Think of:

```java
t1.start();
```

as saying:

> "JVM, I want this `Thread` to execute independently."

The JVM/runtime arranges for a new thread of execution to become runnable, and that thread eventually executes `run()`.

Conceptually:

```text
t1.start()
    ↓
New thread becomes runnable
    ↓
Thread gets CPU time
    ↓
run()
    ↓
Thread's work executes
```

### Very important

`start()` and `run()` are **not equivalent**.

Calling:

```java
t1.run();
```

is simply an ordinary method invocation on the current thread.

It does **not** create a new thread.

Calling:

```java
t1.start();
```

initiates execution of the thread.

---

# 7. Multiple Threads Execute Concurrently

Suppose:

```java
class MyThread extends Thread {

    private int id;

    MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            System.out.println(
                "Thread " + id + " is running: " + i
            );
        }
    }
}
```

And:

```java
public static void main(String[] args) {

    MyThread t1 = new MyThread(1);
    MyThread t2 = new MyThread(2);

    t1.start();
    t2.start();
}
```

We now have:

```text
Main Thread
     |
     ├── creates t1
     |
     ├── creates t2
     |
     ├── starts t1
     |
     └── starts t2

             ↓

       Thread 1      Thread 2
          |             |
         run()         run()
```

The two threads can make progress concurrently.

---

# 8. Never Assume the Output Order

This is another **very important** concept.

You might expect:

```text
Thread 1 → 0
Thread 1 → 1
Thread 1 → 2

Thread 2 → 0
Thread 2 → 1
Thread 2 → 2
```

But you cannot rely on that.

You might get:

```text
Thread 1 → 0
Thread 2 → 0
Thread 1 → 1
Thread 2 → 1
Thread 2 → 2
Thread 1 → 2
```

Or:

```text
Thread 1 → 0
Thread 1 → 1
Thread 2 → 0
Thread 2 → 1
Thread 1 → 2
Thread 2 → 2
```

Or another valid ordering.

### Why?

Because the scheduler decides which runnable thread gets CPU time.

The timing depends on things such as:

- CPU availability.
- Thread scheduling.
- Other system activity.
- Blocking/sleeping.
- Runtime conditions.

Therefore:

> **When multiple threads execute concurrently, don't assume a particular execution order unless you explicitly establish that ordering.**

This is going to become extremely important when we study synchronization.

---

# 9. `sleep()` — Putting the Current Thread to Sleep

A thread can temporarily pause its execution using `Thread.sleep()`.

For example:

```java
Thread.sleep(1000);
```

means approximately:

> Suspend the currently executing thread for 1000 milliseconds.

That is:

```text
1000 ms = 1 second
```

### Mental Model

The instructor's analogy here is excellent:

> **Think of the thread as a person.**

The person is working.

Then:

```java
Thread.sleep(1000);
```

is like the person saying:

> "I'm going to sleep for a second."

They stop executing their work temporarily.

Then, after the sleep period, they become eligible to continue.

---

# 10. Example With `sleep()`

```java
class MyThread extends Thread {

    private int id;

    MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {

            System.out.println(
                "Thread " + id + " is running: " + i
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

Now imagine two threads:

```text
Thread 1
   ↓
Print
   ↓
Sleep
   ↓
Thread 2 gets opportunity
   ↓
Print
   ↓
Sleep
   ↓
Thread 1 wakes and continues
   ↓
Print
   ↓
Sleep
   ↓
...
```

This is why the output can appear interleaved.

---

# 11. `sleep()` Does NOT Mean "Give My Turn to Another Thread"

This is an important refinement.

When a thread sleeps, it stops being runnable for the requested sleep duration.

The scheduler may then run another runnable thread.

But `sleep()` does **not** guarantee:

> "Now Thread 2 must execute."

It only says:

> "The current thread should pause for approximately this duration."

After the sleep ends, the thread becomes eligible to run again; it does not necessarily execute immediately.

---

# 12. The Big Problem With `extends Thread`

At first glance, extending `Thread` looks perfectly fine:

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // work
    }
}
```

But Java has **single inheritance** for classes.

A class can extend only one class.

So this is impossible:

```java
class MyClass extends SomeClass, Thread {
}
```

Java doesn't allow multiple class inheritance.

Therefore, if our class already needs to extend another class:

```java
class MyClass extends SomeBusinessClass {
}
```

we cannot also do:

```java
class MyClass extends Thread {
}
```

That's the major limitation of this approach.

---

# 13. Why This Leads to `Runnable`

This limitation naturally leads us to the next approach.

Instead of saying:

> "My class **is a Thread**."

we can say:

> "My class has **a task that a Thread can execute**."

That's where the `Runnable` interface comes in.

Conceptually:

```text
extends Thread approach

MyTask IS-A Thread
```

versus:

```text
Runnable approach

MyTask = task/work
Thread = execution mechanism
```

This separation is much more flexible.

We'll see why in the next section.

---

# 14. Interview Questions

### Q1. How can you create a thread by extending `Thread`?

Extend the `Thread` class and override its `run()` method.

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // task
    }
}
```

---

### Q2. What is the difference between `start()` and `run()`?

`start()` initiates execution of a new thread, which will eventually invoke `run()`.

Calling `run()` directly is just a normal method call and executes on the calling thread.

---

### Q3. Why can't we extend another class if we extend `Thread`?

Because Java supports single inheritance for classes.

A class can extend only one class.

---

### Q4. Is thread output deterministic?

Generally, no.

When multiple threads execute concurrently, their exact scheduling order is not guaranteed unless synchronization or another ordering mechanism establishes it.

---

### Q5. Does `sleep()` create a new thread?

No.

It pauses the **currently executing thread** for the specified duration.

---

# 15. Final Mental Model

Remember this sequence:

```text
class MyThread extends Thread
             ↓
       override run()
             ↓
       create object
             ↓
          start()
             ↓
   new thread becomes runnable
             ↓
        run() executes
```

And remember the fundamental distinction:

> **`run()` defines the work. `start()` begins execution of that work on the new thread.**

Finally, remember why this approach isn't ideal in every situation:

> **Extending `Thread` consumes your one class-inheritance slot.**

And that is exactly why Java gives us another approach:

> **`Runnable` — separate the task from the thread that executes it.**


Perfect. This completes the **`Runnable` section**, including the transition toward `Callable`. Here are the consolidated GitHub-ready notes.

# 05. Creating Threads in Java — `Runnable`

The second way of creating a thread is by implementing the `Runnable` interface.

This approach solves the biggest limitation of extending `Thread`:

> **Java does not support multiple class inheritance.**

---

# 1. The Problem With `extends Thread`

Earlier, we did this:

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // business logic
    }
}
```

This works.

But remember:

```java
class MyThread extends Thread
```

means `MyThread` has already used its one class inheritance slot.

Java does **not** allow:

```java
class MyThread extends Thread, SomeOtherClass {
}
```

So if our class needs to extend another class for business/domain reasons, extending `Thread` is no longer an option.

This is where `Runnable` helps.

---

# 2. `Runnable` Separates the Task From the Thread

Instead of saying:

> "My class **is a Thread**."

we say:

> "My class represents **work that a Thread can execute**."

That is a much better mental model.

```text
extends Thread

MyTask IS-A Thread
```

versus:

```text
implements Runnable

MyTask = Task/Work
Thread = Worker that executes the task
```

This separation is the key idea behind `Runnable`.

---

# 3. Implementing `Runnable`

The basic structure is:

```java
class MyTask implements Runnable {

    @Override
    public void run() {
        // business logic
    }
}
```

Notice the important difference:

```java
extends Thread
```

became:

```java
implements Runnable
```

And `run()` is still there.

```java
@Override
public void run() {
    // business logic
}
```

The business logic doesn't fundamentally change.

---

# 4. Creating the `Runnable`

Now we create an instance of our task:

```java
Runnable task = new MyTask();
```

At this point:

**We have NOT created a new thread.**

We have created an object representing the work that needs to be performed.

This distinction is extremely important.

```text
Runnable object
       ↓
represents the TASK
```

It is not itself the execution mechanism.

---

# 5. Give the Task to a Thread

Now we create a `Thread` and give it our `Runnable`:

```java
Runnable task = new MyTask();

Thread thread = new Thread(task);
```

Think of this as:

```text
Task
 ↓
"Here is the work you need to perform."

Thread
 ↓
"I am the worker who will execute that work."
```

Then:

```java
thread.start();
```

Now the thread begins execution, and the `run()` method of the supplied `Runnable` is executed by that thread.

---

# 6. Complete Example

```java
class MyTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Task is running");
    }
}
```

Then:

```java
public class Main {

    public static void main(String[] args) {

        Runnable task = new MyTask();

        Thread thread = new Thread(task);

        thread.start();
    }
}
```

The flow is:

```text
MyTask object
     ↓
implements Runnable
     ↓
contains run()
     ↓
       +
     ↓
Thread object
     ↓
new Thread(task)
     ↓
start()
     ↓
new thread executes task.run()
```

---

# 7. What Actually Changed From `extends Thread`?

Very little about the actual execution model changed.

### Earlier

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // work
    }
}
```

```java
MyThread thread = new MyThread();

thread.start();
```

### With `Runnable`

```java
class MyTask implements Runnable {

    @Override
    public void run() {
        // work
    }
}
```

```java
Runnable task = new MyTask();

Thread thread = new Thread(task);

thread.start();
```

The important difference is **who represents the task and who represents the execution mechanism.**

---

# 8. Mental Model: Worker vs Job

This is probably the most useful mental model to remember.

Imagine a company.

You have:

```text
Runnable = Job description
Thread   = Worker
```

The job says:

> "Here is the work that needs to be done."

The worker says:

> "I will execute that work."

So:

```text
Runnable
   ↓
"What should be done?"

Thread
   ↓
"Who executes it?"
```

This separation becomes extremely important later when we move toward `ExecutorService` and thread pools.

---

# 9. Why `Runnable` Is Preferred

Suppose we have an existing business class:

```java
class PaymentService extends SomeBaseService {
    
}
```

We cannot do:

```java
class PaymentService extends SomeBaseService, Thread {
}
```

because Java doesn't support multiple class inheritance.

But we can do:

```java
class PaymentService extends SomeBaseService
                      implements Runnable {

    @Override
    public void run() {
        // background task
    }
}
```

Now we can inherit from our required business class **and** implement `Runnable`.

```text
PaymentService
      │
      ├── extends SomeBaseService
      │
      └── implements Runnable
```

This is the major practical advantage.

---

# 10. `Runnable` Does Not Mean "Thread"

This distinction is worth remembering.

A `Runnable` is simply a contract representing a piece of work that can be executed.

```java
Runnable task = ...;
```

doesn't mean:

> "A thread is running."

A thread exists separately:

```java
Thread thread = new Thread(task);
```

And execution begins when:

```java
thread.start();
```

is called.

---

# 11. Why `run()` Still Exists

You might wonder:

> "If I'm implementing `Runnable`, why am I still writing `run()`?"

Because `Runnable` defines the task contract.

Conceptually, its important method is:

```java
void run();
```

Your implementation provides the actual work:

```java
@Override
public void run() {
    // actual task
}
```

The `Thread` can then execute that task.

---

# 12. Thread vs Runnable

| `Thread` | `Runnable` |
|---|---|
| Class | Interface |
| Extend it | Implement it |
| Your class becomes a `Thread` | Your class represents a task |
| Uses class inheritance | Doesn't consume class inheritance slot |
| Cannot extend another class | Can extend another class |
| Couples task with execution mechanism | Separates task from execution |
| Simple for demonstrations | More flexible design |

### The most important difference

```text
Thread
→ execution mechanism

Runnable
→ task to execute
```

---

# 13. Is `Runnable` More Complicated?

Slightly.

With `Thread`:

```java
MyThread t = new MyThread();
t.start();
```

With `Runnable`:

```java
Runnable task = new MyTask();
Thread t = new Thread(task);
t.start();
```

There is one additional object/step.

But this is **not really a meaningful disadvantage**.

The design benefit of separating the task from the thread is much more important.

That's why `Runnable` is generally preferred over extending `Thread` when directly choosing between the two.

---

# 14. A Better Way to Think About the Design

Don't think:

> "`Runnable` is just another way to create a thread."

Think:

> **`Runnable` allows us to represent work independently from the mechanism that executes that work.**

That distinction becomes much more powerful later.

For example:

```text
Task
 ↓
Runnable
 ↓
ExecutorService
 ↓
Thread Pool
 ↓
Available Worker Thread
```

Now the application doesn't need to manually create a new thread for every task.

This is much closer to how production backend systems are designed.

---

# 15. `Thread` vs `Runnable` vs `Callable`

The instructor is now introducing the next concept: `Callable`.

At first glance, `Runnable` and `Callable` look similar because both represent a task.

The major conceptual difference is:

```text
Runnable
    ↓
run()
    ↓
returns nothing
```

while:

```text
Callable
    ↓
call()
    ↓
can return a result
```

For example, conceptually:

```java
Runnable
    → perform the task
    → no result

Callable
    → perform the task
    → return a result
```

`Callable` also works with checked exceptions in a way `Runnable`'s `run()` contract does not.

We'll treat `Callable` properly when we reach it rather than mixing it into the current thread-creation discussion.

---

# 16. Interview Questions

### Q1. Why would you prefer `Runnable` over extending `Thread`?

Because Java supports single inheritance. Implementing `Runnable` allows the class to extend another class while still representing a task that can be executed by a thread.

---

### Q2. Does implementing `Runnable` create a thread?

**No.**

It creates an object representing a task.

A `Thread` must execute that task.

```java
Runnable task = new MyTask();

Thread thread = new Thread(task);

thread.start();
```

---

### Q3. What is the relationship between `Thread` and `Runnable`?

`Runnable` represents the work, while `Thread` provides the execution mechanism.

---

### Q4. Why is `Runnable` generally preferred over extending `Thread`?

Because it separates the task from the thread and avoids consuming the class's single inheritance relationship.

---

# 17. Final Mental Model

This is the part I would **really remember**:

```text
                 Runnable
                    │
                    │
              "What work?"
                    │
                    ↓
                  run()
                    │
                    │
                    ↓
                 Thread
                    │
                    │
              "Who executes?"
                    │
                    ↓
                  start()
                    │
                    ↓
             New execution
```

Or in one sentence:

> **`Runnable` defines the work; `Thread` provides a mechanism for executing that work.**

And this is the reason `Runnable` is a better abstraction than directly extending `Thread` for most basic thread-task design.

**Next concept: `Callable`** — which looks similar to `Runnable`, but introduces a very important capability: **getting a result back from the task.**

Bro, this is an important section because we're moving from simply creating threads to understanding how Java handles tasks that return results. Let's turn the entire lecture into GitHub-ready notes, keeping the mental models, code examples, and interview distinctions clear.

# 6. Callable, Exceptions, ExecutorService & Future

Java Multithreading & Concurrency · README.md

## 1. Why do we need `Callable`?

Remember our previous discussion about `Runnable`.

```
public interface Runnable {
    void run();
}
```

The `run()` method returns `void`.

Think about ordinary methods in Java. Sometimes we want a method to perform an operation and give us a result.

```
int calculateSum() {
    return 10 + 20;
}
```

If every method had to return `void`, we would lose the ability to return results directly.

The same limitation exists with `Runnable`: its task contract doesn't provide a return value.

This is where `Callable` comes in.

Mental model:

- `Runnable` — perform some work.
- `Callable` — perform some work and return a result.

## 2. Understanding the `Callable` interface

The essential structure of `Callable` is:

```
public interface Callable<V> {
    V call() throws Exception;
}
```

Here, `V` represents the return type.

For example:

```
import java.util.concurrent.Callable;

class SumTask implements Callable<Integer> {

    @Override
    public Integer call() {
        return 10 + 20;
    }
}
```

Let's break this down.

- `Callable<Integer>` means the task returns an `Integer`.
- `call()` contains the actual business logic.
- `return 10 + 20` returns the result.
- `call()` can declare checked exceptions.

Compare that with `Runnable`:

```
class PrintTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Task is running");
    }
}
```

The task can print something, update data, or perform an operation, but its `run()` method doesn't return a value to its caller.

### Runnable vs. Callable

| Feature                      | `Runnable`                                             | `Callable<V>`                                |
| ---------------------------- | ------------------------------------------------------ | -------------------------------------------- |
| Method                       | `run()`                                                | `call()`                                     |
| Return value                 | None (`void`)                                          | Returns `V`                                  |
| Checked exceptions           | Cannot declare arbitrary checked exceptions in `run()` | Can declare checked exceptions               |
| Direct use with `Thread`     | Yes                                                    | No, not directly                             |
| Works with `ExecutorService` | Yes                                                    | Yes                                          |
| Result through `Future`      | Not through the `Runnable` return value                | Yes, when submitted using `submit(Callable)` |

Interview takeaway: `Callable` is useful when a task needs to produce a result or propagate a checked exception through its task-execution mechanism.

## 3. Checked vs. unchecked exceptions

Before going further, we need to understand the exception distinction.

### Checked exceptions

Checked exceptions are checked by the compiler. A method must catch them or declare them using `throws`, unless a suitable enclosing construct already handles them.

Examples:

- `IOException`
- `SQLException`
- `InterruptedException`

Imagine reading a file.

```
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileTask {

    void readFile() throws IOException {
        String content = Files.readString(Path.of("data.txt"));
        System.out.println(content);
    }
}
```

The method declares `throws IOException` because file operations can fail for reasons such as access problems or I/O errors.

This does not mean the compiler checks whether the file actually exists at runtime. It checks whether the checked exception is handled or declared.

### Unchecked exceptions

Unchecked exceptions are exceptions that the compiler does not require you to catch or declare.

Examples:

- `NullPointerException`
- `ArrayIndexOutOfBoundsException`
- `ArithmeticException`

```
String name = null;
System.out.println(name.length());
```

This can throw a `NullPointerException` at runtime.

You may handle it, but Java doesn't force you to.

### The important distinction

| Checked                                        | Unchecked                                                              |
| ---------------------------------------------- | ---------------------------------------------------------------------- |
| Compiler requires handling or declaration      | Compiler does not require handling or declaration                      |
| Examples: `IOException`, `SQLException`        | Examples: `NullPointerException`, `ArithmeticException`                |
| Often represents recoverable external failures | Often indicates invalid state or programming errors, though not always |

One correction worth remembering: unchecked exceptions aren't necessarily logical errors, and checked exceptions aren't necessarily actual failures. The distinction is primarily about Java's compile-time handling requirements.

## 4. Why `Callable` works with `ExecutorService`

Here is where the concepts connect.

With the earlier `Runnable` approach, we could do this:

```
Runnable task = new PrintTask();
Thread thread = new Thread(task);
thread.start();
```

But `Callable` cannot be passed directly to the `Thread` constructor because `Thread` accepts a `Runnable`, not a `Callable`.

Instead, we can use `ExecutorService`.

Mental model:

Imagine a company with a team of workers.

- `Callable` represents a job that produces a result.
- `ExecutorService` manages the execution of submitted jobs.
- Worker threads perform those jobs.
- `Future` is your reference to the eventual result.

Callable task

Defines work and returns a value

ExecutorService

Accepts and manages task execution

Worker thread

Executes call()

\*\*Future

This also introduces a useful design principle: you can submit work without manually creating and starting a new thread for each task.

## 5. Understanding `Future`

Suppose you ask a worker to calculate something.

You don't necessarily want to stand beside the worker and wait for the calculation to finish before doing anything else.

Instead, you want a reference that lets you check the result later.

That is the intuition behind `Future`.

A `Future<V>` represents the eventual result of an asynchronous computation.

It lets you:

- Check whether the task has completed.
- Wait for the result.
- Retrieve the result.
- Cancel the task, subject to the task's state and cancellation behavior.

### The most important method: `get()`

```
Integer result = future.get();
```

If the task has not completed, `get()` blocks the calling thread until the result becomes available, the task fails, or the wait is interrupted.

For example, if `main` calls `future.get()`, it is the main thread that may block.

The worker executing the task is not necessarily blocked by this call.

This distinction is important.

## 6. Complete example: `Callable` + `ExecutorService` + `Future`

This example brings everything together.

```
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SumTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("Calculating sum...");
        Thread.sleep(1000);

        return 10 + 20;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService executor =
                Executors.newSingleThreadExecutor();

        try {
            Callable<Integer> task = new SumTask();

            Future<Integer> future = executor.submit(task);

            System.out.println("Task submitted");

            Integer result = future.get();

            System.out.println("Result: " + result);

        } finally {
            executor.shutdown();
        }
    }
}
```

Possible output:

```
Task submitted
Calculating sum...
Result: 30
```

The precise timing of the messages can vary, but the result is `30` if the task completes successfully.

### Execution flow

1. `newSingleThreadExecutor()` creates an executor that uses one worker thread.
2. `new SumTask()` creates the task; it does not start a thread.
3. `executor.submit(task)` submits the task for execution and returns a `Future<Integer>`.
4. The worker eventually executes `call()`.
5. `call()` returns `30`.
6. `future.get()` retrieves that result, waiting if necessary.
7. `shutdown()` prevents new task submissions and allows submitted work to finish.

The executor manages the worker thread for us.

## 7. Does `Future.get()` make the whole task sequential?

Not necessarily.

This is an important subtlety in the instructor's explanation.

Consider:

```
Future<Integer> future1 = executor.submit(task1);
Future<Integer> future2 = executor.submit(task2);

Integer result1 = future1.get();
Integer result2 = future2.get();
```

Both tasks are submitted before either result is retrieved.

If the executor has sufficient worker threads, `task1` and `task2` may execute concurrently.

However, the main thread waits at `future1.get()` before reaching `future2.get()`.

```
Main thread:
Submit task1
    ↓
Submit task2
    ↓
Wait for future1.get()
    ↓
Retrieve result1
    ↓
Wait for future2.get() if necessary
    ↓
Retrieve result2
```

Meanwhile, the worker threads can execute the tasks independently.

Key lesson: Calling `get()` sequentially doesn't mean the tasks themselves must execute sequentially. It means result retrieval by the calling thread is sequential.

If the executor has only one worker, however, its submitted tasks will generally execute one at a time.

## 8. Why does `Callable` return a `Future` instead of the result immediately?

Imagine a task that takes five seconds.

If `submit()` had to wait for the task to finish before returning its result, the calling thread would have to wait at submission time.

Instead, `submit()` can return a `Future` while the task is still executing.

```
Main thread                  Worker thread
    |                             |
    |---- submit(task) ---------->|
    |                             | execute call()
    |<--- Future returned         |
    |                             |
    | do other work               |
    |                             | compute result
    |                             |
    |---- future.get()            |
    |      wait if needed         |
    |<--- result returned --------|
```

This gives the application flexibility: it can perform other work before retrieving the result.

`Future` is not itself the result; it is a handle for interacting with the computation and its eventual outcome.

## 9. What happens when `Callable` throws an exception?

A `Callable` can declare checked exceptions:

```
Callable<Integer> task = () -> {
    throw new Exception("Calculation failed");
};
```

When submitted to an executor, a failure is recorded in the resulting `Future`.

When you call `get()`, the failure is reported through `ExecutionException`.

```
try {
    Integer result = future.get();
} catch (ExecutionException e) {
    System.out.println("Task failed: " + e.getCause());
}
```

This is another reason `Future` matters: it provides a way to observe the task's result or failure from the calling thread.

## 10. Interview revision

Q1. What is the difference between `Runnable` and `Callable`?

`Runnable.run()` returns no result and cannot declare checked exceptions in its signature. `Callable<V>.call()` returns a value of type `V` and can declare checked exceptions.

Q2. Can you pass a `Callable` directly to `new Thread()`?

No. `Thread` accepts a `Runnable`, not a `Callable`. Use an `ExecutorService` to submit a `Callable`.

Q3. What is a `Future`?

A handle representing the outcome of an asynchronous computation, allowing the caller to check completion, retrieve the result, or attempt cancellation.

Q4. What does `Future.get()` do?

It retrieves the result, blocking the calling thread if necessary. It can throw `InterruptedException` or `ExecutionException`.

Q5. What is the difference between checked and unchecked exceptions?

Checked exceptions must be caught or declared; unchecked exceptions do not have that compiler requirement.

Q6. Why use `ExecutorService`?

It manages task execution and worker threads, avoiding the need to manually create a new thread for every submitted task.

## 11. Final mental model

Remember these four responsibilities:

| Component         | Responsibility                          |
| ----------------- | --------------------------------------- |
| `Runnable`        | Defines work without a return value     |
| `Callable<V>`     | Defines work that returns a value       |
| `ExecutorService` | Manages task execution                  |
| `Future<V>`       | Provides access to the eventual outcome |

The complete flow is:

```
Callable<V>
    ↓
ExecutorService.submit()
    ↓
Worker executes call()
    ↓
Future<V>
    ↓
future.get()
    ↓
Result or exception
```

One final distinction: `Future.get()` is a blocking retrieval mechanism. Later, when we study more advanced concurrency, we can compare it with alternatives such as `CompletableFuture`, which supports composing asynchronous operations without requiring every result to be retrieved through a blocking `get()` call.

The takeaway: `Runnable` teaches us how to define a task, `Callable` adds result-producing tasks, and `ExecutorService` with `Future` introduces the foundation of managed asynchronous execution in Java.

# 07. Java Concurrency — Best Practices, Thread Safety & Exception Handling

## 1. Runnable vs. Callable — Final Revision

Both `Runnable` and `Callable` are functional interfaces that represent tasks, but they differ in return values and exception handling.

| Feature | Runnable | Callable\<V> |
|---|---|---|
| Method | `run()` | `call()` |
| Return value | `void` | Returns type `V` |
| Declares checked exceptions | No | Yes |
| Directly accepted by `new Thread(...)` | Yes | No |
| Can be submitted to `ExecutorService` | Yes | Yes |
| Typical use | Perform an action | Perform work and produce a result |

### Runnable example

```java
Runnable task = () -> {
    System.out.println("Sending welcome email");
};

Thread thread = new Thread(task);
thread.start();
```

`Runnable` is suitable when we don't need a result returned through the task's method.

### Callable example

```java
Callable<Integer> task = () -> {
    return 10 + 20;
};

ExecutorService executor = Executors.newSingleThreadExecutor();

try {
    Future<Integer> future = executor.submit(task);
    System.out.println(future.get()); // 30
} finally {
    executor.shutdown();
}
```

`Callable<Integer>` means the task produces an `Integer` result. The `Future<Integer>` represents the eventual outcome of that submitted task.

**Important:** `Runnable` can still modify shared state, write to a database, or throw unchecked exceptions. It simply doesn't return a value through `run()`.

Also, use `Callable` when you need a result or want to declare checked exceptions—not because it is inherently better than `Runnable`.

## 2. Checked vs. Unchecked Exceptions

### Checked exceptions

The compiler requires checked exceptions to be caught or declared with `throws`.

Examples:
- `IOException`
- `SQLException`
- `InterruptedException`

### Unchecked exceptions

The compiler does not require them to be caught or declared.

Examples:
- `NullPointerException`
- `ArithmeticException`
- `IllegalArgumentException`

Unchecked exceptions can be anticipated too. The distinction is based on Java's exception type hierarchy and compiler rules, not simply whether an exception might occur at runtime.

### Why does Runnable have a limitation?

The `Runnable` interface declares:

```java
public interface Runnable {
    void run();
}
```

Therefore, an implementation cannot add an arbitrary checked exception to the method signature.

This does not compile:

```java
Runnable task = () -> {
    throw new IOException("File error"); // Compilation error
};
```

Instead, handle the checked exception inside `run()`:

```java
Runnable task = () -> {
    try {
        Files.readString(Path.of("data.txt"));
    } catch (IOException e) {
        System.err.println("Unable to read file: " + e.getMessage());
    }
};
```

Alternatively, use `Callable` when you want the exception to propagate through `call()`:

```java
Callable<String> task = () ->
    Files.readString(Path.of("data.txt"));
```

`Callable.call()` declares `throws Exception`, allowing checked exceptions to propagate from the task.

When submitted to an `ExecutorService`, a failure is recorded in the returned `Future`. Calling `get()` on that future typically exposes the failure through an `ExecutionException`, whose cause is the original exception.

## 3. Synchronization and Performance

Synchronization allows threads to coordinate access to shared mutable state.

However, synchronization has costs:
- Threads may block while waiting for a lock.
- Contention can reduce throughput.
- Poorly designed locking can lead to deadlocks.
- Excessive synchronization can limit parallel execution.

### Practical principle

**Keep critical sections small, but do not sacrifice correctness just to reduce locking.**

For example:

```java
class Counter {
    private int count;

    public synchronized void increment() {
        count++;
    }
}
```

Here, synchronization protects the increment operation against concurrent updates to the same counter.

If the application requires more sophisticated coordination, other options may be appropriate, including `ReentrantLock`, atomic variables, or concurrent collections.

Do not remove synchronization simply because it looks expensive. First establish correctness, then measure and optimize.

## 4. Handling Interruptions Correctly

Java uses interruption as a cooperative mechanism for requesting that a thread stop what it is doing or respond to cancellation.

For example, `Thread.sleep()` throws `InterruptedException` when the sleeping thread is interrupted.

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return;
}
```

In this example, the code restores the interrupt status and exits the task.

Why restore the status? Catching `InterruptedException` clears the thread's interrupt status. If the current method cannot propagate the exception, restoring the status allows higher-level code to observe the interruption.

When a method can propagate `InterruptedException`, declaring and rethrowing it is often appropriate.

**Important:** Never automatically swallow interruptions with an empty catch block. Doing so can interfere with cancellation and graceful shutdown.

## 5. Thread Starvation

Thread starvation occurs when a thread repeatedly fails to obtain the CPU time, lock, or other resource it needs to make progress.

Imagine a team where one person is repeatedly denied the opportunity to work. Even if everyone else is productive, that person's task remains unfinished.

The same idea applies to threads.

Potential causes include:
- Unfair lock acquisition.
- Excessive contention.
- Poor thread-pool configuration.
- Long-running tasks occupying all available workers.
- Scheduling priorities that disadvantage some threads.

### How can starvation be reduced?

- Avoid holding locks longer than necessary.
- Use thread pools appropriate to the workload.
- Avoid blocking operations that occupy all workers needed for other tasks.
- Consider fair locking where fairness is required, understanding its performance trade-offs.
- Monitor queue lengths, task latency, and worker utilization.

Thread priorities are not a reliable universal solution. The JVM and operating system ultimately influence scheduling, and increasing a thread's priority does not guarantee fairness.

## 6. High-Level Concurrency Utilities

Java provides utilities in `java.util.concurrent` so that developers do not need to implement every concurrency mechanism manually.

Examples include:

- `ExecutorService` — manages task execution and worker threads.
- `Future` — represents an eventual task outcome.
- `BlockingQueue` — supports thread-safe producer-consumer workflows.
- `ConcurrentHashMap` — supports concurrent access to a map.
- `AtomicInteger` — supports atomic integer operations.
- `CountDownLatch` — lets threads wait for a set of operations to complete.
- `ReentrantLock` — provides explicit locking and additional coordination features.

The right utility depends on the problem. These utilities reduce the amount of low-level coordination you must implement yourself, but they do not automatically make every surrounding operation thread-safe.

## 7. `start()` vs. `run()`

This is one of the most important distinctions in Java threading.

### Calling `start()`

```java
Thread t1 = new Thread(() -> {
    System.out.println("Executing task");
});

t1.start();
```

Calling `start()` schedules the thread for execution. The JVM arranges for its `run()` method to execute on the new thread.

The exact time at which it starts running is not guaranteed.

### Calling `run()` directly

```java
Thread t1 = new Thread(() -> {
    System.out.println("Executing task");
});

t1.run();
```

Calling `run()` directly is an ordinary method invocation. It does not start a new thread. The task executes on whichever thread called `run()`—the main thread in this example.

### Mental model

- `start()` → initiate a separate thread of execution.
- `run()` → execute the method on the current thread.

### Can a thread be started twice?

No.

```java
Thread t1 = new Thread(() -> {
    System.out.println("Hello");
});

t1.start();
t1.start(); // IllegalThreadStateException
```

A `Thread` instance can be started only once, even if its first execution has already completed.

If the same task needs to execute again, create a new `Thread` instance or submit the task again to an appropriate executor.

## 8. What Is Thread Safety?

A component is thread-safe when it behaves correctly under the concurrent access patterns it is designed to support.

Suppose two threads execute:

```java
count++;
```

Even though this looks like one operation, incrementing a normal integer involves reading, calculating, and writing a value. Two threads can interfere with one another, causing lost updates.

This is a race condition.

Thread safety is not achieved merely by using multiple threads. It requires the program's shared-state and coordination rules to remain correct under concurrency.

### Common techniques

**1. Synchronization**

Protect shared mutable state with appropriate locking.

**2. Immutability**

Immutable objects cannot have their state changed after construction, reducing the need for coordination around that state.

**3. Concurrent collections**

Use suitable implementations such as `ConcurrentHashMap` for concurrent access.

**4. Atomic variables**

Use classes such as `AtomicInteger` for supported atomic operations.

**5. ThreadLocal**

Use `ThreadLocal` when each thread needs its own independent value rather than a single shared value.

`ThreadLocal` is not a general replacement for synchronization. With thread pools, values may remain associated with reused worker threads, so cleanup can be necessary.

The correct technique depends on the data, operations, and concurrency requirements.

## 9. What Happens When an Exception Occurs in a Thread?

Consider two threads:
- The main thread.
- A child thread named `T1`.

```java
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            throw new RuntimeException("Something failed");
        });

        t1.start();

        System.out.println("Main thread is running");
    }
}
```

The child thread throws an unchecked exception that is not caught.

What happens?

1. `T1` terminates because of the uncaught exception.
2. The JVM reports the exception through the thread's uncaught-exception handling mechanism, usually printing a stack trace.
3. The exception does not automatically propagate into the main thread.
4. The main thread can continue executing independently.

The exact console output order is not guaranteed. The main thread's message may appear before or after the exception report.

**Important correction:** An uncaught exception in a child thread does not automatically terminate the entire JVM. The JVM can continue while other threads remain active. However, if no non-daemon threads remain, the JVM may exit.

### How can we handle the exception?

For a task executed directly by a `Thread`, we can catch exceptions inside the task:

```java
Thread t1 = new Thread(() -> {
    try {
        throw new RuntimeException("Something failed");
    } catch (RuntimeException e) {
        System.err.println("Task failed: " + e.getMessage());
    }
});

t1.start();
```

Alternatively, an `UncaughtExceptionHandler` can observe uncaught exceptions from threads. When using `ExecutorService`, task exceptions submitted through `submit()` are generally captured in the associated `Future` and should be inspected through it.

## 10. Interview Revision

1. Why can `Callable` declare checked exceptions while `Runnable` cannot?
2. What is the difference between calling `start()` and calling `run()`?
3. Can you restart a `Thread` instance after it finishes?
4. What is thread safety, and why can `count++` be unsafe?
5. What causes thread starvation, and how can it be reduced?
6. What happens when an uncaught exception occurs in a child thread?
7. Why should interruption be handled carefully?
8. When would you choose an atomic variable instead of synchronization?
9. Why might `ThreadLocal` require cleanup when used with thread pools?
10. Why are high-level concurrency utilities usually preferable to implementing synchronization from scratch?

## Final Mental Model

- **Task abstraction:** `Runnable` and `Callable`.
- **Execution:** `Thread` and `ExecutorService`.
- **Result and failure retrieval:** `Future`.
- **Coordination:** Locks, synchronization, latches, and blocking queues.
- **Shared-state safety:** Immutability, atomics, and concurrent collections.
- **Lifecycle and cancellation:** `start()`, interruption, and proper shutdown.
- **Failure handling:** Catch exceptions when appropriate and observe failures from asynchronous tasks.

The objective is not to memorize every concurrency class. It is to understand what problem each abstraction solves and then choose the simplest correct tool for the situation.