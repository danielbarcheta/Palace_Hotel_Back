package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.repository.QuartoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import response.QuartoResponse;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class QuartoServiceImpl implements QuartoService {

    @Autowired
    private final QuartoRepository quartoRepository;

    @Override
    public Quarto addQuartoNovo(MultipartFile file, String tipoQuarto, BigDecimal precoQuarto) throws SQLException, IOException {
        Quarto quarto = new Quarto();
        quarto.setTipoQuarto(tipoQuarto);
        quarto.setPrecoQuarto(precoQuarto);
        if(!file.isEmpty()){
            byte[] fotoBytes = file.getBytes();
            Blob fotoBlob = new SerialBlob(fotoBytes);
            quarto.setPhoto(fotoBlob);
        }
        return quartoRepository.save(quarto);
    }
}
