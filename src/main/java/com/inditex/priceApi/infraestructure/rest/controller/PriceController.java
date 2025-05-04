package com.inditex.priceApi.infraestructure.rest.controller;

import com.inditex.priceApi.application.service.PriceService;
import com.inditex.priceApi.domain.model.Price;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/prices")
@Tag(name = "Prices", description = "API para obtener precios")
public class PriceController {
    private final PriceService priceService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(
            summary = "Obtener precio aplicable",
            description = "Devuelve el precio más relevante según la fecha proporcionada, el ID del producto y el ID de la marca."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Precio encontrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en los parámetros de entrada"),
            @ApiResponse(responseCode = "404", description = "Precio no encontrado para los criterios especificados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/applicable")
    public Price getApplicablePrice(
            @Parameter(description = "ID del producto", example = "35455")
            @NotNull @RequestParam Integer productId,
            @Parameter(description = "ID de la marca", example = "1")
            @NotNull @RequestParam Integer brandId,
            @Parameter(description = "Fecha de aplicación en formato yyyy-MM-dd'T'HH:mm:ss", example = "2020-06-14T10:00:00")
            @NotNull @RequestParam String date) {

        LocalDateTime applicationDate = LocalDateTime.parse(date, dateTimeFormatter);
        return priceService.getApplicablePrice(productId, brandId, applicationDate);
    }
}
