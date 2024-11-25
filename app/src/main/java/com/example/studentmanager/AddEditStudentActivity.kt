// AddEditStudentActivity.kt
package com.example.studentmanager

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var editTextFullName: EditText
    private lateinit var editTextStudentId: EditText
    private lateinit var buttonSave: Button

    private var isEditMode: Boolean = false
    private var editPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Đặt layout
        setContentView(R.layout.activity_add_edit_student)

        // Khởi tạo View
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextStudentId = findViewById(R.id.editTextStudentId)
        buttonSave = findViewById(R.id.buttonSave)

        // Kiểm tra xem có phải chỉnh sửa hay thêm mới
        isEditMode = intent.getBooleanExtra("isEdit", false)
        if(isEditMode){
            val fullName = intent.getStringExtra("studentName") ?: ""
            val studentId = intent.getStringExtra("studentId") ?: ""
            editPosition = intent.getIntExtra("position", -1)

            editTextFullName.setText(fullName)
            editTextStudentId.setText(studentId)
            buttonSave.text = "Update"
        }

        // Xử lý khi nhấn nút Save
        buttonSave.setOnClickListener {
            val fullName = editTextFullName.text.toString().trim()
            val studentId = editTextStudentId.text.toString().trim()

            if(fullName.isEmpty()){
                editTextFullName.error = "Họ tên không được để trống"
                return@setOnClickListener
            }

            if(studentId.isEmpty()){
                editTextStudentId.error = "MSSV không được để trống"
                return@setOnClickListener
            }

            val resultIntent = intent
            resultIntent.putExtra("fullName", fullName)
            resultIntent.putExtra("studentId", studentId)

            if(isEditMode && editPosition != -1){
                resultIntent.putExtra("position", editPosition)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
