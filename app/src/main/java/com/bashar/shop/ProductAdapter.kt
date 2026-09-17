package com.bashar.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ProductAdapter(
    private var list: List<Product>,
    private val onClick: (Product) -> Unit,
    private val onAdd: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgProduct)
        val name: TextView = v.findViewById(R.id.tvName)
        val price: TextView = v.findViewById(R.id.tvPrice)
        val add: Button = v.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_product, p, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        val x = list[pos]
        h.name.text = x.name
        h.price.text = "৳ %.2f".format(x.price)
        h.img.load(x.imageUrl)
        h.itemView.setOnClickListener { onClick(x) }
        h.add.setOnClickListener { onAdd(x) }
    }

    fun submit(newList: List<Product>) { list = newList; notifyDataSetChanged() }
}
