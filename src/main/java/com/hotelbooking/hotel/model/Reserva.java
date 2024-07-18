package com.hotelbooking.hotel.model;

import jakarta.persistence.*;
import lombok.*;
import response.ReservaResponse;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;
    @Column(name = "check_In")
    private LocalDate dataCheckIn;
    @Column(name = "check_Out")
    private LocalDate dataCheckOut;
    @Column(name = "nome_Completo_Hospede")
    private String nomeCompletoHospede;
    @Column(name = "email_Hospede")
    private String emailHospede;
    @Column(name = "adultos")
    private int numeroAdultos;
    @Column(name = "criancas")
    private int numeroCriancas;
    @Column(name = "total_Hospedes")
    private int numeroTotalHospedes;
    @Column(name = "codigo_Confirmacao")
    private String codigoConfirmacaoReserva;
    @JoinColumn(name = "id_Quarto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Quarto quarto;

    private void calculaNumeroTotalHospedes(){
        this.numeroTotalHospedes = this.numeroAdultos + this.numeroCriancas;
    }

    public void setNumeroCriancas(int numeroCriancas) {
        this.numeroCriancas = numeroCriancas;
        this.calculaNumeroTotalHospedes();
    }
    public void setNumeroAdultos(int numeroAdultos) {
        this.numeroAdultos = numeroAdultos;
        this.calculaNumeroTotalHospedes();
    }


    public void setCodigoConfirmacaoReserva(String codigoConfirmacaoReserva) {
        this.codigoConfirmacaoReserva = codigoConfirmacaoReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public static ReservaResponse toReservaResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getIdReserva(),
                reserva.getDataCheckIn(),
                reserva.getDataCheckOut(),
                reserva.getNomeCompletoHospede(),
                reserva.getEmailHospede(),
                reserva.getNumeroAdultos(),
                reserva.getNumeroCriancas(),
                reserva.getNumeroTotalHospedes(),
                reserva.getCodigoConfirmacaoReserva(),
                reserva.getQuarto()
        );
    }
}
