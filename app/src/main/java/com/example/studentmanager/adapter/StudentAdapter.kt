package com.example.studentmanager.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.studentmanager.R
import com.example.studentmanager.model.Student

class StudentAdapter(context: Context, private val resource: Int, private val students: MutableList<Student>) :
    ArrayAdapter<Student>(context, resource, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val student = students[position]

        val textViewFullName = view.findViewById<TextView>(R.id.textViewFullName)
        val textViewStudentId = view.findViewById<TextView>(R.id.textViewStudentId)

        textViewFullName.text = student.fullName
        textViewStudentId.text = student.studentId

        return view
    }
}
