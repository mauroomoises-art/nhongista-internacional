package com.example.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.R
import com.example.data.SampleData
import com.example.ui.theme.ThemeConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishProductDialog(
    onDismiss: () -> Unit,
    onPublish: (
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
    ) -> Unit
) {
    // 4 Mandatory fields state
    var phone by remember { mutableStateOf("") }
    var locationCity by remember { mutableStateOf("Maputo") }
    var locationSpecific by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var customImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedPresetImage by remember { mutableIntStateOf(R.drawable.app_icon_deal_1794260145369) }

    // Optional fields state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Telemóveis") }
    var sellerName by remember { mutableStateOf("") }
    var whatsApp by remember { mutableStateOf("") }

    // Validation feedback state
    var hasAttemptedSubmit by remember { mutableStateOf(false) }

    val isPhoneValid = phone.trim().length >= 8
    val isPriceValid = (priceText.toDoubleOrNull() ?: 0.0) > 0.0
    val isLocationValid = locationCity.isNotBlank()
    val isImageSelected = customImageUri != null || selectedPresetImage != 0

    val isFormValid = isPhoneValid && isPriceValid && isLocationValid && isImageSelected

    // Photo picker launcher (complies with Google Play policy)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            customImageUri = uri
        }
    }

    var cityDropdownExpanded by remember { mutableStateOf(false) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    val presetImages = listOf(
        Pair("Negócio / Geral", R.drawable.app_icon_deal_1794260145369),
        Pair("Capulanas", R.drawable.product_capulana_1794260259363),
        Pair("Telemóvel", R.drawable.product_smartphone_1794260259535),
        Pair("Banner Comercial", R.drawable.nhonguista_banner_1794260160007)
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .testTag("publish_product_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header of Modal
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
                                text = "Publicar o que vendes",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "Vende rápido para todo Moçambique sem comissões!",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("btn_close_publish")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Scrollable Form
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Mandatory notice
                    Surface(
                        color = Color(0xFFFEF2F2),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFECACA))),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "* Campos com asterisco são OBRIGATÓRIOS para garantir a segurança dos compradores em Moçambique.",
                            fontSize = 11.sp,
                            color = Color(0xFF991B1B),
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 1) FOTO DO PRODUTO (OBRIGATÓRIO)
                    Text(
                        text = "1. Imagem do que vendes *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    // Image preview container
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.5.dp,
                                color = if (hasAttemptedSubmit && !isImageSelected) Color.Red else ThemeConfig.AlibabaOrange,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color(0xFFF9FAFB)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (customImageUri != null) {
                            AsyncImage(
                                model = customImageUri,
                                contentDescription = "Foto selecionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = selectedPresetImage),
                                contentDescription = "Imagem de amostra",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Overlay button to upload custom photo
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp)
                                .testTag("btn_pick_photo")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Carregar Foto da Galeria", fontSize = 11.sp, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ou escolha uma imagem de catálogo rápido:",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Preset image chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presetImages.forEach { (label, resId) ->
                            val isSelected = (customImageUri == null && selectedPresetImage == resId)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) ThemeConfig.AlibabaOrange.copy(alpha = 0.15f) else Color(0xFFF3F4F6),
                                border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ThemeConfig.AlibabaOrange)) else null,
                                modifier = Modifier.clickable {
                                    customImageUri = null
                                    selectedPresetImage = resId
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = resId),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) ThemeConfig.AlibabaOrange else Color(0xFF374151)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2) NÚMERO DE CELULAR (OBRIGATÓRIO)
                    Text(
                        text = "2. Número de Celular * (Vodacom / Tmcel / Movitel)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        placeholder = { Text("Ex: 844935865 ou 821234567") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = hasAttemptedSubmit && !isPhoneValid,
                        supportingText = {
                            if (hasAttemptedSubmit && !isPhoneValid) {
                                Text("Número de celular obrigatório (mínimo 8 dígitos).", color = Color.Red)
                            } else {
                                Text("Os clientes ligarão diretamente para este número.", color = Color(0xFF6B7280))
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3) LOCALIZAÇÃO (OBRIGATÓRIO)
                    Text(
                        text = "3. Localização * (Província / Cidade e Bairro)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // City Dropdown
                    ExposedDropdownMenuBox(
                        expanded = cityDropdownExpanded,
                        onExpandedChange = { cityDropdownExpanded = !cityDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = locationCity,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Cidade / Província *") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = cityDropdownExpanded,
                            onDismissRequest = { cityDropdownExpanded = false }
                        ) {
                            SampleData.MOZAMBIQUE_CITIES.filter { it != "Todas as Cidades" }.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city) },
                                    onClick = {
                                        locationCity = city
                                        cityDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = locationSpecific,
                        onValueChange = { locationSpecific = it },
                        placeholder = { Text("Bairro / Rua ou Mercado (ex: Polana Caniço, Zimpeto, Maquinino)") },
                        label = { Text("Bairro / Ponto de Referência") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_location_specific")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4) PREÇO EM MT (OBRIGATÓRIO)
                    Text(
                        text = "4. Preço em MT (Meticais) *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        placeholder = { Text("Ex: 1500") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.AttachMoney, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                        },
                        suffix = {
                            Text("MT", fontWeight = FontWeight.Bold, color = ThemeConfig.AlibabaOrange)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = hasAttemptedSubmit && !isPriceValid,
                        supportingText = {
                            if (hasAttemptedSubmit && !isPriceValid) {
                                Text("Preço em MT deve ser superior a zero.", color = Color.Red)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_price")
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // CAMPOS OPCIONAIS (Divisor)
                    Surface(
                        color = Color(0xFFF3F4F6),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "CAMPOS OPCIONAIS (Para destacar mais o teu anúncio)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4B5563),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Nome do Produto (Opcional)
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Nome do Produto") },
                        placeholder = { Text("Ex: Capulana Samakaka original ou iPhone 13 128GB") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, tint = Color(0xFF6B7280))
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_title")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Categoria (Opcional)
                    ExposedDropdownMenuBox(
                        expanded = categoryDropdownExpanded,
                        onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Categoria") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Category, contentDescription = null, tint = Color(0xFF6B7280))
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryDropdownExpanded,
                            onDismissRequest = { categoryDropdownExpanded = false }
                        ) {
                            SampleData.CATEGORIES.filter { it != "Todas as Categorias" }.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        category = cat
                                        categoryDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nome do Vendedor & WhatsApp (Opcional)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = sellerName,
                            onValueChange = { sellerName = it },
                            label = { Text("Nome do Vendedor") },
                            placeholder = { Text("Ex: Mama Zinha") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color(0xFF6B7280))
                            },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = whatsApp,
                            onValueChange = { whatsApp = it },
                            label = { Text("WhatsApp") },
                            placeholder = { Text("Ex: 844935865") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Descrição (Opcional)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descrição detalhada do artigo") },
                        placeholder = { Text("Detalhes de conservação, garantias, modo de entrega, etc.") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = Color(0xFF6B7280))
                        },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Bottom Action Bar
                Surface(
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E7EB)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Text("Cancelar", color = Color(0xFF374151), fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                hasAttemptedSubmit = true
                                if (isFormValid) {
                                    val finalPrice = priceText.toDoubleOrNull() ?: 0.0
                                    onPublish(
                                        phone.trim(),
                                        locationCity,
                                        locationSpecific.trim(),
                                        finalPrice,
                                        title.trim(),
                                        description.trim(),
                                        category,
                                        sellerName.trim(),
                                        whatsApp.trim().ifBlank { null },
                                        selectedPresetImage,
                                        customImageUri?.toString()
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(2f)
                                .height(46.dp)
                                .testTag("btn_submit_publish")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Publicar no Feed Agora",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
