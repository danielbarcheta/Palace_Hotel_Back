package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.exception.InvalidBookingRequestException;
import com.hotelbooking.hotel.model.Quarto;
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

    @Autowired
    private final QuartoService quartoService;

    public ReservaServiceImpl(ReservaRepository reservaRepository, QuartoService quartoService) {
        this.reservaRepository = reservaRepository;
        this.quartoService = quartoService;
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
    public String salvarReserva(Long quartoId, Reserva reservaRequest) {
        if(reservaRequest.getDataCheckOut().isBefore(reservaRequest.getDataCheckIn())) {
            throw new InvalidBookingRequestException("Data de Check-out anterior a data de Check-in");
        }
        Quarto quarto = this.quartoService.getQuartoByQuartoId(quartoId).get();
        List<Reserva> reservasExistentes = quarto.getListaReservas();
        boolean isQuartoDisponivel = isQuartoDisponivel(reservaRequest, reservasExistentes);
        if(isQuartoDisponivel) {
             quarto.adicionarReserva(reservaRequest);
             reservaRepository.save(reservaRequest);
        } else {
            throw new InvalidBookingRequestException("Não há disponibilidade para essa reserva nas datas informadas");
        }
        return reservaRequest.getCodigoConfirmacaoReserva();
    }

    @Override
    public Reserva findByCodigoConfirmacao(String codigoConfirmacao) {
        return reservaRepository.findByCodigoConfirmacaoReserva(codigoConfirmacao);
    }

    public Boolean excluirReserva(Long reservaId) {
        Optional<Reserva> reserva = this.reservaRepository.findById(reservaId);
        if(!reserva.isPresent()) {
            return false;
        }
        else {
            this.reservaRepository.delete(reserva.get());
            return true;
        }
    }

    private boolean isQuartoDisponivel(Reserva reservaRequest, List<Reserva> reservasExistentes) {
        return reservasExistentes.stream()
                .noneMatch(reservaExistente ->
                        reservaRequest.getDataCheckIn().equals(reservaExistente.getDataCheckIn())

                        || (reservaRequest.getDataCheckOut().isBefore(reservaExistente.getDataCheckOut())
                        && reservaRequest.getDataCheckIn().isBefore(reservaExistente.getDataCheckOut()))

                        || (reservaRequest.getDataCheckIn().isBefore(reservaExistente.getDataCheckIn())
                        && reservaRequest.getDataCheckOut().equals(reservaExistente.getDataCheckOut()))

                        || (reservaRequest.getDataCheckIn().isBefore(reservaExistente.getDataCheckIn())
                        && reservaRequest.getDataCheckOut().isAfter(reservaExistente.getDataCheckOut()))

                        || (reservaRequest.getDataCheckIn().equals(reservaExistente.getDataCheckOut())
                        && reservaRequest.getDataCheckOut().equals(reservaExistente.getDataCheckIn()))

                        || (reservaRequest.getDataCheckIn().equals(reservaExistente.getDataCheckOut())
                        && reservaRequest.getDataCheckOut().equals(reservaRequest .getDataCheckIn())));
    }


}
