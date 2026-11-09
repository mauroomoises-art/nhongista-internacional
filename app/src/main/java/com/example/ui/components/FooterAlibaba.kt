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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.ThemeConfig

@Composable
fun FooterAlibaba(
    onCityClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp)
    ) {
        // Trust Badges Row (Estilo Alibaba Trade Assurance)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = ThemeConfig.AlibabaOrange,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pague na Entrega",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "100% Protegido",
                    color = Color(0xFF9CA3AF),
                    fontSize = 9.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = null,
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Entrega Rápida",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Em todo Moçambique",
                    color = Color(0xFF9CA3AF),
                    fontSize = 9.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.SupportAgent,
                    contentDescription = null,
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Apoio 24/7",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Linha 844935865",
                    color = Color(0xFF9CA3AF),
                    fontSize = 9.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(16.dp))

        // Brand & Creator Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .border(1.5.dp, ThemeConfig.AlibabaOrange, RoundedCornerShape(6.dp))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon_deal_1794260145369),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "NHONGUISTA ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = ThemeConfig.AlibabaOrange
                    )
                    Text(
                        text = "INTERNACIONAL",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Text(
                    text = "Criador do site: Lúcifer O'black",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00E676)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Direct call to creator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:844935865")
                    }
                    context.startActivity(dialIntent)
                }
                .padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                tint = Color(0xFF00E676),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Contacto Oficial: 844935865",
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Cidades em Destaque
        Text(
            text = "Mercados Ativos em Moçambique:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD1D5DB)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Maputo", "Matola", "Beira", "Nampula", "Tete").forEach { city ->
                Text(
                    text = city,
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF),
                    modifier = Modifier.clickable { onCityClick(city) }
                )
                Text(text = "•", fontSize = 10.sp, color = Color(0xFF4B5563))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Pagamentos aceites
        Text(
            text = "Pagamento na Entrega (Dinheiro vivo ao entregador) | M-Pesa (Vodacom) | e-Mola (Movitel)",
            fontSize = 10.sp,
            color = Color(0xFF6B7280),
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color(0xFF374151))
        Spacer(modifier = Modifier.height(12.dp))

        // Copyright Notice
        Text(
            text = "© 2026 NHONGUISTA INTERNACIONAL - Criado por Lúcifer O'black. Todos os direitos reservados. O maior marketplace de Moçambique.",
            fontSize = 10.sp,
            color = Color(0xFF6B7280),
            lineHeight = 14.sp
        )
    }
}
