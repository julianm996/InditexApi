package com.inditex.priceApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = BudgetPriceApi.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class PriceApiE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_correct_price_when_request_is_valid() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.brand.id").value(1))
                .andExpect(jsonPath("$.productId").value(35455));
    }

    @Test
    public void should_return_price_25_45_on_14th_at_16h() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.priceList").value(2));
    }

    @Test
    public void should_return_price_35_50_on_14th_at_21h() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    public void should_return_price_30_50_on_15th_at_10h() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.priceList").value(3));
    }

    @Test
    public void should_return_price_38_95_on_16th_at_21h() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.priceList").value(4));
    }

    @Test
    public void should_return_404_when_no_price_is_found() throws Exception {
        mockMvc.perform(get("/prices/applicable")
                        .param("productId", "99999") // no existe en los datos
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No applicable price found for productId=99999, brandId=1, date=2020-06-14T10:00"));
    }
}
