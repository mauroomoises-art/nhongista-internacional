package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.AppThemeType
import com.example.ui.theme.ThemeConfig

@Composable
fun SettingsAboutDialog(
    currentTheme: AppThemeType,
    isDarkMode: Boolean,
    onSelectTheme: (AppThemeType) -> Unit,
    onToggleDarkMode: () -> Unit,
    onPickCustomBackground: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onPickCustomBackground(uri.toString())
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .testTag("settings_about_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Surface(
                    color = ThemeConfig.AlibabaOrange,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Definições & Sobre o Site",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "NHONGUISTA INTERNACIONAL - Moçambique",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar", tint = Color.White)
                        }
                    }
                }

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // SELETOR DE TEMAS INCRÍVEIS
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = ThemeConfig.AlibabaOrange,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Seletor de Temas (Muda na hora!)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E1E)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val themeOptions = listOf(
                        Triple(AppThemeType.FIRE_ORANGE, ThemeConfig.AlibabaOrange, "Laranja Fogo (Clássico Alibaba)"),
                        Triple(AppThemeType.LIGHT_GREEN, ThemeConfig.GreenSecondary, "Verde Claro (Esmeralda Fresco)"),
                        Triple(AppThemeType.OCEAN_MAN_CHAIR, Color(0xFF0284C7), "Oceano com homem sentado na cadeira"),
                        Triple(AppThemeType.NEON_PURPLE, ThemeConfig.PurpleNeonPrimary, "Roxo Neon (Cyberpunk Moderno)"),
                        Triple(AppThemeType.OCEAN_BLUE, ThemeConfig.OceanBluePrimary, "Azul Oceano (Mar Profundo)")
                    )

                    themeOptions.forEach { (type, colorBadge, label) ->
                        val isSelected = currentTheme == type
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onSelectTheme(type) }
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) ThemeConfig.AlibabaOrange else Color(0xFFE5E7EB),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFFFF7ED) else Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(colorBadge)
                                            .border(1.dp, Color.White, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = label,
                                            fontSize = 13.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = Color(0xFF1E1E1E)
                                        )
                                        Text(
                                            text = type.description,
                                            fontSize = 10.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                    }
                                }

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selecionado",
                                        tint = ThemeConfig.AlibabaOrange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Opção de colocar foto como fundo
                    OutlinedButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = ThemeConfig.AlibabaOrange,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Colocar Foto da Galeria como Fundo",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ThemeConfig.AlibabaOrange
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Switch Modo Claro / Modo Escuro
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    tint = ThemeConfig.AlibabaOrange,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isDarkMode) "Modo Escuro Ativo" else "Modo Claro Ativo",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E1E1E)
                                    )
                                    Text(
                                        text = "Alterne a iluminação do aplicativo",
                                        fontSize = 10.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }

                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { onToggleDarkMode() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = ThemeConfig.AlibabaOrange
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = Color(0xFFE5E7EB))
                    Spacer(modifier = Modifier.height(16.dp))

                    // CRÉDITOS DO CRIADOR (DESTACADO CONFORME PEDIDO)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(ThemeConfig.AlibabaOrange),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.app_icon_deal_1794260145369),
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "NHONGUISTA INTERNACIONAL",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black,
                                        color = ThemeConfig.AlibabaOrange
                                    )
                                    Text(
                                        text = "Criado por Lúcifer O'black",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Criador do site: Lúcifer O'black",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF3F4F6)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

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
                                    text = "Contacto do Criador: 844935865",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00E676)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // COMO FUNCIONA O PAGAMENTO NA ENTREGA
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF86EFAC))),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Como Funciona o Pagamento na Entrega?",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "1. Escolhe o produto que pretendes no site.\n" +
                                        "2. Fazes a encomenda SEM adiantar nenhum valor ou taxa inicial.\n" +
                                        "3. O entregador ou fornecedor leva a mercadoria até à tua morada (Maputo, Matola, Beira, Nampula, etc.).\n" +
                                        "4. Verificas o produto com os teus próprios olhos, experimentas e certificas-te da qualidade.\n" +
                                        "5. Só depois de aprovares é que efetuas o pagamento em dinheiro vivo ou através do M-Pesa ao entregador.\n" +
                                        "Zero burlas, zero riscos, 100% de confiança para o povo moçambicano!",
                                fontSize = 11.sp,
                                color = Color(0xFF166534),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // TEXTÃO PROFISSIONAL SOBRE VANTAGENS DO SITE (CONFORME PEDIDO)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Sobre o NHONGUISTA INTERNACIONAL",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E1E1E)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "O NHONGUISTA INTERNACIONAL nasceu com a missão grandiosa de revolucionar e digitalizar o comércio em todo o território de Moçambique, desde a Ponta do Ouro até ao Rio Rovuma.\n\n" +
                                        "Inspirado no modelo de sucesso global do Alibaba, criámos uma plataforma robusta, rápida e acessível que conecta diretamente quem tem produtos a vender com milhares de clientes compradores ávidos por fechar negócio (a verdadeira 'bolada').\n\n" +
                                        "Principais Vantagens:\n" +
                                        "• Entrada 100% Gratuita: Sem taxas de inscrição, sem cobranças ocultas para navegar ou pesquisar.\n" +
                                        "• Empoderamento dos Nhonguistas: Pequenos comerciantes informais, alfaiates, revendedores de telemóveis e grandes armazéns têm agora uma montra digital moderna com alcance internacional.\n" +
                                        "• Negociação Direta e Transparente: Comunicação instantânea por WhatsApp, chamada telefónica ou chat integrado.\n" +
                                        "• Preços Transparentes em Meticais (MT): Sem conversões confusas, os valores são reais e adaptados à realidade económica do nosso país.\n" +
                                        "• Segurança Inviolável: A nossa política de 'Pagamento na Entrega' protege o suor do trabalhador moçambicano contra fraudes virtuais.\n\n" +
                                        "O Nhonguista Internacional é mais do que um aplicativo: é o motor económico que move empreendedores em Maputo, Matola, Sofala, Zambézia, Nampula, Cabo Delgado, Niassa, Manica, Gaza e Inhambane. Juntos fechamos a melhor bolada!",
                                fontSize = 11.sp,
                                color = Color(0xFF374151),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
