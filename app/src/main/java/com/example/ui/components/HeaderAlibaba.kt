package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.data.model.SearchTab
import com.example.ui.theme.ThemeConfig

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeaderAlibaba(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    currentSearchTab: SearchTab,
    onSearchTabChange: (SearchTab) -> Unit,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    selectedCity: String,
    onCitySelect: (String) -> Unit,
    minPrice: Double?,
    maxPrice: Double?,
    onPriceFilterChange: (Double?, Double?) -> Unit,
    cartCount: Int,
    favoritesCount: Int,
    unreadMessagesCount: Int,
    onOpenCart: () -> Unit,
    onOpenMessages: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenPublish: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPriceFilterModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // 1. TOP BAR (Barra superior fina estilo Alibaba)
        Surface(
            color = Color(0xFF1E1E1E),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Top text & live status
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00E676))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "bolada ONLINE - 100% Online / Servidor Ativo",
                        color = Color(0xFF00E676),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Quick links on right
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Vender no Nhonguista",
                        color = ThemeConfig.AlibabaOrange,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onOpenPublish() }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .testTag("top_bar_vender")
                    )
                    Text(
                        text = "Moçambique",
                        color = Color(0xFFD1D5DB),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "Definições",
                        color = Color(0xFFD1D5DB),
                        fontSize = 11.sp,
                        modifier = Modifier
                            .clickable { onOpenSettings() }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .testTag("top_bar_definicoes")
                    )
                }
            }
        }

        // 2. MAIN HEADER (Branco estilo Alibaba)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Row with Brand Logo & Quick Action Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // BRAND: Logo + Giant Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onCategorySelect("Todas as Categorias") }
                        .testTag("brand_logo_title")
                ) {
                    // Logo Box: Two Hands deal icon in orange and black with orange border
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .border(2.dp, ThemeConfig.AlibabaOrange, RoundedCornerShape(8.dp))
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.app_icon_deal_1794260145369),
                            contentDescription = "Logotipo Nhonguista - Duas mãos fechando negócio",
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // GIGANTIC Capital Name: NHONGUISTA (orange) + INTERNACIONAL (black)
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "NHONGUISTA ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = ThemeConfig.AlibabaOrange,
                                letterSpacing = (-0.5).sp
                            )
                            Text(
                                text = "INTERNACIONAL",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E1E1E),
                                letterSpacing = (-0.5).sp
                            )
                        }
                        Text(
                            text = "Compra e Vende em Moçambique",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF6B7280)
                        )
                    }
                }

                // Action Buttons at Right
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Messages
                    IconButton(
                        onClick = onOpenMessages,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("btn_messages")
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadMessagesCount > 0) {
                                    Badge(containerColor = ThemeConfig.AlibabaOrange) {
                                        Text("$unreadMessagesCount", color = Color.White, fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = "Mensagens",
                                tint = Color(0xFF374151),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Favorites
                    IconButton(
                        onClick = onOpenFavorites,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("btn_favorites")
                    ) {
                        BadgedBox(
                            badge = {
                                if (favoritesCount > 0) {
                                    Badge(containerColor = Color(0xFFEF4444)) {
                                        Text("$favoritesCount", color = Color.White, fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favoritos",
                                tint = if (favoritesCount > 0) Color(0xFFEF4444) else Color(0xFF374151),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Cart
                    IconButton(
                        onClick = onOpenCart,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("btn_cart")
                    ) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge(containerColor = ThemeConfig.AlibabaOrange) {
                                        Text("$cartCount", color = Color.White, fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrinho",
                                tint = Color(0xFF374151),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Orange button "+ Publicar / Vender"
                    Button(
                        onClick = onOpenPublish,
                        colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        modifier = Modifier
                            .height(36.dp)
                            .testTag("btn_top_publicar")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Publicar",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Entrar / Conta
                    IconButton(
                        onClick = onOpenLogin,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("btn_login")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Entrar / Perfil",
                            tint = Color(0xFF374151),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. GIANT ALIBABA SEARCH BAR IN CENTER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, ThemeConfig.AlibabaOrange, RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                // Search Tabs: [Produtos | Fornecedores | Fabricantes]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchTab.entries.forEach { tab ->
                        val isSelected = currentSearchTab == tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) ThemeConfig.AlibabaOrange else Color.Transparent)
                                .clickable { onSearchTabChange(tab) }
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tab.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF4B5563)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Search input + Orange "Pesquisar" Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = when (currentSearchTab) {
                                    SearchTab.PRODUTOS -> "Pesquisa produtos, capulana, telemóvel, mota..."
                                    SearchTab.FORNECEDORES -> "Pesquisa fornecedores em Maputo, Beira, Matola..."
                                    SearchTab.FABRICANTES -> "Pesquisa fabricantes e alfaiates em Moçambique..."
                                },
                                fontSize = 13.sp,
                                color = Color(0xFF9CA3AF),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = ThemeConfig.AlibabaOrange,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(
                                    onClick = { onSearchQueryChange("") },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Limpar",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFF9FAFB),
                            unfocusedContainerColor = Color(0xFFF9FAFB)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("search_text_input")
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(
                        onClick = { /* trigger search action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(44.dp)
                            .testTag("btn_pesquisar")
                    ) {
                        Text(
                            text = "Pesquisar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Hot Keywords Underneath
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mais pesquisados: ",
                    fontSize = 11.sp,
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.Bold
                )
                SampleData.HOT_KEYWORDS.forEach { keyword ->
                    Text(
                        text = keyword,
                        fontSize = 11.sp,
                        color = Color(0xFF4B5563),
                        modifier = Modifier
                            .clickable { onSearchQueryChange(keyword) }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Text(text = "•", fontSize = 9.sp, color = Color(0xFFD1D5DB))
                }
            }
        }

        // 4. CATEGORIES & PRICE FILTER BAR
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F9FA))
                .border(width = 0.5.dp, color = Color(0xFFE5E7EB))
        ) {
            // Horizontal Categories Scroll
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // "Todas as Categorias" Button
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (selectedCategory == "Todas as Categorias") ThemeConfig.AlibabaOrange else Color.White,
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier
                        .clickable { onCategorySelect("Todas as Categorias") }
                        .testTag("cat_todas")
                ) {
                    Text(
                        text = "Todas as Categorias",
                        color = if (selectedCategory == "Todas as Categorias") Color.White else Color(0xFF1E1E1E),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                // Individual Categories
                SampleData.CATEGORIES.filter { it != "Todas as Categorias" }.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) ThemeConfig.AlibabaOrange else Color.White,
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .clickable { onCategorySelect(cat) }
                            .testTag("cat_$cat")
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else Color(0xFF374151),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Location & Price Filter Row (Pesquisar por Preço)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // City / Província Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ThemeConfig.AlibabaOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    SampleData.MOZAMBIQUE_CITIES.forEach { city ->
                        val isSelected = selectedCity == city
                        Text(
                            text = city,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ThemeConfig.AlibabaOrange else Color(0xFF4B5563),
                            modifier = Modifier
                                .clickable { onCitySelect(city) }
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }

                // Price Filter Trigger Button
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (minPrice != null || maxPrice != null) ThemeConfig.AlibabaOrange.copy(alpha = 0.15f) else Color.White,
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier
                        .clickable { showPriceFilterModal = !showPriceFilterModal }
                        .testTag("btn_filter_price")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = ThemeConfig.AlibabaOrange,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (minPrice != null || maxPrice != null) {
                                "${minPrice?.toInt() ?: 0} - ${maxPrice?.toInt() ?: "..."} MT"
                            } else {
                                "Filtrar Preço"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ThemeConfig.AlibabaOrange
                        )
                    }
                }
            }

            // Quick Price Chips if toggled
            if (showPriceFilterModal) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Faixa de Preço em Meticais (MT):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val ranges = listOf(
                            Pair("< 2.000 MT", Pair(null, 2000.0)),
                            Pair("2k - 10k MT", Pair(2000.0, 10000.0)),
                            Pair("10k - 30k MT", Pair(10000.0, 30000.0)),
                            Pair("> 30.000 MT", Pair(30000.0, null))
                        )

                        ranges.forEach { (label, range) ->
                            val isSelected = minPrice == range.first && maxPrice == range.second
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (isSelected) ThemeConfig.AlibabaOrange else Color(0xFFF3F4F6),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (isSelected) {
                                            onPriceFilterChange(null, null)
                                        } else {
                                            onPriceFilterChange(range.first, range.second)
                                        }
                                    }
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else Color(0xFF374151),
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    if (minPrice != null || maxPrice != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Limpar filtro de preço",
                            fontSize = 11.sp,
                            color = Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    onPriceFilterChange(null, null)
                                    showPriceFilterModal = false
                                }
                                .padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
