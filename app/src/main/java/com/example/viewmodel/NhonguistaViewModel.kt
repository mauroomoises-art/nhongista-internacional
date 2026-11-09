package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.SampleData
import com.example.data.model.AppThemeType
import com.example.data.model.CartItem
import com.example.data.model.ChatMessage
import com.example.data.model.Product
import com.example.data.model.SearchTab
import com.example.data.model.SupplierChat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

class NhonguistaViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(SampleData.INITIAL_PRODUCTS)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchTab = MutableStateFlow(SearchTab.PRODUTOS)
    val searchTab: StateFlow<SearchTab> = _searchTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todas as Categorias")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedCity = MutableStateFlow("Todas as Cidades")
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    private val _minPrice = MutableStateFlow<Double?>(null)
    val minPrice: StateFlow<Double?> = _minPrice.asStateFlow()

    private val _maxPrice = MutableStateFlow<Double?>(null)
    val maxPrice: StateFlow<Double?> = _maxPrice.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private val _supplierChats = MutableStateFlow(SampleData.INITIAL_SUPPLIER_CHATS)
    val supplierChats: StateFlow<List<SupplierChat>> = _supplierChats.asStateFlow()

    private val _messagesList = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage("m1", "Mama Zinha", "Olá mano, temos capulanas novinhas em folha!", "10:30", false, "844935865"),
            ChatMessage("m2", "Eu", "Fazem entrega na Matola Rio?", "10:32", true, "844935865"),
            ChatMessage("m3", "Mama Zinha", "Sim! Entrega feita com Pagamento na Entrega via motorizada.", "10:35", false, "844935865")
        )
    )
    val messagesList: StateFlow<List<ChatMessage>> = _messagesList.asStateFlow()

    // Dialog & UI Navigation States
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _isPublishOpen = MutableStateFlow(false)
    val isPublishOpen: StateFlow<Boolean> = _isPublishOpen.asStateFlow()

    private val _isCartOpen = MutableStateFlow(false)
    val isCartOpen: StateFlow<Boolean> = _isCartOpen.asStateFlow()

    private val _isCheckoutOpen = MutableStateFlow(false)
    val isCheckoutOpen: StateFlow<Boolean> = _isCheckoutOpen.asStateFlow()

    private val _isSettingsOpen = MutableStateFlow(false)
    val isSettingsOpen: StateFlow<Boolean> = _isSettingsOpen.asStateFlow()

    private val _isMessagesOpen = MutableStateFlow(false)
    val isMessagesOpen: StateFlow<Boolean> = _isMessagesOpen.asStateFlow()

    private val _isFavoritesOpen = MutableStateFlow(false)
    val isFavoritesOpen: StateFlow<Boolean> = _isFavoritesOpen.asStateFlow()

    private val _isLoginOpen = MutableStateFlow(false)
    val isLoginOpen: StateFlow<Boolean> = _isLoginOpen.asStateFlow()

    // Theme & Appearance
    private val _themeType = MutableStateFlow(AppThemeType.FIRE_ORANGE)
    val themeType: StateFlow<AppThemeType> = _themeType.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _customPhotoUri = MutableStateFlow<String?>(null)
    val customPhotoUri: StateFlow<String?> = _customPhotoUri.asStateFlow()

    // Feedback
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _orderSuccessSummary = MutableStateFlow<String?>(null)
    val orderSuccessSummary: StateFlow<String?> = _orderSuccessSummary.asStateFlow()

    // Filter criteria intermediate flows
    private val _textAndCategoryFlow = combine(
        _searchQuery,
        _searchTab,
        _selectedCategory
    ) { query, tab, category -> Triple(query, tab, category) }

    private val _locationAndPriceFlow = combine(
        _selectedCity,
        _minPrice,
        _maxPrice
    ) { city, minP, maxP -> Triple(city, minP, maxP) }

    // Filtered products flow
    val filteredProducts: StateFlow<List<Product>> = combine(
        _products,
        _textAndCategoryFlow,
        _locationAndPriceFlow
    ) { productsList, textCat, locPrice ->
        val (query, tab, category) = textCat
        val (city, minP, maxP) = locPrice

        productsList.filter { prod ->
            val matchesQuery = if (query.isBlank()) {
                true
            } else {
                when (tab) {
                    SearchTab.PRODUTOS -> {
                        prod.title.contains(query, ignoreCase = true) ||
                                prod.description.contains(query, ignoreCase = true) ||
                                prod.category.contains(query, ignoreCase = true)
                    }
                    SearchTab.FORNECEDORES -> {
                        prod.supplierName.contains(query, ignoreCase = true) ||
                                prod.location.contains(query, ignoreCase = true)
                    }
                    SearchTab.FABRICANTES -> {
                        prod.supplierName.contains(query, ignoreCase = true) ||
                                prod.title.contains(query, ignoreCase = true)
                    }
                }
            }

            val matchesCategory = (category == "Todas as Categorias") || (prod.category.equals(category, ignoreCase = true))
            val matchesCity = (city == "Todas as Cidades") || (prod.city.equals(city, ignoreCase = true))

            val matchesMinPrice = minP == null || prod.priceMt >= minP
            val matchesMaxPrice = maxP == null || prod.priceMt <= maxP

            matchesQuery && matchesCategory && matchesCity && matchesMinPrice && matchesMaxPrice
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _products.value)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchTab(tab: SearchTab) {
        _searchTab.value = tab
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun setPriceRange(min: Double?, max: Double?) {
        _minPrice.value = min
        _maxPrice.value = max
    }

    fun clearPriceRange() {
        _minPrice.value = null
        _maxPrice.value = null
    }

    fun selectProduct(product: Product?) {
        _selectedProduct.value = product
    }

    fun setPublishOpen(open: Boolean) {
        _isPublishOpen.value = open
    }

    fun setCartOpen(open: Boolean) {
        _isCartOpen.value = open
    }

    fun setCheckoutOpen(open: Boolean) {
        _isCheckoutOpen.value = open
    }

    fun setSettingsOpen(open: Boolean) {
        _isSettingsOpen.value = open
    }

    fun setMessagesOpen(open: Boolean) {
        _isMessagesOpen.value = open
    }

    fun setFavoritesOpen(open: Boolean) {
        _isFavoritesOpen.value = open
    }

    fun setLoginOpen(open: Boolean) {
        _isLoginOpen.value = open
    }

    fun setThemeType(type: AppThemeType) {
        _themeType.value = type
        showToast("Tema alterado para: ${type.displayName}")
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setCustomPhotoUri(uri: String?) {
        _customPhotoUri.value = uri
        if (uri != null) {
            _themeType.value = AppThemeType.CUSTOM_PHOTO
            showToast("Foto de fundo personalizada aplicada!")
        }
    }

    fun toggleFavorite(productId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.contains(productId)) {
            current.remove(productId)
            showToast("Removido dos Favoritos")
        } else {
            current.add(productId)
            showToast("Adicionado aos Favoritos!")
        }
        _favoriteIds.value = current
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            current.add(CartItem(product = product, quantity = quantity))
        }
        _cartItems.value = current
        showToast("Adicionado ao carrinho: ${product.title}")
    }

    fun updateCartQuantity(productId: String, delta: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            val item = current[index]
            val newQty = item.quantity + delta
            if (newQty <= 0) {
                current.removeAt(index)
            } else {
                current[index] = item.copy(quantity = newQty)
            }
            _cartItems.value = current
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getCartTotalMt(): Double {
        return _cartItems.value.sumOf { it.product.priceMt * it.quantity }
    }

    fun publishProduct(
        phone: String,
        locationCity: String,
        locationSpecific: String,
        price: Double,
        title: String,
        description: String,
        category: String,
        sellerName: String,
        whatsApp: String?,
        selectedPresetImage: Int?,
        customImageUri: String?
    ): Boolean {
        if (phone.isBlank() || locationCity.isBlank() || price <= 0.0) {
            return false
        }

        val fullLocation = if (locationSpecific.isNotBlank()) {
            "$locationCity - $locationSpecific"
        } else {
            locationCity
        }

        val newProduct = Product(
            id = "user-prod-${UUID.randomUUID().toString().take(6)}",
            title = if (title.isNotBlank()) title else "Artigo em Venda ($category)",
            priceMt = price,
            originalPriceMt = price * 1.25,
            category = if (category.isNotBlank()) category else "Outros",
            supplierName = if (sellerName.isNotBlank()) sellerName else "Vendedor Particular ($locationCity)",
            supplierPhone = phone,
            supplierWhatsApp = whatsApp ?: phone,
            location = fullLocation,
            city = locationCity,
            rating = 5.0f,
            reviewsCount = 1,
            minOrder = "1 Peça",
            soldCount = 0,
            imageRes = selectedPresetImage ?: R.drawable.app_icon_deal_1794260145369,
            customImageUri = customImageUri,
            isVerifiedSupplier = true,
            isFeatured = true,
            isDealOfTheDay = true,
            isBestSeller = false,
            isNew = true,
            description = if (description.isNotBlank()) description else "Produto anunciado no Nhonguista Internacional. Contacte diretamente o vendedor pelo celular ou WhatsApp. Aceita Pagamento na Entrega em $locationCity."
        )

        // Prepend immediately at the top of the feed as requested
        _products.value = listOf(newProduct) + _products.value
        _isPublishOpen.value = false
        showToast("Sucesso! O seu anúncio está 100% ONLINE no topo do feed.")
        return true
    }

    fun completeOrder(
        paymentMethod: String,
        customerName: String,
        customerPhone: String,
        deliveryCity: String,
        deliveryAddress: String
    ) {
        val total = getCartTotalMt()
        val orderCode = "NH-" + (100000..999999).random()

        _orderSuccessSummary.value = "Encomenda #$orderCode confirmada!\nMétodo: $paymentMethod\nTotal: ${formatMt(total)}\nDestino: $deliveryCity, $deliveryAddress\nContacto: $customerPhone\nO nosso motorizado entrará em contacto para entregar a sua mercadoria em segurança!"

        clearCart()
        _isCheckoutOpen.value = false
    }

    fun dismissOrderSummary() {
        _orderSuccessSummary.value = null
    }

    fun sendMessageToSupplier(supplierPhone: String, messageText: String) {
        if (messageText.isBlank()) return
        val newMsg = ChatMessage(
            id = "msg-${System.currentTimeMillis()}",
            senderName = "Eu",
            message = messageText,
            timestamp = "Agora",
            isFromMe = true,
            supplierPhone = supplierPhone
        )
        _messagesList.value = _messagesList.value + newMsg
        showToast("Mensagem enviada com sucesso!")
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    companion object {
        fun formatMt(amount: Double): String {
            val nf = NumberFormat.getNumberInstance(Locale("pt", "MZ"))
            nf.maximumFractionDigits = 0
            return "${nf.format(amount)} MT"
        }
    }
}
