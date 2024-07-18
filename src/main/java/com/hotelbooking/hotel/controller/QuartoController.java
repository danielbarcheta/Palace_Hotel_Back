package com.hotelbooking.hotel.controller;

import com.hotelbooking.hotel.exception.PhotoRetrievalException;
import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.repository.QuartoRepository;
import com.hotelbooking.hotel.service.QuartoService;
import com.hotelbooking.hotel.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.QuartoResponse;
import response.ReservaResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/quartos")
@RequiredArgsConstructor
public class QuartoController {

    @Autowired
    private final QuartoService quartoService;
    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private ReservaService reservaService;

    @PostMapping("/add/quarto-novo")
    public ResponseEntity<QuartoResponse> addQuartoNovo(
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("tipoQuarto") String tipoQuarto,
            @RequestParam("precoQuarto") BigDecimal precoQuarto) throws SQLException, IOException {

        Quarto quartoSalvo = quartoService.addQuartoNovo(foto, tipoQuarto, precoQuarto);
        QuartoResponse response = new QuartoResponse(quartoSalvo.getId(), quartoSalvo.getTipoQuarto(),
                quartoSalvo.getPrecoQuarto());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipos-quarto")
    public List<String> getTiposQuarto() {
        return quartoRepository.findDistinctTipoQuarto();
    }

    @GetMapping("/todos-quartos")
    public ResponseEntity<List<QuartoResponse>> getAllQuartos() {
        List<QuartoResponse> quartoResponses = quartoRepository.findAll().stream()
                .map(quarto -> {
                    QuartoResponse response = new QuartoResponse(
                            quarto.getId(),
                            quarto.getTipoQuarto(),
                            quarto.getPrecoQuarto()
                    );

                    if (quarto.getPhoto() != null && quarto.getPhoto().length > 0) {
                        // Convertendo byte[] para base64
                        String base64Photo = Base64.encodeBase64String(quarto.getPhoto());
                        response.setFoto(base64Photo);
                    }

                    List<Reserva> reservas = reservaService.getAllReservasByQuartoId(quarto.getId());
                    List<ReservaResponse> reservaResponses = reservas.stream()
                            .map(reserva -> new ReservaResponse(
                                    reserva.getIdReserva(),
                                    reserva.getDataCheckIn(),
                                    reserva.getDataCheckOut(),
                                    reserva.getCodigoConfirmacaoReserva()
                            ))
                            .collect(Collectors.toList());

                    response.setReservas(reservaResponses);
                    return response;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(quartoResponses);
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<QuartoResponse> getQuarto(@PathVariable("quartoId") Long id) {
        Optional<Quarto> quartoSalvo = quartoService.getQuartoByQuartoId(id);
        if(quartoSalvo.isPresent()){
            Quarto quarto = quartoSalvo.get();
            QuartoResponse quartoResponse = Quarto.toQuartoResponse(quarto);
            return ResponseEntity.ok(quartoResponse);
        }
        return null;
    }

    @DeleteMapping("/deletar/quarto/{quartoId}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable("quartoId") Long id) {
        quartoService.deletarQuarto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<QuartoResponse> atualizaQuarto(@PathVariable("quartoId") Long id,
                                                         @RequestParam(required = false) String tipoQuarto,
                                                         @RequestParam(required = false) String precoQuarto,
                                                         @RequestParam(required = false) MultipartFile foto) throws SQLException, IOException {
    byte[] photoBytes = foto != null && !foto.isEmpty() ?
            foto.getBytes() : quartoService.getFotoQuartoByQuartoId(id);
        Quarto quarto = quartoService.atualizaQuarto(id, tipoQuarto, precoQuarto, photoBytes);
        quarto.setPhoto(photoBytes);

        QuartoResponse quartoResponse = Quarto.toQuartoResponse(quarto);
        return ResponseEntity.ok(quartoResponse);
    }
}

