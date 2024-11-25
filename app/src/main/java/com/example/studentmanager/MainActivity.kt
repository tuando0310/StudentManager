// MainActivity.kt
package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentmanager.adapter.StudentAdapter
import com.example.studentmanager.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var listViewStudents: ListView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    private val ADD_STUDENT_REQUEST_CODE = 1
    private val EDIT_STUDENT_REQUEST_CODE = 2

    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Đặt layout
        setContentView(R.layout.activity_main)

        // Thiết lập Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        listViewStudents = findViewById(R.id.listViewStudents)

        studentList.add(Student("Nguyễn Văn A", "SV001"))
        studentList.add(Student("Trần Thị B", "SV002"))


        studentAdapter = StudentAdapter(this, R.layout.item_student, studentList)
        listViewStudents.adapter = studentAdapter


        registerForContextMenu(listViewStudents)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_add_new -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if(v?.id == R.id.listViewStudents){
            menuInflater.inflate(R.menu.context_menu, menu)
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            selectedPosition = info.position
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_edit -> {
                val student = studentList[selectedPosition]
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("isEdit", true)
                intent.putExtra("studentName", student.fullName)
                intent.putExtra("studentId", student.studentId)
                intent.putExtra("position", selectedPosition)
                startActivityForResult(intent, EDIT_STUDENT_REQUEST_CODE)
                true
            }
            R.id.menu_remove -> {
                // Xác nhận trước khi xóa
                AlertDialog.Builder(this)
                    .setTitle("Xóa sinh viên")
                    .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
                    .setPositiveButton("Có") { _, _ ->
                        studentList.removeAt(selectedPosition)
                        studentAdapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Không", null)
                    .show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null){
            val fullName = data.getStringExtra("fullName") ?: ""
            val studentId = data.getStringExtra("studentId") ?: ""

            if(requestCode == ADD_STUDENT_REQUEST_CODE){
                // Thêm sinh viên mới
                studentList.add(Student(fullName, studentId))
                studentAdapter.notifyDataSetChanged()
            } else if(requestCode == EDIT_STUDENT_REQUEST_CODE){
                // Cập nhật sinh viên
                val position = data.getIntExtra("position", -1)
                if(position != -1){
                    studentList[position].fullName = fullName
                    studentList[position].studentId = studentId
                    studentAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
