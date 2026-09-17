package com.bashar.shop

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.firebase.firestore.FirebaseFirestore

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val id = intent.getStringExtra("id") ?: return
        FirebaseFirestore.getInstance().collection("products").document(id).get()
            .addOnSuccessListener { d ->
                val p = Product(d.id, d.getString("name") ?: "", d.getDouble("price") ?: 0.0,
                    d.getString("category") ?: "", d.getString("imageUrl") ?: "", d.getString("description") ?: "")
                findViewById<ImageView>(R.id.imgProduct).load(p.imageUrl)
                findViewById<TextView>(R.id.tvName).text = p.name
                findViewById<TextView>(R.id.tvPrice).text = "৳ %.2f".format(p.price)
                findViewById<TextView>(R.id.tvDescription).text = p.description
                findViewById<Button>(R.id.btnAdd).setOnClickListener {
                    CartManager.add(p)
                    Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
