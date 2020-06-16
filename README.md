# sample-testing
This app was built following AndroidDevelopers Codelabs. The app demonstrates:

+ Difference between local and instrumentation tests
+ Difference between unit and integration tests
+ Basics of Espresso and Mockito
+ Concept of Test Driven Development
+ Setting up manual dependency injection
+ Creating ServiceLocators 
+ Creating fakes and mocks
+ Concept of AndroidX Test and Robolectric to get a simulated Android environment
+ Using runBlocking and runBlockingTest
+ Testing LiveData
+ Writing unit tests for a repository and view model using fakes and dependency injection
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

## License
Copyright 2019 Google, Inc (all resources are from AndroidDevelopers Codelabs).

