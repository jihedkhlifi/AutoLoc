package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Employe;
import tn.esprit.autolocapi.dto.EmployeInputDto;
import tn.esprit.autolocapi.dto.EmployeResponseDto;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.EmployeMapper;
import tn.esprit.autolocapi.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private static final String RESSOURCE = "Employe";

    private final IEmployeRepository employeRepository;
    private final EmployeMapper mapper;

    @Override
    public EmployeResponseDto create(EmployeInputDto dto) {
        Employe employe = mapper.toEntity(dto);
        valider(employe);
        return mapper.toDto(employeRepository.save(employe));
    }

    @Override
    public EmployeResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Employe getEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<EmployeResponseDto> findAll() {
        return employeRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public EmployeResponseDto update(Long id, EmployeInputDto dto) {
        Employe employe = mapper.toEntity(dto);
        valider(employe);
        Employe existant = getEntity(id);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        if (employe.getAgence() != null) existant.setAgence(employe.getAgence());
        return mapper.toDto(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        employeRepository.deleteById(id);
    }

    private void valider(Employe employe) {
        if (estVide(employe.getNom()) || estVide(employe.getPrenom())) {
            throw new IllegalArgumentException("Le nom et le prénom de l'employé sont obligatoires");
        }
        if (employe.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
