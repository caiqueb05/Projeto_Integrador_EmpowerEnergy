package com.empowerenergy.empowerenergy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe espelho da tabela postagem no banco db_empowerenergy.
 * 
 * @author Lucas
 * @since 1.0
 *
 */

@Entity
@Table(name = "tb_postagem")
public class PostagemModel {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long IdPostagem;
	private @NotBlank String titulo;
	private @NotBlank(message = "O atributo Usuário é Obrigatório!") @Size(min = 5, max = 500) String descricao;
	private @Size(max = 500) String imagem;
	private String localizacao;
	private String mencao;
	private String hashtag;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPostagem = new java.sql.Date(System.currentTimeMillis());

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	@JsonIgnoreProperties("minhasPostagens")
	private UsuarioModel usuario;

	@ManyToOne
	@JoinColumn(name = "id_tema")
	@JsonIgnoreProperties("postagens")
	//@ApiModelProperty(access = "tema.idTema", hidden = true)
	private TemaModel tema;

	public Long getIdPostagem() {
		return IdPostagem;
	}

	public void setIdPostagem(Long idPostagem) {
		IdPostagem = idPostagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(Date dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getMencao() {
		return mencao;
	}

	public void setMencao(String mencao) {
		this.mencao = mencao;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public TemaModel getTema() {
		return tema;
	}

	public void setTema(TemaModel tema) {
		this.tema = tema;
	}
}
