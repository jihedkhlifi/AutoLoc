package tn.esprit.autolocapi.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import tn.esprit.autolocapi.domain.*;
import tn.esprit.autolocapi.dto.*;
import tn.esprit.autolocapi.repository.*;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import java.util.List;
import java.util.ArrayList;
@Component
@RequiredArgsConstructor
public class PaiementMapper {
    private final IContratRepository contratRepository;

    public PaiementResponseDto toDto(Paiement e) {
        return new PaiementResponseDto(
            e.getIdPaiement(),
            e.getMontant(),
            e.getDatePaiement(),
            e.getModePaiement(),
            e.getContrat() == null ? null : e.getContrat().getIdContrat()
        );
    }
}
