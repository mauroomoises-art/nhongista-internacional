package com.example

import com.example.viewmodel.NhonguistaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testPublishingProductAndCashOnDelivery() {
        val viewModel = NhonguistaViewModel()
        val initialCount = viewModel.products.value.size

        val success = viewModel.publishProduct(
            phone = "844935865",
            locationCity = "Maputo",
            locationSpecific = "Polana Caniço",
            price = 1500.0,
            title = "Capulana Especial",
            description = "Capulana autêntica moçambicana",
            category = "Capulanas & Beleza",
            sellerName = "Lúcifer O'black",
            whatsApp = "844935865",
            selectedPresetImage = null,
            customImageUri = null
        )

        assertTrue(success)
        assertEquals(initialCount + 1, viewModel.products.value.size)
        val firstProduct = viewModel.products.value.first()
        assertEquals("Capulana Especial", firstProduct.title)
        assertEquals("844935865", firstProduct.supplierPhone)
        assertTrue(firstProduct.acceptsCashOnDelivery)
    }

    @Test
    fun testCurrencyFormatting() {
        val formatted = NhonguistaViewModel.formatMt(15000.0)
        assertTrue(formatted.contains("MT"))
    }
}
