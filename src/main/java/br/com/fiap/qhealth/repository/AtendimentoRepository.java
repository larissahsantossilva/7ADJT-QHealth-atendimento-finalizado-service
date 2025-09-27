package br.com.fiap.qhealth.repository;

import br.com.fiap.qhealth.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {
    List<Atendimento> findByFilaIdOrderByDataCriacaoAsc(UUID filaId);
}
