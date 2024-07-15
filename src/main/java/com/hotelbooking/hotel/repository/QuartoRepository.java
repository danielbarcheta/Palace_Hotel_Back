package com.hotelbooking.hotel.repository;

import com.hotelbooking.hotel.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    @Query("SELECT DISTINCT  r.tipoQuarto FROM Quarto r")
    List<String> findDistinctTipoQuarto();
}
