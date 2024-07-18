package response;

import com.hotelbooking.hotel.model.Quarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Long idReserva;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private String nomeCompletoHospede;
    private String emailHospede;
    private int numeroAdultos;
    private int numeroCriancas;
    private int numeroTotalHospedes;
    private String codigoConfirmacaoReserva;
    private Quarto quarto;

    public ReservaResponse(Long idReserva, LocalDate dataCheckIn, LocalDate dataCheckOut, String codigoConfirmacaoReserva) {
        this.idReserva = idReserva;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.codigoConfirmacaoReserva = codigoConfirmacaoReserva;
    }
}
