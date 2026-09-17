package com.bashar.shop

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val products = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerProducts)
        adapter = ProductAdapter(products,
            { p -> startActivity(Intent(this, ProductDetailsActivity::class.java).putExtra("id", p.id)) },
            { p -> CartManager.add(p); Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show() }
        )
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        findViewById<Button>(R.id.btnCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val search = findViewById<EditText>(R.id.etSearch)
        search.setOnEditorActionListener { _, _, _ ->
            val q = search.text.toString().trim().lowercase()
            adapter.submit(products.filter { it.name.lowercase().contains(q) || it.category.lowercase().contains(q) })
            true
        }

        loadProducts()
    }

    private fun loadProducts() {
        db.collection("products").get().addOnSuccessListener { snap ->
            products.clear()
            for (d in snap.documents) {
                products.add(Product(
                    d.id, d.getString("name") ?: "",
                    d.getDouble("price") ?: 0.0,
                    d.getString("category") ?: "",
                    d.getString("imageUrl") ?: "",
                    d.getString("description") ?: ""
                ))
            }
            adapter.submit(products.toList())
        }
    }
}
