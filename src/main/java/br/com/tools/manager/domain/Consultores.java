package br.com.tools.manager.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Consultores.
 */
@Entity
@Table(name = "consultores")
public class Consultores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Consultores nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataprimeiroregistro() {
        return dataprimeiroregistro;
    }

    public Consultores dataprimeiroregistro(LocalDate dataprimeiroregistro) {
        this.dataprimeiroregistro = dataprimeiroregistro;
        return this;
    }

    public void setDataprimeiroregistro(LocalDate dataprimeiroregistro) {
        this.dataprimeiroregistro = dataprimeiroregistro;
    }

    public LocalDate getDataultimoregistro() {
        return dataultimoregistro;
    }

    public Consultores dataultimoregistro(LocalDate dataultimoregistro) {
        this.dataultimoregistro = dataultimoregistro;
        return this;
    }

    public void setDataultimoregistro(LocalDate dataultimoregistro) {
        this.dataultimoregistro = dataultimoregistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consultores consultores = (Consultores) o;
        if (consultores.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultores.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultores{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataprimeiroregistro='" + getDataprimeiroregistro() + "'" +
            ", dataultimoregistro='" + getDataultimoregistro() + "'" +
            "}";
    }
}
