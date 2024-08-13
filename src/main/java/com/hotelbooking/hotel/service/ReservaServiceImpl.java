package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.model.Reserva;
import com.hotelbooking.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Reserva> getAllReservas() {
        return this.reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public Reserva salvarReserva(Long quartoId, Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva findByCodigoConfirmacao(String codigoConfirmacao) {
        return reservaRepository.findByCodigoConfirmacao(codigoConfirmacao);
    }


}
