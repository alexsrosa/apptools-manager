package br.com.tools.manager.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "time_sheet")
public class TimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricula", nullable = false)
    private Integer matricula;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "tarefa", nullable = false)
    private Integer tarefa;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "codigofase", nullable = false)
    private Integer codigofase;

    @NotNull
    @Column(name = "descricaofase", nullable = false)
    private String descricaofase;

    @NotNull
    @Column(name = "codigoatividade", nullable = false)
    private Integer codigoatividade;

    @NotNull
    @Column(name = "descricaoatividade", nullable = false)
    private String descricaoatividade;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "observacao")
    private String observacao;

    @NotNull
    @Column(name = "horas", nullable = false)
    private String horas;

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

    public Integer getMatricula() {
        return matricula;
    }

    public TimeSheet matricula(Integer matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public TimeSheet nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTarefa() {
        return tarefa;
    }

    public TimeSheet tarefa(Integer tarefa) {
        this.tarefa = tarefa;
        return this;
    }

    public void setTarefa(Integer tarefa) {
        this.tarefa = tarefa;
    }

    public String getDescricao() {
        return descricao;
    }

    public TimeSheet descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCodigofase() {
        return codigofase;
    }

    public TimeSheet codigofase(Integer codigofase) {
        this.codigofase = codigofase;
        return this;
    }

    public void setCodigofase(Integer codigofase) {
        this.codigofase = codigofase;
    }

    public String getDescricaofase() {
        return descricaofase;
    }

    public TimeSheet descricaofase(String descricaofase) {
        this.descricaofase = descricaofase;
        return this;
    }

    public void setDescricaofase(String descricaofase) {
        this.descricaofase = descricaofase;
    }

    public Integer getCodigoatividade() {
        return codigoatividade;
    }

    public TimeSheet codigoatividade(Integer codigoatividade) {
        this.codigoatividade = codigoatividade;
        return this;
    }

    public void setCodigoatividade(Integer codigoatividade) {
        this.codigoatividade = codigoatividade;
    }

    public String getDescricaoatividade() {
        return descricaoatividade;
    }

    public TimeSheet descricaoatividade(String descricaoatividade) {
        this.descricaoatividade = descricaoatividade;
        return this;
    }

    public void setDescricaoatividade(String descricaoatividade) {
        this.descricaoatividade = descricaoatividade;
    }

    public LocalDate getData() {
        return data;
    }

    public TimeSheet data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public TimeSheet observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getHoras() {
        return horas;
    }

    public TimeSheet horas(String horas) {
        this.horas = horas;
        return this;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public LocalDate getDatainclusao() {
        return datainclusao;
    }

    public TimeSheet datainclusao(LocalDate datainclusao) {
        this.datainclusao = datainclusao;
        return this;
    }

    public void setDatainclusao(LocalDate datainclusao) {
        this.datainclusao = datainclusao;
    }

    public LocalDate getDataultimaatualizacao() {
        return dataultimaatualizacao;
    }

    public TimeSheet dataultimaatualizacao(LocalDate dataultimaatualizacao) {
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
        TimeSheet timeSheet = (TimeSheet) o;
        if (timeSheet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeSheet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeSheet{" +
            "id=" + getId() +
            ", matricula='" + getMatricula() + "'" +
            ", nome='" + getNome() + "'" +
            ", tarefa='" + getTarefa() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", codigofase='" + getCodigofase() + "'" +
            ", descricaofase='" + getDescricaofase() + "'" +
            ", codigoatividade='" + getCodigoatividade() + "'" +
            ", descricaoatividade='" + getDescricaoatividade() + "'" +
            ", data='" + getData() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", horas='" + getHoras() + "'" +
            ", datainclusao='" + getDatainclusao() + "'" +
            ", dataultimaatualizacao='" + getDataultimaatualizacao() + "'" +
            "}";
    }
}
