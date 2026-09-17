package com.bashar.shop

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val pass = findViewById<EditText>(R.id.etPassword).text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    FirebaseFirestore.getInstance().collection("admins").document(uid).get()
                        .addOnSuccessListener { d ->
                            if (d.getBoolean("admin") == true) {
                                startActivity(Intent(this, AdminActivity::class.java))
                                finish()
                            } else Toast.makeText(this, "Not an admin account", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_LONG).show() }
        }
    }
}
