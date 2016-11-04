package controllers;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dao.ControleDAO;
import dao.ControleFornecedorDAO;
import dominio.Cliente;
import dominio.Contato;
import dominio.Controle;
import dominio.ControleFornecedor;
import dominio.Fornecedor;
import dominio.Funcionario;

@ManagedBean
@SessionScoped
public class ControleFornecedorMB {
	

	@Inject
    ControleFornecedorDAO ctrltemp;
    List<String> strings = new ArrayList<String>();
    int idVendaRecente;
    String idFuncionario;
    
    
  
 
    
    
    
    public int getIdVendaRecente() {
		return idVendaRecente;
	}

	public void setIdVendaRecente(int idVendaRecente) {
		this.idVendaRecente = idVendaRecente;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
    public List<String> getStrings() {
        return strings;
    }
   
	
	public Fornecedor fornecedorPorNome(String idClientela) {
		// TODO Auto-generated method stub
		ctrltemp.FornecedorPorNome(idClientela);
		return null;
	}
	  public void mudarFornecedor(String login){
	    	ctrltemp.atualizarFornecedor(login);
	    }
	    
	    public Fornecedor recuperarFornecedor(){
	    	ControleFornecedor temporario=ctrltemp.recFornecedor();
	    	return ctrltemp.conversor2(temporario);
	    }
    
}