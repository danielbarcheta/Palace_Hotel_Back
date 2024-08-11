package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Reserva;

import java.util.List;

public interface ReservaService {

    List<Reserva> getAllReservasByQuartoId(Long id);
    List<Reserva> getAllReservas();
}
