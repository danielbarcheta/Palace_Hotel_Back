package com.hotelbooking.hotel.service;

import com.hotelbooking.hotel.exception.PhotoRetrievalException;
import com.hotelbooking.hotel.exception.ResourceNotFoundException;
import com.hotelbooking.hotel.model.Quarto;
import com.hotelbooking.hotel.repository.QuartoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import response.QuartoResponse;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuartoServiceImpl implements QuartoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private final QuartoRepository quartoRepository;

    @Override
    public Quarto addQuartoNovo(MultipartFile file, String tipoQuarto, BigDecimal precoQuarto) throws SQLException, IOException {
        Quarto quarto = new Quarto();
        quarto.setTipoQuarto(tipoQuarto);
        quarto.setPrecoQuarto(precoQuarto);
        if(!file.isEmpty()){
            byte[] fotoBytes = file.getBytes();
            quarto.setPhoto(fotoBytes);
        }
        return quartoRepository.save(quarto);
    }

    public List<String> getAllTiposQuarto(){
        return this.quartoRepository.findAll().stream().map(Quarto::getTipoQuarto).collect(Collectors.toList());

    }

    @Override
    public List<Quarto> getAllQuartos() {
        return quartoRepository.findAll();
    }

    @Override
    @Transactional // Garante que a operação ocorra dentro de uma transação
    public byte[] getFotoQuartoByQuartoId(Long quartoId) {
        Optional<Quarto> quartoOptional = quartoRepository.findById(quartoId);
        if (quartoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Quarto não encontrado!");
        }
        Quarto quarto = quartoOptional.get();

        // Obtém a foto do quarto como byte[]
        byte[] photo = quarto.getPhoto();
        if (photo == null) {
            throw new ResourceNotFoundException("Foto do quarto não encontrada!");
        }

        return photo;
    }

    @Override
    public void deletarQuarto(Long quartoId) {
        Optional<Quarto> quarto = quartoRepository.findById(quartoId);
        if(quarto.isPresent()) {
            quartoRepository.deleteById(quartoId);
        }
    }
}
