package com.example.listview

import java.io.Serializable

data class Contacte(
    var nom: String,
    var cognom: String,
    var adreca: String,
    var telefon: String,
    var mail: String,
    var dataNaixement: String
) : Serializable