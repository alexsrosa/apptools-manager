package br.com.tools.manager.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Parametro.
 */
@Entity
@Table(name = "parametro")
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "valor", nullable = false)
    private String valor;

    @NotNull
    @Column(name = "datainclusao", nullable = false)
    private LocalDate datainclusao;

    @NotNull
    @Column(name = "dataultimaatualizacao", nullable = false)
    private LocalDate dataultimaatualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Parametro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public Parametro valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public LocalDate getDatainclusao() {
        return datainclusao;
    }

    public Parametro datainclusao(LocalDate datainclusao) {
        this.datainclusao = datainclusao;
        return this;
    }

    public void setDatainclusao(LocalDate datainclusao) {
        this.datainclusao = datainclusao;
    }

    public LocalDate getDataultimaatualizacao() {
        return dataultimaatualizacao;
    }

    public Parametro dataultimaatualizacao(LocalDate dataultimaatualizacao) {
        this.dataultimaatualizacao = dataultimaatualizacao;
        return this;
    }

    public void setDataultimaatualizacao(LocalDate dataultimaatualizacao) {
        this.dataultimaatualizacao = dataultimaatualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parametro parametro = (Parametro) o;
        if (parametro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parametro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parametro{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor='" + getValor() + "'" +
            ", datainclusao='" + getDatainclusao() + "'" +
            ", dataultimaatualizacao='" + getDataultimaatualizacao() + "'" +
            "}";
    }
}
