package com.florido.expense_tracker_api.models;

import com.florido.expense_tracker_api.enums.CategoriaTransacao;
import com.florido.expense_tracker_api.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_transacao")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Transacao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "valor")
    private long valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTransacao Tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaTransacao Categoria;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "Data_transacao")
    private LocalDate data;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", Tipo=" + Tipo +
                ", Categoria=" + Categoria +
                ", descricao='" + descricao + '\'' +
                ", data=" + data +
                '}';
    }
}
