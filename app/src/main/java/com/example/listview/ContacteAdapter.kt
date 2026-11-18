package com.example.listview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class ContacteAdapter(
    private val context: Context,
    private val contactes: List<Contacte>
) : ArrayAdapter<Contacte>(context, 0, contactes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_contacte, parent, false)
        val contacte = contactes[position]

        val tvNom = view.findViewById<TextView>(R.id.tvItemNom)
        val tvTelefon = view.findViewById<TextView>(R.id.tvItemTelefon)
        val tvEmail = view.findViewById<TextView>(R.id.tvItemEmail)
        val btnTrucar = view.findViewById<ImageButton>(R.id.btnItemTrucar)
        val btnEmail = view.findViewById<ImageButton>(R.id.btnItemEmail)

        tvNom.text = "${contacte.nom} ${contacte.cognom}".trim()
        tvTelefon.text = contacte.telefon
        tvEmail.text = contacte.mail

        btnTrucar.setOnClickListener {
            if (contacte.telefon.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contacte.telefon}"))
                context.startActivity(intent)
            }
        }

        btnEmail.setOnClickListener {
            if (contacte.mail.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${contacte.mail}"))
                context.startActivity(intent)
            }
        }

        return view
    }
}