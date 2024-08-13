package com.hotelbooking.hotel.controller;

import com.hotelbooking.hotel.exception.ResourceNotFoundException;
import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.service.QuartoService;
import com.hotelbooking.hotel.service.ReservaService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/confirmacao/{codigoConfirmacao}")
    public ResponseEntity<?> getReservaByCodigoConfirmacao (@PathVariable String codigoConfirmacao) {
        try {
            Reserva reserva = reservaService.findByCodigoConfirmacao(codigoConfirmacao);
            ReservaResponse reservaResponse = this.getReservaResponse(reserva);
            return ResponseEntity.ok(reservaResponse);
        } catch (ResourceNotFoundException excecao) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excecao.getMessage());

        }
    }

    @PostMapping
    public ResponseEntity<?> salvarReserva(@RequestBody Reserva reserva, @PathVariable Long id) {
            Optional<Quarto> quarto = quartoService.getQuartoByQuartoId(id);
            if(quarto.isEmpty()) {

            }
            Reserva reservaSalva = reservaService.salvarReserva(reserva);
            return ResponseEntity.ok("Quarto salvo com sucesso! O código da reserva é"
            + reservaSalva.getCodigoConfirmacaoReserva());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaSalva = reservaService.getReservaById(id);
        if (reservaSalva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Reserva reservaAtualizada = reservaService.salvarReserva(reserva);
        return ResponseEntity.ok(reservaAtualizada);
    }


    public ReservaResponse getReservaResponse(Reserva reserva) {
        return new ReservaResponse(reserva.getIdReserva(), reserva.getDataCheckIn(), reserva.getDataCheckOut(), reserva.getCodigoConfirmacaoReserva());
    }
}
