package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Client;
import tn.esprit.autolocapi.dto.ClientInputDto;
import tn.esprit.autolocapi.dto.ClientResponseDto;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.ClientMapper;
import tn.esprit.autolocapi.repository.IClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private static final String RESSOURCE = "Client";

    private final IClientRepository clientRepository;
    private final ClientMapper mapper;

    @Override
    public ClientResponseDto create(ClientInputDto dto) {
        Client client = mapper.toEntity(dto);
        valider(client);
        return mapper.toDto(clientRepository.save(client));
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Client getEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ClientResponseDto update(Long id, ClientInputDto dto) {
        Client client = mapper.toEntity(dto);
        valider(client);
        Client existant = getEntity(id);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());
        return mapper.toDto(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        clientRepository.deleteById(id);
    }

    private void valider(Client client) {
        if (estVide(client.getNom()) || estVide(client.getEmail())) {
            throw new IllegalArgumentException("Le nom et l'e-mail du client sont obligatoires");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
