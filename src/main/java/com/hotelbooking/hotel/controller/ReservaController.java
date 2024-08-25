package com.hotelbooking.hotel.controller;

import com.hotelbooking.hotel.exception.InvalidBookingRequestException;
import com.hotelbooking.hotel.exception.ResourceNotFoundException;
import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.service.QuartoService;
import com.hotelbooking.hotel.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ReservaResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private final ReservaService reservaService;
    @Autowired
    private final QuartoService quartoService;

    @GetMapping("/todas-reservas")
    public ResponseEntity<List<ReservaResponse>> getTodasReservas () {
        List<Reserva> reservas = reservaService.getAllReservas();
        List<ReservaResponse> reservaResponses = new ArrayList<>();
        for(Reserva reserva : reservas) {
            ReservaResponse reservaResponse = this.getReservaResponse(reserva);
            reservaResponses.add(reservaResponse);
        }
        return ResponseEntity.ok(reservaResponses);
    }

    @GetMapping("/reserva/{codigoConfirmacao}")
    public ResponseEntity<?> getReservaByCodigoConfirmacao (@PathVariable String codigoConfirmacao) {
        try {
            Reserva reserva = reservaService.findByCodigoConfirmacao(codigoConfirmacao);
            ReservaResponse reservaResponse = this.getReservaResponse(reserva);
            return ResponseEntity.ok(reservaResponse);
        } catch (ResourceNotFoundException excecao) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excecao.getMessage());

        }
    }

    @PostMapping("/quarto/{id}/reserva")
    public ResponseEntity<?> salvarReserva(@RequestBody Reserva reserva, @PathVariable Long id) {
        Optional<Quarto> quarto = quartoService.getQuartoByQuartoId(id);
        if(quarto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não encontrado!");
        }
        try {
            String codigoConfirmacaoReserva = reservaService.salvarReserva(id, reserva);
            return ResponseEntity.ok("Quarto salvo com sucesso! O código da reserva é"
                    + codigoConfirmacaoReserva);
        } catch (InvalidBookingRequestException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<String> excluirReserva(@PathVariable Long reservaId) {
        boolean excluida = reservaService.excluirReserva(reservaId);
        if (excluida) {
            return ResponseEntity.ok("Reserva excluída com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ReservaResponse getReservaResponse(Reserva reserva) {
        return new ReservaResponse(reserva.getIdReserva(), reserva.getDataCheckIn(), reserva.getDataCheckOut(), reserva.getCodigoConfirmacaoReserva());
    }
}
