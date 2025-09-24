package br.com.fiap.qhealth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atendimento", schema = "atendimento_finalizado")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(nullable = false, length = 255)
    private String status;

    @Column(name = "fila_id", nullable = false)
    private UUID filaId;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = now();
        this.dataUltimaAlteracao = now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataUltimaAlteracao = now();
    }
}
