package com.example.android.architecture.blueprints.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskViewModel
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsViewModel
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailViewModel
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel


/**
 * Factory for all ViewModels
 */
class ViewModelFactory(private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(StatisticsViewModel::class.java)
                    -> StatisticsViewModel(tasksRepository)

                    isAssignableFrom(TaskDetailViewModel::class.java)
                    -> TaskDetailViewModel(tasksRepository)

                    isAssignableFrom(TasksViewModel::class.java)
                    -> TasksViewModel(tasksRepository)

                    isAssignableFrom(AddEditTaskViewModel::class.java)
                    -> AddEditTaskViewModel(tasksRepository)

                    else ->
                        throw IllegalArgumentException("Unknown view movel class: ${modelClass.name}")
                }
            } as T
}
