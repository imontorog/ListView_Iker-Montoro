package com.example.listview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetallActivity : AppCompatActivity() {

    private var posicio = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detall)

        val tvTitol = findViewById<TextView>(R.id.tvTitolDetall)
        val etNom = findViewById<EditText>(R.id.etNom)
        val etCognom = findViewById<EditText>(R.id.etCognom)
        val etAdreca = findViewById<EditText>(R.id.etAdreca)
        val etTelefon = findViewById<EditText>(R.id.etTelefon)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etDataNaixement = findViewById<EditText>(R.id.etDataNaixement)

        val btnTrucar = findViewById<ImageButton>(R.id.btnDetallTrucar)
        val btnEmail = findViewById<ImageButton>(R.id.btnDetallEmail)
        val btnAcceptar = findViewById<Button>(R.id.btnAcceptar)
        val btnCancellar = findViewById<Button>(R.id.btnCancellar)
        val btnEsborrar = findViewById<Button>(R.id.btnEsborrar)

        posicio = intent.getIntExtra("POSICIO", -1)
        val contacteRebut = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("CONTACTE", Contacte::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("CONTACTE") as? Contacte
        }

        if (contacteRebut != null && posicio != -1) {
            tvTitol.text = getString(R.string.title_edit)
            etNom.setText(contacteRebut.nom)
            etCognom.setText(contacteRebut.cognom)
            etAdreca.setText(contacteRebut.adreca)
            etTelefon.setText(contacteRebut.telefon)
            etEmail.setText(contacteRebut.mail)
            etDataNaixement.setText(contacteRebut.dataNaixement)
            btnEsborrar.visibility = View.VISIBLE
        } else {
            tvTitol.text = getString(R.string.title_new)
            btnEsborrar.visibility = View.GONE
        }

        btnTrucar.setOnClickListener {
            val telefon = etTelefon.text.toString().trim()
            if (telefon.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telefon")))
            }
        }

        btnEmail.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")))
            }
        }

        btnAcceptar.setOnClickListener {
            val nom = etNom.text.toString().trim()
            val cognom = etCognom.text.toString().trim()
            val adreca = etAdreca.text.toString().trim()
            val telefon = etTelefon.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val dataNaixement = etDataNaixement.text.toString().trim()

            if (nom.isEmpty() || telefon.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_required), Toast.LENGTH_SHORT).show()
            } else {
                val nouContacte = Contacte(nom, cognom, adreca, telefon, email, dataNaixement)
                val resultIntent = Intent()
                resultIntent.putExtra("CONTACTE", nouContacte)
                resultIntent.putExtra("POSICIO", posicio)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        btnEsborrar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("POSICIO", posicio)
            resultIntent.putExtra("ESBORRAR", true)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancellar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}