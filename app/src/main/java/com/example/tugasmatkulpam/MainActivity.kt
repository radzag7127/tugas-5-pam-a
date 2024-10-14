package com.example.tugasmatkulpam

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //Rayza Glagah Sudharma NIM 225150400111022

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private var dataList = ArrayList<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        dataList.addAll(databaseHelper.getAllData())

        recyclerView = findViewById(R.id.recyclerView)
        adapter = RecyclerViewAdapter(dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener { showAddItemDialog() }
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    addNewData(title, description)
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun addNewData(name: String, description: String) {
        val id = databaseHelper.insertData(name, description)
        if (id != -1L) {
            dataList.add(DataModel(name, description))
            adapter.notifyItemInserted(dataList.size - 1)
            Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to Add Data", Toast.LENGTH_SHORT).show()
        }
    }
}
