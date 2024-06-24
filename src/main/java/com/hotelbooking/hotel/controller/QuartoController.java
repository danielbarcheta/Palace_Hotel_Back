package com.hotelbooking.hotel.controller;

import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.service.QuartoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import response.QuartoResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@RestController
@RequestMapping("/quartos")
@RequiredArgsConstructor
public class QuartoController {

    @Autowired
    private final QuartoService quartoService;

    @PostMapping("/add/quarto-novo")
    public ResponseEntity<QuartoResponse> addQuartoNovo(
             @RequestParam("foto") MultipartFile foto,
             @RequestParam("tipoQuarto") String tipoQuarto,
             @RequestParam("precoSala") BigDecimal precoQuarto) throws SQLException, IOException {

        Quarto quartoSalvo = quartoService.addQuartoNovo(foto, tipoQuarto, precoQuarto);
        QuartoResponse response = new QuartoResponse(quartoSalvo.getId(), quartoSalvo.getTipoQuarto(),
                quartoSalvo.getPrecoQuarto());

        return ResponseEntity.ok(response);
    };

}
