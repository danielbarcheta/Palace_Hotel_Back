package com.hotelbooking.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoQuarto;
    private BigDecimal precoQuarto;
    private boolean isReservada;
    @OneToMany(mappedBy = "quarto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reserva> listaReservas;

    @Lob
    private Blob photo;

    public Quarto() {
        this.listaReservas = new ArrayList<>();
    }

    public void adicionarReserva(Reserva reserva){
        if(listaReservas == null){
            listaReservas = new ArrayList<Reserva>();
        }
        listaReservas.add(reserva);
        reserva.setQuarto(this);
        this.isReservada = true;
        String codigoReserva = RandomStringUtils.randomNumeric(14);
    }
}
