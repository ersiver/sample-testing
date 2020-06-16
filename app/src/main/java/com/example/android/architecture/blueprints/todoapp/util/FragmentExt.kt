package com.example.android.architecture.blueprints.todoapp.util

import androidx.fragment.app.Fragment
import com.example.android.architecture.blueprints.todoapp.TodoApplication
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory

//To avoid building a seperate factory for each view model use this extension function .
fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as TodoApplication).taskRepository
    return ViewModelFactory(repository)
}