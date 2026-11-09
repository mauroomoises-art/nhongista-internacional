package com.example.data.model

import androidx.annotation.DrawableRes

enum class SearchTab(val label: String) {
    PRODUTOS("Produtos"),
    FORNECEDORES("Fornecedores"),
    FABRICANTES("Fabricantes")
}

enum class AppThemeType(val displayName: String, val description: String) {
    FIRE_ORANGE("Laranja Fogo", "Estilo clássico Alibaba com laranja vibrante e preto"),
    LIGHT_GREEN("Verde Claro", "Tom esmeralda fresco e dinâmico"),
    OCEAN_MAN_CHAIR("Oceano com Homem na Cadeira", "Fundo estético com homem contemplando o mar"),
    NEON_PURPLE("Roxo Neon", "Visual moderno e tecnológico em roxo e violeta"),
    OCEAN_BLUE("Azul Oceano", "Marítimo e limpo com tons azuis profundos"),
    CUSTOM_PHOTO("Foto Personalizada", "Usar imagem personalizada como fundo do aplicativo")
}

data class Product(
    val id: String,
    val title: String,
    val priceMt: Double,
    val originalPriceMt: Double? = null,
    val category: String,
    val supplierName: String,
    val supplierPhone: String,
    val supplierWhatsApp: String? = null,
    val location: String, // e.g. "Maputo - Polana Caniço"
    val city: String, // e.g. "Maputo", "Matola", "Beira", "Nampula", "Tete"
    val rating: Float = 4.9f,
    val reviewsCount: Int = 38,
    val minOrder: String = "1 Peça",
    val soldCount: Int = 89,
    @DrawableRes val imageRes: Int? = null,
    val customImageUri: String? = null,
    val isVerifiedSupplier: Boolean = true,
    val isFeatured: Boolean = false,
    val isDealOfTheDay: Boolean = false,
    val isBestSeller: Boolean = false,
    val isNew: Boolean = false,
    val description: String,
    val acceptsCashOnDelivery: Boolean = true,
    val acceptsMPesa: Boolean = true
)

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val supplierPhone: String
)

data class SupplierChat(
    val supplierName: String,
    val supplierPhone: String,
    val location: String,
    val lastMessage: String,
    val lastTime: String,
    val unreadCount: Int = 0
)
