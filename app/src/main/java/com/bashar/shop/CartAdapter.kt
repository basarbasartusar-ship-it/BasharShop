package com.bashar.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val list: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.VH>() {
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvName)
        val qty: TextView = v.findViewById(R.id.tvQty)
        val price: TextView = v.findViewById(R.id.tvPrice)
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_cart, p, false))
    override fun getItemCount() = list.size
    override fun onBindViewHolder(h: VH, i: Int) {
        val x = list[i]
        h.name.text = x.product.name
        h.qty.text = "x${x.quantity}"
        h.price.text = "৳ %.2f".format(x.product.price * x.quantity)
    }
}
