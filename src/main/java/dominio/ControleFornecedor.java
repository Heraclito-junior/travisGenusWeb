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
@Table(name="ControleFornecedor")
public class ControleFornecedor {
	
	
	public ControleFornecedor(int idControle, String nomeFornecedor) {
		super();
		this.idControle = idControle;
		this.nomeFornecedor = nomeFornecedor;
	}







	public ControleFornecedor() {
		super();
	}







	public int getIdControle() {
		return idControle;
	}



	public void setIdControle(int idControle) {
		this.idControle = idControle;
	}




	@Id
	@Column(nullable=false)
	private int idControle;
	
	
	

	
	@Column(nullable=false)
	private String nomeFornecedor;

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}



	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	
	
	
	
	

	
}
