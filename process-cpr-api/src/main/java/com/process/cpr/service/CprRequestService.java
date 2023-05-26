package com.process.cpr.service;

import com.process.cpr.dto.CprRequestDTO;
import com.process.cpr.dto.CprSolicitacaoRequestDTO;
import com.process.cpr.repository.CprRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CprRequestService {

    private CprSolicitacaoRequestDTO cprSolicitacaoRequestDTO;

    private final CprRequestRepository cprRequestRepository;
    private final WebClient webClient;

    @Autowired
    public CprRequestService(CprRequestRepository cprRequestRepository, WebClient webClient) {
        this.cprRequestRepository = cprRequestRepository;
        this.webClient = webClient;
    }

    public Long registerCprRequest(CprSolicitacaoRequestDTO cprRequestDTO) {
        // Converte o DTO em um objeto Model e salva no repositório
        CprRequest cprRequest = convertToModel(cprSolicitacaoRequestDTO);
        Long requestId = cprRequestRepository.save(cprRequest);

        // Faz uma chamada ao serviço externo para registrar a solicitação
        webClient.post()
                .uri("https://servico-externo.com/api/cpr/register")
                .body(Mono.just(cprRequestDTO), CprSolicitacaoRequestDTO.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return requestId;
    }

    public CprRequestDTO getCprRequest(Long id) {
        // Recupera a solicitação do repositório
        CprRequest cprRequest = cprRequestRepository.findById(id);
        if (cprRequest != null) {
            // Converte o Model em um DTO e retorna
            return convertToDTO(cprRequest);
        } else {
            return null;
        }
    }

    public boolean updateCprRequest(Long id, CprRequestDTO updatedCprRequestDTO) {
        // Verifica se a solicitação existe no repositório
        CprRequest cprRequest = cprRequestRepository.findById(id);
        if (cprRequest != null) {

            cprRequest.setName(updatedCprRequestDTO.getName());
            cprRequest.setAddress(updatedCprRequestDTO.getAddress());

            cprRequestRepository.save(cprRequest);

            return true;
        } else {
            return false;
        }
    }

    private CprRequest convertToModel(CprRequestDTO cprRequestDTO) {
        CprRequest cprRequest = new CprRequest();
        cprRequest.setName(cprRequestDTO.getName());
        cprRequest.setAddress(cprRequestDTO.getAddress());


        return cprRequest;
    }

    private CprRequestDTO convertToDTO(CprRequest cprRequest) {
        CprRequestDTO cprRequestDTO = new CprRequestDTO();
        cprRequestDTO.setName(cprRequest.getName());
        cprRequestDTO.setAddress(cprRequest.getAddress());


        return cprRequestDTO;
    }
}

