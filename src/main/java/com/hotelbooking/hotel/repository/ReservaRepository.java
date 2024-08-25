package com.hotelbooking.hotel.repository;

import com.hotelbooking.hotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByQuartoId(Long quartoId);

    Reserva findByCodigoConfirmacaoReserva(String codigoConfirmacao);
}
