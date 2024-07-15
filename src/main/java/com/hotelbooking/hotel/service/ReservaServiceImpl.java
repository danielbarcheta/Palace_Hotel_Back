package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public List<Reserva> getAllReservasByQuartoId(Long id) {
        return this.reservaRepository.findByQuartoId(id);
    }
}
