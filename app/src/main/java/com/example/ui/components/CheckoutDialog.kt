package com.example.ui.components

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.SampleData
import com.example.data.model.CartItem
import com.example.ui.theme.ThemeConfig
import com.example.viewmodel.NhonguistaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutDialog(
    cartItems: List<CartItem>,
    totalMt: Double,
    onDismiss: () -> Unit,
    onConfirmOrder: (
        paymentMethod: String,
        customerName: String,
        customerPhone: String,
        deliveryCity: String,
        deliveryAddress: String
    ) -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("Pagamento na Entrega") }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var mpesaPhone by remember { mutableStateOf("") }
    var deliveryCity by remember { mutableStateOf("Maputo") }
    var deliveryAddress by remember { mutableStateOf("") }
    var cityDropdownExpanded by remember { mutableStateOf(false) }

    var hasAttemptedSubmit by remember { mutableStateOf(false) }

    val isPhoneValid = customerPhone.trim().length >= 8
    val isNameValid = customerName.trim().isNotBlank()
    val isAddressValid = deliveryAddress.trim().isNotBlank()

    val isFormValid = isPhoneValid && isNameValid && isAddressValid

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
                .testTag("checkout_dialog")
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
                                text = "Finalizar Encomenda",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "Pagamento na Entrega em Moçambique",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar", tint = Color.White)
                        }
                    }
                }

                // Body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Summary Banner
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Resumo da Compra (${cartItems.sumOf { it.quantity }} itens):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4B5563)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            cartItems.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${item.quantity}x ${item.product.title}",
                                        fontSize = 11.sp,
                                        color = Color(0xFF1E1E1E),
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1
                                    )
                                    Text(
                                        text = NhonguistaViewModel.formatMt(item.product.priceMt * item.quantity),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E1E1E)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total a Pagar:",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E1E1E)
                                )
                                Text(
                                    text = NhonguistaViewModel.formatMt(totalMt),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ThemeConfig.AlibabaOrange
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // MÉTODO DE PAGAMENTO (DESTAQUE BEM VISÍVEL)
                    Text(
                        text = "Escolha o Método de Pagamento:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 1: Pagamento na Entrega (Primary & Recommended)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPaymentMethod = "Pagamento na Entrega" }
                            .border(
                                width = if (selectedPaymentMethod == "Pagamento na Entrega") 2.dp else 1.dp,
                                color = if (selectedPaymentMethod == "Pagamento na Entrega") Color(0xFF16A34A) else Color(0xFFE5E7EB),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPaymentMethod == "Pagamento na Entrega") Color(0xFFF0FDF4) else Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = if (selectedPaymentMethod == "Pagamento na Entrega") Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (selectedPaymentMethod == "Pagamento na Entrega") Color(0xFF16A34A) else Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Pagamento na Entrega",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF15803D)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = Color(0xFF16A34A),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "RECOMENDADO",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "O cliente SÓ PAGA quando receber e inspecionar a encomenda pessoalmente! Podes pagar em dinheiro ou por M-Pesa ao entregador quando a mercadoria chegar.",
                                    fontSize = 11.sp,
                                    color = Color(0xFF166534),
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 2: M-Pesa (Alternative)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPaymentMethod = "M-Pesa Moçambique" }
                            .border(
                                width = if (selectedPaymentMethod == "M-Pesa Moçambique") 2.dp else 1.dp,
                                color = if (selectedPaymentMethod == "M-Pesa Moçambique") ThemeConfig.AlibabaOrange else Color(0xFFE5E7EB),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPaymentMethod == "M-Pesa Moçambique") Color(0xFFFFF7ED) else Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = if (selectedPaymentMethod == "M-Pesa Moçambique") Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (selectedPaymentMethod == "M-Pesa Moçambique") ThemeConfig.AlibabaOrange else Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "M-Pesa Moçambique (Vodacom)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E1E1E)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Pague diretamente pela sua carteira móvel M-Pesa (84/85). Receberás um aviso no telemóvel para autorizar a transação.",
                                    fontSize = 11.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }
                        }
                    }

                    if (selectedPaymentMethod == "M-Pesa Moçambique") {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = mpesaPhone,
                            onValueChange = { mpesaPhone = it },
                            label = { Text("Número M-Pesa (84 ou 85)") },
                            placeholder = { Text("Ex: 844935865") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.PhoneAndroid, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // DADOS DE ENTREGA
                    Text(
                        text = "Endereço de Entrega em Moçambique:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Nome do Cliente
                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Nome Completo *") },
                        placeholder = { Text("Ex: António Macamo") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                        },
                        isError = hasAttemptedSubmit && !isNameValid,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_input_name")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Telefone
                    OutlinedTextField(
                        value = customerPhone,
                        onValueChange = { customerPhone = it },
                        label = { Text("Telefone para contacto da entrega *") },
                        placeholder = { Text("Ex: 844935865") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = hasAttemptedSubmit && !isPhoneValid,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_input_phone")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cidade Dropdown
                    ExposedDropdownMenuBox(
                        expanded = cityDropdownExpanded,
                        onExpandedChange = { cityDropdownExpanded = !cityDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = deliveryCity,
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
                                        deliveryCity = city
                                        cityDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bairro e Morada
                    OutlinedTextField(
                        value = deliveryAddress,
                        onValueChange = { deliveryAddress = it },
                        label = { Text("Bairro, Rua e Ponto de Referência *") },
                        placeholder = { Text("Ex: Bairro Alto Maé, Rua da Resistência, perto da farmácia") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = ThemeConfig.AlibabaOrange)
                        },
                        isError = hasAttemptedSubmit && !isAddressValid,
                        minLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_input_address")
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Bottom Action
                Surface(
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = {
                                hasAttemptedSubmit = true
                                if (isFormValid) {
                                    onConfirmOrder(
                                        selectedPaymentMethod,
                                        customerName.trim(),
                                        customerPhone.trim(),
                                        deliveryCity,
                                        deliveryAddress.trim()
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ThemeConfig.AlibabaOrange),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("btn_confirm_order")
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Confirmar com $selectedPaymentMethod",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
