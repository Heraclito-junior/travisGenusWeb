package controllers;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dao.ControleDAO;
import dominio.Cliente;
import dominio.Contato;
import dominio.Controle;
import dominio.Fornecedor;
import dominio.Funcionario;

@ManagedBean
@SessionScoped
public class ControleMB {
	

	@Inject
    ControleDAO ctrltemp;
    List<String> strings = new ArrayList<String>();
    int idVendaRecente;
    String idFuncionario;
    
    
  
    public void imprime() {
    	
    	System.out.println("qualquer coisa");
    	ctrltemp.outracoisa();
	}
    
    
    
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

    public void createNewTile() {
        strings.add("output");
    }
    public void removeNewTile() {
        strings.remove("output");
    }
    
    
    
    public void mudarUsuario(String login){
    	ctrltemp.atualizarUsuario(login);
    }
    
    public Funcionario recuperarUsuario(){
    	Controle temporario=ctrltemp.recFunc();
    	return ctrltemp.conversor(temporario);
    }



	public Cliente clientePorCPF(long idClientela) {
		// TODO Auto-generated method stub
		ctrltemp.clientePorCPF(idClientela);
		return null;
	}
	

    
}