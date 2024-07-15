package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Quarto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface QuartoService {
    Quarto addQuartoNovo(MultipartFile foto, String tipoQuarto, BigDecimal precoSala) throws SQLException, IOException;

    List<String> getAllTiposQuarto();

    List<Quarto> getAllQuartos();

    byte[] getFotoQuartoByQuartoId(Long quartoId) throws SQLException;

    void deletarQuarto(Long quartoId);
}
