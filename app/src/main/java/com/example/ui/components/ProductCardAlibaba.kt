package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Product
import com.example.ui.theme.ThemeConfig
import com.example.viewmodel.NhonguistaViewModel

@Composable
fun ProductCardAlibaba(
    product: Product,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    onContactSupplier: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("product_card_${product.id}"),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Product Image Container with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color(0xFFF3F4F6))
            ) {
                if (product.customImageUri != null) {
                    AsyncImage(
                        model = product.customImageUri,
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (product.imageRes != null) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon_deal_1794260145369),
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Badges in top-left
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (product.isNew) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFEF4444)
                        ) {
                            Text(
                                text = "NOVO",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (product.originalPriceMt != null && product.originalPriceMt > product.priceMt) {
                        val discountPct = (((product.originalPriceMt - product.priceMt) / product.originalPriceMt) * 100).toInt()
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = ThemeConfig.AlibabaOrange
                        ) {
                            Text(
                                text = "-$discountPct%",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Favorite Heart in top-right
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.85f), CircleShape)
                        .testTag("fav_btn_${product.id}")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = if (isFavorite) Color(0xFFEF4444) else Color(0xFF6B7280),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Pagamento na Entrega badge at bottom of photo
                Surface(
                    color = Color.Black.copy(alpha = 0.75f),
                    shape = RoundedCornerShape(topEnd = 6.dp),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF00E676),
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Pague na Entrega",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Price & MOQ (Alibaba Style)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = NhonguistaViewModel.formatMt(product.priceMt),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "/ ${product.minOrder}",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Normal
                    )
                }

                // Original Price strikethrough if discounted
                if (product.originalPriceMt != null && product.originalPriceMt > product.priceMt) {
                    Text(
                        text = NhonguistaViewModel.formatMt(product.originalPriceMt),
                        fontSize = 11.sp,
                        color = Color(0xFF9CA3AF),
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Product Title (2 lines)
                Text(
                    text = product.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F2937),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Supplier & Verified Seal
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Fornecedor Verificado",
                        tint = ThemeConfig.AlibabaOrange,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = product.supplierName,
                        fontSize = 10.sp,
                        color = Color(0xFF4B5563),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Mozambican Location & Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = product.location,
                            fontSize = 10.sp,
                            color = Color(0xFF6B7280),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB800),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "${product.rating}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF374151)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons: "Contactar" and "Comprar"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Contact Supplier button (opens dialer or chat)
                    OutlinedButton(
                        onClick = onContactSupplier,
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = ButtonDefaults.ContentPadding,
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .testTag("btn_contact_${product.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color(0xFF374151),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Contactar",
                            fontSize = 10.sp,
                            color = Color(0xFF374151),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    // Comprar / Carrinho button (orange Alibaba style)
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = ButtonDefaults.ContentPadding,
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .testTag("btn_buy_${product.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Comprar",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
