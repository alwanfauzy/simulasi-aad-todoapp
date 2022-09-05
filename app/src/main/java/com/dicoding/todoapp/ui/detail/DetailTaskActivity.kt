package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskAdapter
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val taskId = intent.getIntExtra(TASK_ID, 0)
        setupViewModel(taskId)
    }

    private fun setupViewModel(taskId: Int) {
        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        detailTaskViewModel.setTaskId(taskId)
        detailTaskViewModel.task.observe(this, Observer(this::populateDetailTask))

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            detailTaskViewModel.deleteTask()
        }
    }

    private fun populateDetailTask(task: Task) {
        val detailEdTitle = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val detailEdDescription = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val detailEdDueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

        task.apply {
            detailEdTitle.setText(title)
            detailEdDescription.setText(description)
            detailEdDueDate.setText(DateConverter.convertMillisToString(dueDateMillis))
        }
    }
}