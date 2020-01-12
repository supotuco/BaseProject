package com.supotuco.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.supotuco.baseproject.employeelistview.EmployeeListView

class EmployeeDirectoryView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(
                        R.id.fragment_container,
                        EmployeeListView(),
                        EmployeeListView.FRAGMENT_TAG
                )
                .commit()
    }
}
