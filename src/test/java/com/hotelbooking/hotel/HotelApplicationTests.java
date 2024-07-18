package com.hotelbooking.hotel;

import com.hotelbooking.hotel.repository.QuartoRepository;
import com.hotelbooking.hotel.service.QuartoService;
import com.hotelbooking.hotel.service.QuartoServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class HotelApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private QuartoService quartoService;

//    @Test
//    public void testGetFotoQuartoByQuartoId() throws SQLException {
//        Long quartoId = 1L; // Id de um quarto existente no seu banco de dados
//        byte[] fotoBytes = quartoService.getFotoQuartoByQuartoId(quartoId);
//        assertNotNull(fotoBytes);
//        Assert.assertTrue(fotoBytes.length > 0);
//    }

}
