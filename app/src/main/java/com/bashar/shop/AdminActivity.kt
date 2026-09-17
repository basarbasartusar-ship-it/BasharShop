package com.bashar.shop

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        findViewById<Button>(R.id.btnAddProduct).setOnClickListener {
            val name = findViewById<EditText>(R.id.etProductName).text.toString().trim()
            val price = findViewById<EditText>(R.id.etPrice).text.toString().toDoubleOrNull()
            val category = findViewById<EditText>(R.id.etCategory).text.toString().trim()
            val image = findViewById<EditText>(R.id.etImageUrl).text.toString().trim()
            val desc = findViewById<EditText>(R.id.etDescription).text.toString().trim()
            if (name.isEmpty() || price == null) {
                Toast.makeText(this, "Product name and price are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val data = hashMapOf("name" to name, "price" to price, "category" to category,
                "imageUrl" to image, "description" to desc)
            db.collection("products").add(data).addOnSuccessListener {
                findViewById<TextView>(R.id.tvStatus).text = "Product added successfully."
            }.addOnFailureListener {
                findViewById<TextView>(R.id.tvStatus).text = "Error: ${it.message}"
            }
        }
        findViewById<Button>(R.id.btnOrders).setOnClickListener {
            db.collection("orders").get().addOnSuccessListener { s ->
                val text = s.documents.joinToString("\n\n") { d ->
                    "${d.getString("name")} | ${d.getString("phone")} | ${d.getString("status")} | ৳${d.getDouble("total") ?: 0}"
                }
                findViewById<TextView>(R.id.tvStatus).text = if (text.isBlank()) "No orders" else text
            }
        }
    }
}
