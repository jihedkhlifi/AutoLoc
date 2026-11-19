package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Agence;
import tn.esprit.autolocapi.dto.AgenceInputDto;
import tn.esprit.autolocapi.dto.AgenceResponseDto;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.AgenceMapper;
import tn.esprit.autolocapi.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private static final String RESSOURCE = "Agence";

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper mapper;

    @Override
    public AgenceResponseDto create(AgenceInputDto dto) {
        Agence agence = mapper.toEntity(dto);
        valider(agence);
        return mapper.toDto(agenceRepository.save(agence));
    }

    @Override
    public AgenceResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Agence getEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<AgenceResponseDto> findAll() {
        return agenceRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public AgenceResponseDto update(Long id, AgenceInputDto dto) {
        Agence agence = mapper.toEntity(dto);
        valider(agence);
        Agence existante = getEntity(id);
        existante.setNom(agence.getNom());
        existante.setVille(agence.getVille());
        existante.setAdresse(agence.getAdresse());
        existante.setTelephone(agence.getTelephone());
        return mapper.toDto(agenceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        agenceRepository.deleteById(id);
    }

    private void valider(Agence agence) {
        if (estVide(agence.getNom())) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
        if (estVide(agence.getVille())) {
            throw new IllegalArgumentException("La ville de l'agence est obligatoire");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
