package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.ui.theme.ThemeConfig

@Composable
fun AlibabaHeroSection(
    onCategoryClick: (String) -> Unit,
    onOpenPublish: () -> Unit,
    onOpenLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isWide = maxWidth >= 700.dp

        if (isWide) {
            // 3-Column Alibaba Desktop / Tablet Layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Column 1: Left Categories Menu
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Categorias Principais",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ThemeConfig.AlibabaOrange
                        )
                        val quickList = listOf(
                            "Telemóveis",
                            "Capulanas & Beleza",
                            "Motas & Carros",
                            "Eletrónica",
                            "Casa & Móveis",
                            "Comida & Bebida",
                            "Agricultura"
                        )
                        quickList.forEach { cat ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCategoryClick(cat) }
                                    .padding(vertical = 4.dp, horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = cat,
                                    fontSize = 11.sp,
                                    color = Color(0xFF374151),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                // Column 2: Center Main Banner
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    HeroBannerContent(onOpenPublish)
                }

                // Column 3: Right Welcome & Quick Action Card
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    WelcomeCardContent(onOpenPublish, onOpenLogin)
                }
            }
        } else {
            // Mobile Adaptive Layout: Big Banner + Welcome Strip
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                // Main Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    HeroBannerContent(onOpenPublish)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mobile Welcome & Trust Row
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    WelcomeCardContent(onOpenPublish, onOpenLogin)
                }
            }
        }
    }
}

@Composable
private fun HeroBannerContent(onOpenPublish: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.nhonguista_banner_1794260160007),
            contentDescription = "Mercado Nhonguista Internacional Moçambique",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay for high text legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.85f),
                            Color.Black.copy(alpha = 0.45f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = ThemeConfig.AlibabaOrange,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "O MAIOR MARKETPLACE DE MOÇAMBIQUE",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Compra e Vende\nSem Sair de Casa",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "100% Pagamento na Entrega • Seguro & Direto",
                    color = Color(0xFFD1FAE5),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onOpenPublish,
                colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(34.dp)
                    .testTag("hero_banner_cta")
            ) {
                Text(
                    text = "Começar a Vender Grátis",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun WelcomeCardContent(
    onOpenPublish: () -> Unit,
    onOpenLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // User welcome row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ThemeConfig.AlibabaOrange.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = ThemeConfig.AlibabaOrange,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Bem-vindo ao Nhonguista!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E1E)
                )
                Text(
                    text = "A tua bolada garantida em MT",
                    fontSize = 10.sp,
                    color = Color(0xFF6B7280)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Security badges
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9FAFB), RoundedCornerShape(6.dp))
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = null,
                    tint = ThemeConfig.AlibabaOrange,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Pague na Entrega",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF374151)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Fornecedor Verificado",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF374151)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "M-Pesa Prático",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF374151)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Publish button
        Button(
            onClick = onOpenPublish,
            colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .testTag("welcome_btn_publish")
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "+ Publicar o que Vendes",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
