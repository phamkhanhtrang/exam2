package com.example.admin.Data

import com.google.firebase.auth.FirebaseAuth

data class ProductData(
    var productId: String? ="",
    var name: String?="",
    var cost: String?="",
    var imageUrl: String?="",
)