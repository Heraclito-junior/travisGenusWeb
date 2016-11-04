package dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Controle")
public class Controle {
	
	
	public Controle() {
		super();
	}



	public Controle(int idControle, String idFuncAtual) {
		super();
		this.idControle = idControle;
		this.idFuncAtual = idFuncAtual;
	}



	public int getIdControle() {
		return idControle;
	}



	public void setIdControle(int idControle) {
		this.idControle = idControle;
	}



	public String getIdFuncAtual() {
		return idFuncAtual;
	}



	public void setIdFuncAtual(String idFuncAtual) {
		this.idFuncAtual = idFuncAtual;
	}



	@Id
	@Column(nullable=false)
	private int idControle;
	
	
	
	@Column(nullable=false)
	private String  idFuncAtual;
	
	


	
	
	
	
	

	
}
