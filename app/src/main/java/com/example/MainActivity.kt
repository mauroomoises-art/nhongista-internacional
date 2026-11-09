package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.model.AppThemeType
import com.example.data.model.Product
import com.example.ui.components.AlibabaHeroSection
import com.example.ui.components.CartDialog
import com.example.ui.components.CheckoutDialog
import com.example.ui.components.FooterAlibaba
import com.example.ui.components.HeaderAlibaba
import com.example.ui.components.MessagesDialog
import com.example.ui.components.ProductCardAlibaba
import com.example.ui.components.ProductDetailDialog
import com.example.ui.components.PublishProductDialog
import com.example.ui.components.SettingsAboutDialog
import com.example.ui.theme.NhonguistaTheme
import com.example.ui.theme.ThemeConfig
import com.example.viewmodel.NhonguistaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NhonguistaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeType by viewModel.themeType.collectAsStateWithLifecycle()
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            NhonguistaTheme(
                themeType = themeType,
                darkTheme = isDarkMode
            ) {
                MainMarketplaceScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainMarketplaceScreen(viewModel: NhonguistaViewModel) {
    val context = LocalContext.current

    // Observe StateFlows
    val products by viewModel.products.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchTab by viewModel.searchTab.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()
    val minPrice by viewModel.minPrice.collectAsStateWithLifecycle()
    val maxPrice by viewModel.maxPrice.collectAsStateWithLifecycle()

    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val supplierChats by viewModel.supplierChats.collectAsStateWithLifecycle()
    val messagesList by viewModel.messagesList.collectAsStateWithLifecycle()

    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val isPublishOpen by viewModel.isPublishOpen.collectAsStateWithLifecycle()
    val isCartOpen by viewModel.isCartOpen.collectAsStateWithLifecycle()
    val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsStateWithLifecycle()
    val isSettingsOpen by viewModel.isSettingsOpen.collectAsStateWithLifecycle()
    val isMessagesOpen by viewModel.isMessagesOpen.collectAsStateWithLifecycle()
    val isFavoritesOpen by viewModel.isFavoritesOpen.collectAsStateWithLifecycle()
    val isLoginOpen by viewModel.isLoginOpen.collectAsStateWithLifecycle()

    val themeType by viewModel.themeType.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val customPhotoUri by viewModel.customPhotoUri.collectAsStateWithLifecycle()

    val toastMsg by viewModel.toastMessage.collectAsStateWithLifecycle()
    val orderSummary by viewModel.orderSuccessSummary.collectAsStateWithLifecycle()

    LaunchedEffect(toastMsg) {
        toastMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    val gridState = rememberLazyGridState()

    // Root background with optional image wallpaper for "Oceano com homem centado na cadeira" or custom photo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Theme Photo Wallpapers
        if (themeType == AppThemeType.OCEAN_MAN_CHAIR) {
            Image(
                painter = painterResource(id = R.drawable.seated_ocean_theme_1794260159588),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Translucent scrim for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = if (isDarkMode) 0.15f else 0.85f))
            )
        } else if (themeType == AppThemeType.CUSTOM_PHOTO && customPhotoUri != null) {
            AsyncImage(
                model = customPhotoUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = if (isDarkMode) 0.15f else 0.85f))
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.safeDrawing,
            // Central floating "+" button as requested:
            // "Botão '+' flutuante grande no centro na parte de baixo. Ao clicar abre formulário 'Publicar o que vendes'."
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.setPublishOpen(true) },
                    containerColor = ThemeConfig.AlibabaOrange,
                    contentColor = Color.White,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .size(60.dp)
                        .testTag("fab_publish_center")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Publicar o que vendes",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val screenWidth = maxWidth
                val columns = if (screenWidth >= 900.dp) 4 else if (screenWidth >= 600.dp) 3 else 2

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    state = gridState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("products_grid"),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Item 1: HEADER COMPONENT
                    item(span = { GridItemSpan(columns) }) {
                        HeaderAlibaba(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) },
                            currentSearchTab = searchTab,
                            onSearchTabChange = { viewModel.setSearchTab(it) },
                            selectedCategory = selectedCategory,
                            onCategorySelect = { viewModel.setSelectedCategory(it) },
                            selectedCity = selectedCity,
                            onCitySelect = { viewModel.setSelectedCity(it) },
                            minPrice = minPrice,
                            maxPrice = maxPrice,
                            onPriceFilterChange = { min, max -> viewModel.setPriceRange(min, max) },
                            cartCount = cartItems.sumOf { it.quantity },
                            favoritesCount = favoriteIds.size,
                            unreadMessagesCount = supplierChats.sumOf { it.unreadCount },
                            onOpenCart = { viewModel.setCartOpen(true) },
                            onOpenMessages = { viewModel.setMessagesOpen(true) },
                            onOpenFavorites = { viewModel.setFavoritesOpen(true) },
                            onOpenPublish = { viewModel.setPublishOpen(true) },
                            onOpenSettings = { viewModel.setSettingsOpen(true) },
                            onOpenLogin = { viewModel.setLoginOpen(true) }
                        )
                    }

                    // Item 2: HERO BANNER & 3-COLUMN SECTION (only if no active query)
                    if (searchQuery.isBlank() && selectedCategory == "Todas as Categorias" && selectedCity == "Todas as Cidades" && minPrice == null && maxPrice == null) {
                        item(span = { GridItemSpan(columns) }) {
                            AlibabaHeroSection(
                                onCategoryClick = { viewModel.setSelectedCategory(it) },
                                onOpenPublish = { viewModel.setPublishOpen(true) },
                                onOpenLogin = { viewModel.setLoginOpen(true) }
                            )
                        }

                        // Item 3: OFERTAS DO DIA (FLASH DEALS HORIZONTAL BAR)
                        item(span = { GridItemSpan(columns) }) {
                            FlashDealsSection(
                                dealProducts = products.filter { it.isDealOfTheDay },
                                onProductClick = { viewModel.selectProduct(it) }
                            )
                        }

                        // Item 4: PRODUTOS EM DESTAQUE / VERIFICADOS BAR
                        item(span = { GridItemSpan(columns) }) {
                            FeaturedSuppliersBanner(
                                onCityClick = { viewModel.setSelectedCity(it) }
                            )
                        }
                    }

                    // Item 5: SECTION TITLE (Feed Header)
                    item(span = { GridItemSpan(columns) }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Whatshot,
                                    contentDescription = null,
                                    tint = ThemeConfig.AlibabaOrange,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (searchQuery.isNotBlank()) "Resultados da Pesquisa (${filteredProducts.size})"
                                    else if (selectedCategory != "Todas as Categorias") "$selectedCategory (${filteredProducts.size})"
                                    else "Feed de Produtos em Moçambique",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isDarkMode) Color.White else Color(0xFF1E1E1E)
                                )
                            }

                            if (searchQuery.isNotBlank() || selectedCategory != "Todas as Categorias" || selectedCity != "Todas as Cidades" || minPrice != null) {
                                TextButton(
                                    onClick = {
                                        viewModel.setSearchQuery("")
                                        viewModel.setSelectedCategory("Todas as Categorias")
                                        viewModel.setSelectedCity("Todas as Cidades")
                                        viewModel.clearPriceRange()
                                    }
                                ) {
                                    Text("Limpar Filtros", color = ThemeConfig.AlibabaOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Item 6: EMPTY STATE
                    if (filteredProducts.isEmpty()) {
                        item(span = { GridItemSpan(columns) }) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = ThemeConfig.AlibabaOrange,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Nenhum produto encontrado nesta busca",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E1E1E)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Tenta pesquisar por 'capulana', 'telemóvel' ou altera a cidade.",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = {
                                            viewModel.setSearchQuery("")
                                            viewModel.setSelectedCategory("Todas as Categorias")
                                            viewModel.setSelectedCity("Todas as Cidades")
                                            viewModel.clearPriceRange()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange)
                                    ) {
                                        Text("Mostrar Todos os Produtos", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    } else {
                        // PRODUCT GRID ITEMS (Grelha infinita de produtos)
                        items(filteredProducts, key = { it.id }) { product ->
                            Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                                ProductCardAlibaba(
                                    product = product,
                                    isFavorite = favoriteIds.contains(product.id),
                                    onToggleFavorite = { viewModel.toggleFavorite(product.id) },
                                    onClick = { viewModel.selectProduct(product) },
                                    onAddToCart = { viewModel.addToCart(product) },
                                    onContactSupplier = {
                                        // open detail or supplier chat
                                        viewModel.selectProduct(product)
                                    }
                                )
                            }
                        }
                    }

                    // Item 7: FOOTER
                    item(span = { GridItemSpan(columns) }) {
                        Spacer(modifier = Modifier.height(16.dp))
                        FooterAlibaba(
                            onCityClick = { viewModel.setSelectedCity(it) },
                            onCategoryClick = { viewModel.setSelectedCategory(it) },
                            onOpenSettings = { viewModel.setSettingsOpen(true) }
                        )
                    }
                }
            }
        }
    }

    // MODALS & DIALOGS

    // 1. Product Detail Modal
    selectedProduct?.let { product ->
        ProductDetailDialog(
            product = product,
            isFavorite = favoriteIds.contains(product.id),
            onToggleFavorite = { viewModel.toggleFavorite(product.id) },
            onDismiss = { viewModel.selectProduct(null) },
            onAddToCart = {
                viewModel.addToCart(it)
                viewModel.selectProduct(null)
            },
            onBuyNow = {
                viewModel.addToCart(it)
                viewModel.selectProduct(null)
                viewModel.setCheckoutOpen(true)
            },
            onOpenSupplierChat = { phone ->
                viewModel.selectProduct(null)
                viewModel.setMessagesOpen(true)
            }
        )
    }

    // 2. Publish Product Dialog (Formulário "Publicar o que vendes")
    if (isPublishOpen) {
        PublishProductDialog(
            onDismiss = { viewModel.setPublishOpen(false) },
            onPublish = { phone, city, specific, price, title, desc, cat, seller, wa, preset, customUri ->
                viewModel.publishProduct(
                    phone = phone,
                    locationCity = city,
                    locationSpecific = specific,
                    price = price,
                    title = title,
                    description = desc,
                    category = cat,
                    sellerName = seller,
                    whatsApp = wa,
                    selectedPresetImage = preset,
                    customImageUri = customUri
                )
            }
        )
    }

    // 3. Cart Dialog
    if (isCartOpen) {
        CartDialog(
            cartItems = cartItems,
            totalMt = viewModel.getCartTotalMt(),
            onQuantityChange = { id, delta -> viewModel.updateCartQuantity(id, delta) },
            onRemoveItem = { id -> viewModel.removeFromCart(id) },
            onClearCart = { viewModel.clearCart() },
            onProceedToCheckout = {
                viewModel.setCartOpen(false)
                viewModel.setCheckoutOpen(true)
            },
            onDismiss = { viewModel.setCartOpen(false) }
        )
    }

    // 4. Checkout Dialog (With Pagamento na Entrega & M-Pesa)
    if (isCheckoutOpen) {
        CheckoutDialog(
            cartItems = cartItems,
            totalMt = viewModel.getCartTotalMt(),
            onDismiss = { viewModel.setCheckoutOpen(false) },
            onConfirmOrder = { method, name, phone, city, address ->
                viewModel.completeOrder(
                    paymentMethod = method,
                    customerName = name,
                    customerPhone = phone,
                    deliveryCity = city,
                    deliveryAddress = address
                )
            }
        )
    }

    // 5. Settings & About Dialog (Criador Lúcifer O'black, Temas, Pagamento na Entrega)
    if (isSettingsOpen) {
        SettingsAboutDialog(
            currentTheme = themeType,
            isDarkMode = isDarkMode,
            onSelectTheme = { viewModel.setThemeType(it) },
            onToggleDarkMode = { viewModel.toggleDarkMode() },
            onPickCustomBackground = { viewModel.setCustomPhotoUri(it) },
            onDismiss = { viewModel.setSettingsOpen(false) }
        )
    }

    // 6. Messages Dialog
    if (isMessagesOpen) {
        MessagesDialog(
            supplierChats = supplierChats,
            messagesList = messagesList,
            onSendMessage = { phone, msg -> viewModel.sendMessageToSupplier(phone, msg) },
            onDismiss = { viewModel.setMessagesOpen(false) }
        )
    }

    // 7. Favorites Dialog
    if (isFavoritesOpen) {
        val favoriteProducts = products.filter { favoriteIds.contains(it.id) }
        AlertDialog(
            onDismissRequest = { viewModel.setFavoritesOpen(false) },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFEF4444))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Meus Favoritos (${favoriteProducts.size})", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                if (favoriteProducts.isEmpty()) {
                    Text("Ainda não tens produtos favoritados. Clica no coração dos produtos para guardar.")
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        favoriteProducts.take(5).forEach { prod ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        viewModel.setFavoritesOpen(false)
                                        viewModel.selectProduct(prod)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = prod.title,
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = NhonguistaViewModel.formatMt(prod.priceMt),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ThemeConfig.AlibabaOrange
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.setFavoritesOpen(false) }) {
                    Text("Fechar", color = ThemeConfig.AlibabaOrange, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // 8. Login / Profile Simulation Dialog
    if (isLoginOpen) {
        var userNameInput by remember { mutableStateOf("") }
        var userPhoneInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { viewModel.setLoginOpen(false) },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Entrar / Minha Conta", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "No Nhonguista Internacional a navegação é 100% LIVRE e sem taxas! Não pagas nada para entrar ou publicar.",
                        fontSize = 12.sp,
                        color = Color(0xFF4B5563)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = userNameInput,
                        onValueChange = { userNameInput = it },
                        label = { Text("Teu Nome") },
                        placeholder = { Text("Ex: Carlos Sitoe") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = userPhoneInput,
                        onValueChange = { userPhoneInput = it },
                        label = { Text("Número de Celular") },
                        placeholder = { Text("Ex: 844935865") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.showToast("Sessão iniciada como ${if (userNameInput.isNotBlank()) userNameInput else "Nhonguista"}")
                        viewModel.setLoginOpen(false)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange)
                ) {
                    Text("Entrar Agora", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setLoginOpen(false) }) {
                    Text("Continuar como Visitante", color = Color.Gray)
                }
            }
        )
    }

    // 9. Order Confirmation Notification
    orderSummary?.let { summary ->
        AlertDialog(
            onDismissRequest = { viewModel.dismissOrderSummary() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color(0xFF16A34A))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Encomenda Realizada com Sucesso!", fontWeight = FontWeight.Black, fontSize = 16.sp)
                }
            },
            text = {
                Column {
                    Text(
                        text = summary,
                        fontSize = 13.sp,
                        color = Color(0xFF1E1E1E),
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "🛡️ Lembra-te: O pagamento só é efetuado quando o motorizado te entregar a mercadoria em mãos!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissOrderSummary() },
                    colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange)
                ) {
                    Text("Concluir", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun FlashDealsSection(
    dealProducts: List<Product>,
    onProductClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header with Fire icon and countdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Ofertas do Dia • Boladas Relâmpago",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E1E1E)
                    )
                }

                // Countdown timer badge
                Surface(
                    color = Color(0xFF1E1E1E),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Termina em 05:42:19",
                        color = Color(0xFFFFD700),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Scroll of Flash Deals
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                dealProducts.take(6).forEach { product ->
                    Surface(
                        modifier = Modifier
                            .width(130.dp)
                            .clickable { onProductClick(product) }
                            .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFAFAFA)
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(6.dp))
                            ) {
                                if (product.imageRes != null) {
                                    Image(
                                        painter = painterResource(id = product.imageRes),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Surface(
                                    color = Color(0xFFEF4444),
                                    shape = RoundedCornerShape(bottomEnd = 4.dp),
                                    modifier = Modifier.align(Alignment.TopStart)
                                ) {
                                    Text(
                                        text = "-35%",
                                        color = Color.White,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = product.title,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = NhonguistaViewModel.formatMt(product.priceMt),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = ThemeConfig.AlibabaOrange
                            )
                            Text(
                                text = "📍 ${product.city}",
                                fontSize = 9.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedSuppliersBanner(onCityClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = ThemeConfig.AlibabaOrange,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Fornecedores Verificados de Moçambique",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Text(
                        text = "Vendedores de Maputo, Matola, Beira, Nampula e Tete",
                        fontSize = 10.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ThemeConfig.AlibabaOrange
            ) {
                Text(
                    text = "Ver Todos",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onCityClick("Todas as Cidades") }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
