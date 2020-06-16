/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.tasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Integration test for the Task List screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class TasksFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    // Navigation test to check, that when you click on a task in the task list,
    //it takes you to the correct TaskDetailFragment.
    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        repository.saveTask(Task("TITLE1", "DESCRIPTION1", false, "id1"))
        repository.saveTask(Task("TITLE2", "DESCRIPTION2", true, "id2"))

        //1. GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)

        //Create a mock with Mockito, passing the class you want to mock:
        val navController = mock(NavController::class.java)

        //Associate mock-NavController with the TaskFragment.
        scenario.onFragment { taskFragment ->
            Navigation.setViewNavController(taskFragment.view!!, navController)
        }

        //2. WHEN - Click on the first list item
        ///Use Espresso statement to do the do a ViewAction using perform:
        // find the item in the RecyclerView that has the text "TITLE1" and click it
        onView(withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("TITLE1")), click()))

        //3. THEN - Verify that we navigate to the first detail screen
        //
        //Mockito's verify method is what makes this a mock—you're able to confirm the mocked
        // navController called a specific method (navigate) with a parameter
        // (actionTasksFragmentToTaskDetailFragment with the ID of "id1").
        verify(navController).navigate(
                TasksFragmentDirections
                        .actionTasksFragmentToTaskDetailFragment("id1")
        )
    }

    //Navigation test to check, that if you click on the + FAB,
    // you navigate to the AddEditTaskFragment.
    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {
        val newTask = Task("TITLE1", "DESCRIPTION1", false, "id1")

        //1. GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment { taskFragment ->
            Navigation.setViewNavController(taskFragment.view!!, navController)
        }
        //2. WHEN - Click on the + FAB
        onView(withId(R.id.add_task_fab)).perform(click())

        //3. THEN - Verify that we navigate to the add screen
        verify(navController).navigate(
                TasksFragmentDirections
                        .actionTasksFragmentToAddEditTaskFragment(null, getApplicationContext<Context>().getString(R.string.add_task))
        )
    }
}

//Writing tests for navigations:
//1. Use Mockito to create a NavController mock.
//2. Attach that mocked NavController to the fragment.
//3. Verify that navigate was called with the correct action and parameter(s).