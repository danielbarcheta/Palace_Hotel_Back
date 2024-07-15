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
    private String tipoQuarto;
    private BigDecimal precoQuarto;
    private boolean isReservada;
    private String foto;
    private List<ReservaResponse> reservas;

    public QuartoResponse(Long id, String tipoQuarto, BigDecimal precoQuarto) {
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.precoQuarto = precoQuarto;
    }

    public QuartoResponse(Long id, String tipoQuarto, BigDecimal precoQuarto, boolean isReservada, byte[] photoByte, List<ReservaResponse> reservas) {
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.precoQuarto = precoQuarto;
        this.isReservada = isReservada;
        this.foto = photoByte != null ? Base64.encodeBase64String(photoByte) : null;
        this.reservas = reservas;
    }
}
