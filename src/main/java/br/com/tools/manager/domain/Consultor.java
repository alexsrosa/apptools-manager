package br.com.tools.manager.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.tools.manager.domain.enumeration.ConsultorStatus;

/**
 * A Consultor.
 */
@Entity
@Table(name = "consultor")
public class Consultor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "dataprimeiroregistro", nullable = false)
    private LocalDate dataprimeiroregistro;

    @NotNull
    @Column(name = "dataultimoregistro", nullable = false)
    private LocalDate dataultimoregistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flativo", nullable = false)
    private ConsultorStatus flativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Consultor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataprimeiroregistro() {
        return dataprimeiroregistro;
    }

    public Consultor dataprimeiroregistro(LocalDate dataprimeiroregistro) {
        this.dataprimeiroregistro = dataprimeiroregistro;
        return this;
    }

    public void setDataprimeiroregistro(LocalDate dataprimeiroregistro) {
        this.dataprimeiroregistro = dataprimeiroregistro;
    }

    public LocalDate getDataultimoregistro() {
        return dataultimoregistro;
    }

    public Consultor dataultimoregistro(LocalDate dataultimoregistro) {
        this.dataultimoregistro = dataultimoregistro;
        return this;
    }

    public void setDataultimoregistro(LocalDate dataultimoregistro) {
        this.dataultimoregistro = dataultimoregistro;
    }

    public ConsultorStatus getFlativo() {
        return flativo;
    }

    public Consultor flativo(ConsultorStatus flativo) {
        this.flativo = flativo;
        return this;
    }

    public void setFlativo(ConsultorStatus flativo) {
        this.flativo = flativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consultor consultor = (Consultor) o;
        if (consultor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataprimeiroregistro='" + getDataprimeiroregistro() + "'" +
            ", dataultimoregistro='" + getDataultimoregistro() + "'" +
            ", flativo='" + getFlativo() + "'" +
            "}";
    }
}
