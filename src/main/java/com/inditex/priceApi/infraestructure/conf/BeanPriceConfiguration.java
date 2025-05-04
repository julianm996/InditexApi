package com.inditex.priceApi.infraestructure.conf;


import com.inditex.priceApi.application.service.DomainPriceService;
import com.inditex.priceApi.application.service.PriceService;
import com.inditex.priceApi.domain.port.PricePort;
import com.inditex.priceApi.infraestructure.repositories.PriceRepository;
import com.inditex.priceApi.infraestructure.adapter.PricePortAdapter;
import com.inditex.priceApi.infraestructure.mapper.PriceMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPriceConfiguration {

    @Bean
    public PricePort pricePort(PriceRepository priceRepository, PriceMapper priceMapper) {
        return new PricePortAdapter(priceRepository, priceMapper);
    }

    @Bean
    public PriceService priceService(PricePort pricePort) {
        return new DomainPriceService(pricePort);
    }
}
