package com.bashar.shop

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val recycler = findViewById<RecyclerView>(R.id.recyclerCart)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CartAdapter(CartManager.items)
        findViewById<TextView>(R.id.tvTotal).text = "Total: ৳ %.2f".format(CartManager.total())
        findViewById<Button>(R.id.btnCheckout).setOnClickListener {
            if (CartManager.items.isEmpty()) Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            else startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }
}
