package com.inditex.priceApi;

import com.inditex.priceApi.infraestructure.mapper.PriceMapper;
import com.inditex.priceApi.infraestructure.repositories.PriceRepository;
import com.inditex.priceApi.infraestructure.rest.controller.PriceController;
import com.inditex.priceApi.domain.model.Brand;
import com.inditex.priceApi.domain.model.Currency;
import com.inditex.priceApi.domain.model.Price;
import com.inditex.priceApi.application.service.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    public void testGetApplicablePrice() throws Exception {
        Brand brand = new Brand(1L, "ZARA");
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-06-14T23:59:59");

        Price mockPrice = new Price();
        mockPrice.setId(1L);
        mockPrice.setBrand(brand);
        mockPrice.setStartDate(startDate);
        mockPrice.setEndDate(endDate);
        mockPrice.setPriceList(1);
        mockPrice.setProductId(35455);
        mockPrice.setPriority(0);
        mockPrice.setPrice(BigDecimal.valueOf(35.50));
        mockPrice.setCurrency(Currency.EUR);

        when(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-14T10:00:00")))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    public void should_return_400_when_missing_parameters() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                                .param("productId", "35455")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_400_when_date_format_is_invalid() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "not-a-date"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_404_when_price_not_found() throws Exception {
        when(priceService.getApplicablePrice(99999, 1, LocalDateTime.parse("2020-06-14T10:00:00")))
                .thenThrow(new com.inditex.priceApi.infraestructure.exceptions.PriceException(
                        "No se encontró un precio aplicable para el productId=99999, brandId=1, date=2020-06-14T10:00"));

        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "99999")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontró un precio aplicable para el productId=99999, brandId=1, date=2020-06-14T10:00"));
    }
}