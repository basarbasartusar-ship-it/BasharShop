package com.bashar.shop

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        findViewById<Button>(R.id.btnPlaceOrder).setOnClickListener {
            val name = findViewById<EditText>(R.id.etName).text.toString().trim()
            val phone = findViewById<EditText>(R.id.etPhone).text.toString().trim()
            val address = findViewById<EditText>(R.id.etAddress).text.toString().trim()
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val order = hashMapOf(
                "orderId" to UUID.randomUUID().toString(),
                "name" to name, "phone" to phone, "address" to address,
                "total" to CartManager.total(), "status" to "Pending",
                "items" to CartManager.items.map { mapOf("productId" to it.product.id, "name" to it.product.name, "quantity" to it.quantity, "price" to it.product.price) },
                "userId" to (FirebaseAuth.getInstance().currentUser?.uid ?: "guest"),
                "createdAt" to System.currentTimeMillis()
            )
            FirebaseFirestore.getInstance().collection("orders").add(order)
                .addOnSuccessListener {
                    CartManager.clear()
                    Toast.makeText(this, "Order placed successfully", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
        }
    }
}
