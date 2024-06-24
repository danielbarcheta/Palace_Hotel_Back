package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Quarto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public interface QuartoService {
    Quarto addQuartoNovo(MultipartFile foto, String tipoQuarto, BigDecimal precoSala) throws SQLException, IOException;
}
