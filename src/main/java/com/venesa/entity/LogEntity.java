package com.venesa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "log")
@Getter
@Setter
@NoArgsConstructor
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private String username;

	@Column
	private String url;

	@Column
	private String message;
	
	@Column
	private String method;
	
	@Column(name = "remote_addr")
	private String remoteAddr;
	
	@Column(name = "type_err")
	private String typeErr;
	
	@Column
	private String body;

	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

}
