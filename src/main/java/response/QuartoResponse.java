package response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class QuartoResponse {
    private Long id;
    private String tipoSala;
    private BigDecimal precoSala;
    private boolean isReservada;
    private String foto;
    private List<ReservaResponse> reservas;

    public QuartoResponse(Long id, String tipoSala, BigDecimal precoSala) {
        this.id = id;
        this.tipoSala = tipoSala;
        this.precoSala = precoSala;
    }

    public QuartoResponse(Long id, String tipoSala, BigDecimal precoSala, boolean isReservada, byte[] photoByte, List<ReservaResponse> reservas) {
        this.id = id;
        this.tipoSala = tipoSala;
        this.precoSala = precoSala;
        this.isReservada = isReservada;
        this.foto = photoByte != null ? Base64.encodeBase64String(photoByte) : null;
        this.reservas = reservas;
    }
}
