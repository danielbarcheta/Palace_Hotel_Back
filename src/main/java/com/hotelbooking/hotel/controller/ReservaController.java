package com.hotelbooking.hotel.controller;

import com.hotelbooking.hotel.exception.ResourceNotFoundException;
import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.ReservaResponse;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private final ReservaService reservaService;

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

    @GetMapping("/confirmacao/{codigoConfirmacao}")
    public ResponseEntity<?> getReservasByCodigoConfirmacao (@PathVariable String codigoConfirmacao) {
        try {
            Reserva reserva = reservaService.findByCodigoConfirmacao(codigoConfirmacao);
            ReservaResponse reservaResponse = this.getReservaResponse(reserva);
            return ResponseEntity.ok(reservaResponse);
        } catch (ResourceNotFoundException excecao) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excecao.getMessage());

        }
    }

    public ReservaResponse getReservaResponse(Reserva reserva) {
        return new ReservaResponse(reserva.getIdReserva(), reserva.getDataCheckIn(), reserva.getDataCheckOut(), reserva.getCodigoConfirmacaoReserva());
    }
}
