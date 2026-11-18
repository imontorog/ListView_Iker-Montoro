package com.example.listview

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var lvContactes: ListView
    private lateinit var adapter: ContacteAdapter
    private val llistaContactes = mutableListOf<Contacte>()

    private val detallLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val data = result.data!!
            val posicio = data.getIntExtra("POSICIO", -1)
            val esborrar = data.getBooleanExtra("ESBORRAR", false)

            if (esborrar && posicio != -1) {
                llistaContactes.removeAt(posicio)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, getString(R.string.msg_deleted), Toast.LENGTH_SHORT).show()
            } else {
                val contacte = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data.getSerializableExtra("CONTACTE", Contacte::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    data.getSerializableExtra("CONTACTE") as? Contacte
                }

                if (contacte != null) {
                    if (!contacte.telefon.startsWith("+34")) {
                        contacte.telefon = "+34 ${contacte.telefon}"
                    }

                    if (posicio == -1) {
                        llistaContactes.add(contacte)
                    } else {
                        llistaContactes[posicio] = contacte
                    }
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, getString(R.string.msg_saved), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llistaContactes.add(Contacte("Pol", "Giménez", "Carrer Major 12", "+34 653151285", "e.pgimenezm@ies-sabadell.cat", "24/11/2006"))
        llistaContactes.add(Contacte("Izan", "Boyero", "Avinguda Roma 45", "+34 644305430", "izanbo@ies-sabadell.cat", "02/05/2005"))
        llistaContactes.add(Contacte("Adam", "Partida", "Carrer Menor 13", "+34 622133180", "apartidam@ies-sabadell.cat", "19/08/2000"))
        llistaContactes.add(Contacte("Victoria", "Alcázar", "Avinguda Paris 25", "+34 653124380", "e.valcazarf@ies-sabadell.cat", "09/07/2007"))
        llistaContactes.add(Contacte("Didac", "Avellán", "Carrer Mitja 14", "+34 611413670", "davellana@ies-sabadell.cat", "26/09/2007"))
        llistaContactes.add(Contacte("Adrian", "Herrera", "Avinguda Cadiz 15", "+34 647883871", "aherrerag@ies-sabadell.cat", "14/07/2001"))

        lvContactes = findViewById(R.id.lvContactes)
        val btnNouContacte = findViewById<Button>(R.id.btnNouContacte)

        adapter = ContacteAdapter(this, llistaContactes)
        lvContactes.adapter = adapter

        btnNouContacte.setOnClickListener {
            val intent = Intent(this, DetallActivity::class.java)
            detallLauncher.launch(intent)
        }

        lvContactes.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetallActivity::class.java)
            intent.putExtra("CONTACTE", llistaContactes[position])
            intent.putExtra("POSICIO", position)
            detallLauncher.launch(intent)
        }
    }
}