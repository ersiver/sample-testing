# sample-testing
This app was built following AndroidDevelopers Codelabs. The app demonstrates:

+ A collection of unit, integration and e2e tests
+ Difference between local and instrumentation tests
+ Difference between unit and integration tests
+ Espresso and Mockito Frameworks
+ Concept of Test Driven Development
+ Setting up manual dependency injection
+ Creating ServiceLocators 
+ Creating fakes and mocks
+ Concept of AndroidX Test and Robolectric to get a simulated Android environment
+ Using runBlocking and runBlockingTest
+ Testing LiveData
+ Writing unit tests for a repository and view model using fakes and dependency injection
+ Writing integration tests for the app-level navigation.
+ Writing integration tests for fragments and view models interactions using Espresso and Mockito frameworks.


# Cheat sheet

## 1. Types of tests depending on the way they are run:
<b>1.1. Local tests (test source set)</b>
<br>These tests are run locally on your development machine's JVM and do not require an emulator or physical device. Because of this, they run fast, but their fidelity is lower, meaning they act less like they would in the real world.

<b>1.2 Instrumented tests (androidTest source set)</b>
<br>These tests run on real or emulated Android devices, so they reflect what will happen in the real world, but are also much slower (If you are testing something visual like Fragments, run it as an instrumented test to render them on a screen).

## 2. Types of tests depending on the subject under testing:
<b>2.1 Unit tests</b>
<br>These are highly focused tests that run on a single class, usually a single method in that class. If a unit test fails, you can know exactly where in your code the issue is. They have low fidelity since in the real world, your app involves much more than the execution of one method or class. They are fast enough to run every time you change your code. They will most often be locally run tests (in the test source set).

<b>2.2 Integration tests </b>
<br>These tests verify interaction between parts of your program to make sure they behave as expected when used together. 
These tests can be run either locally (test source set) or as instrumentation tests (androidTest source set).

<b>2.3 End to end tests (E2E)  </b>
<br>These tests verify features. They test large portions of the app, simulate real usage closely, and therefore are usually slow. They tell you that your application actually works as a whole. These tests have the highest fidelity, are often instrumented, and may take longer to run.

The suggested proportion of these tests is often represented by a pyramid, with the vast majority of tests being unit tests: unit tests 70%, integration tests 20%, E2E tests 10%.

## 3. Test Doubles 
To isolate parts of your app for testing, you can use test doubles. A test double is a version of a class crafted specifically for testing. For example, you fake getting data from a database or the internet. Examples of test doubles are: fake, mocks, stubs, dummies and spies.

<b>Mock:</b> A test double that checks whether specific methods were called correctly.

<b>Fake:</b> A test double that has a "working" implementation of the class, but it's implemented in a way that makes it good for tests but unsuitable for production.

<b>Stub:</b> A test double that includes no logic and only returns what you program it to return. A StubTaskRepository could be programmed to return certain combinations of tasks from getTasks for example.

<b>Dummy:</b> A test double that is passed around but not used, such as if you just need to provide it as a parameter. If you had a NoOpTaskRepository, it would just implement the TaskRepository with no code in any of the methods.

<b>Spy:</b> A test double which also keeps tracks of some additional information; for example, if you made a SpyTaskRepository, it might keep track of the number of times the addTask method was called.

The most common test doubles used in Android are <b>Fakes</b> and <b>Mocks</b>.

## 4. Mockito
+ Mockito is a popular mock framework that allows to create and configure mock objects. 

+ Example usage of Mockito is to write testing for Navigation:
 <br>a) Use Mockito to create a NavController mock
 <br>b) Attach that mocked NavController to the fragment
 <br>c) Verify that navigate was called with the correct action and parameter(s) with Mockito's verify method

## 5. Espresso
Espresso is a testing framework for Android to make it easy to write reliable user interface tests.
+ Espresso helps to interact with views, like clicking buttons, sliding a bar, or scrolling down a screen and to assert that certain views are on screen or are in a certain state (such as containing particular text, or that a checkbox is checked, etc.)

+ Espresso tests run on a real device and thus are instrumentation tests by nature.

+ Espresso statements are made up of four parts:
 <br>a) Static Espresso method (e.g. onView, onData) 
 <br>b) ViewMatcher (e.g. withId)
 <br>c) ViewAction (something that can be done to the view, e.g.clicking the view) 
 <br>d) ViewAssertion (check or asserts something about the view. The most common ViewAssertion is the matches assertion).

+ Statement Example: 
  <br><i>onView(withId(R.id.task_detail_complete_checkbox)).perform(click()).check(matches(isChecked())) </i> 
  <br>The above statement finds the checkbox view with the id task_detail_complete_checkbox, clicks it, then asserts that it is checked.

+ Note that you don't always call both perform and check in an Espresso statement. You can have statements that just make an assertion using check or just do a ViewAction using perform:
  <br><i>onView(withId(R.id.tasks_list))
  .perform(RecyclerViewActions.actionOnItem<RecyclerView
  .ViewHolder>(hasDescendant(withText("TITLE1")),  click())))</i> <br>That statement only finds the item in the RecyclerView that has the text "TITLE1" and click it.


+ For Espresso UI testing, it's a best practice to turn animations off before implementing anything else.Disable: Window animation scale, Transition animation scale, and Animator duration scale.

+ Espresso’s main advantage over other UI testing frameworks is that it synchronizes with your app. Where Espresso cannot tell whether the app is busy updating the UI or not, you can use the idling resource synchronization mechanism. (e.g. Snackbar situation -> Espresso doesn’t really know when your app is idle because it only sees 15 ms in the future and Snackbar.LENGTH_SHORT is 2 seconds. 

## 6. Testing & coroutines

+ Code executes either synchronously or asynchronously. When code is running <b>synchronously</b>, a task completely finishes before execution moves to the next task. When code is running <b>asynchronously</b>, tasks run in parallel. 

+ Asynchronous code is almost always used for long-running tasks, such as network or database calls. It can also be difficult to test. Asynchronous code tends to be <b>non-deterministic</b>. What this means is that if a test runs operations A and B in parallel, multiple times, sometimes A will finish first, and sometimes B. This can cause <b>flaky</b> tests (tests with inconsistent results). When testing, you often need to ensure some sort of synchronization mechanism for asynchronous code. Synchronization mechanisms are ways to tell the test execution to "wait" until the asynchronous work finishes.

+ In Kotlin, a common mechanism for running code asynchronously is coroutines. When testing asynchronous code, you need to make your code deterministic and provide <b>synchronization mechanisms</b>. The following methodologies help with that:

    a) Using <b>runBlockingTest</b> or <b>runBlocking</b>. Use runBlockingTest whenever you want to run a coroutine from a test. Usually,  this is when you need to call a suspend function from a test. When writing test doubles, use runBlocking.
    
    b) Using TestCoroutineDispatcher for local tests.
    
    c) Pausing coroutine execution to test the state of the code at an exact place in time.

+ <b>runBlockingTest</b> takes in a block of code and blocks the test thread until all of the coroutines it starts are finished. It also runs the code in the coroutines immediately (skipping any calls to delay) and in the order they are called–-in short, it runs them in a deterministic order.(runBlockingTest essentially makes your coroutines run like non-coroutines by giving you a coroutine context specifically for test code.)

## 7. Testing database
+ In general, make database tests instrumented tests, meaning they will be in the androidTest source set. This is because, if you run these tests locally, they will use whatever version of SQLite you have on your local machine, which could be very different from the version of SQLite that ships with your Android device. Different Android devices also ship with different SQLite versions, so it's helpful as well to be able to run these tests as instrumented tests on different devices.

+ </b>When initializing a database for testing:</b>

    a) Create an in-memory database using Room.inMemoryDatabaseBuilder. Normal databases are meant to persist. By comparison, an in-memory database will be completely deleted once the process that created it is killed, since it's never actually stored on disk. Always use and in-memory database for your tests.
    
    b) Use the AndroidX Test libraries' ApplicationProvider.getApplicationContext() method to get the application context.
    
    c) Run the test using runBlockingTest because both insertTask and getTaskById are suspend functions.
    
    d) You use the DAO as normal, accessing it from your database instance.
    
## TDD - Test Driven Development

+ Write the test for a new feature before implementing the feature itself
+ Run the tests a first time - it will fail as the feature is not yet implemented
+ Implement the feature
+ Run the test again and check that it pass
+ In this way you are sure that the test actually tests your code.

This is especially a good process to follow when fixing a bug in a feature:
+ Implement a test that exposes the bug (the test fails)
+ Fix the bug in the codebase
+ Check that the test is now running successfully

## License
Copyright 2019 Google, Inc (all resources are from AndroidDevelopers Codelabs).

