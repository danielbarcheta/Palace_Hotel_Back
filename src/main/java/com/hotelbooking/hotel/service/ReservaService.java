package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {

    List<Reserva> getAllReservasByQuartoId(Long id);
    List<Reserva> getAllReservas();
    Optional<Reserva> getReservaById(Long id);
    String salvarReserva(Long quartoId, Reserva reserva);
    Boolean excluirReserva(Long reservaId);
    Reserva findByCodigoConfirmacao(String codigoConfirmacao);
}
