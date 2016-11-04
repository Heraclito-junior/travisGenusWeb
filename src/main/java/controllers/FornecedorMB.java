package controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;
import dao.ClienteDAO;
import dao.FornecedorDAO;
import dominio.Categoria;
import dominio.Cliente;
import dominio.Contato;
import dominio.Fornecedor;

@ManagedBean
@SessionScoped
public class FornecedorMB {
	String fid;
	String nome;
	



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	private Fornecedor fornecedor;
	
	

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setCliente(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}



	@Inject
	private FornecedorDAO fornecedorDAO;
	
	private List<Fornecedor> listaFornecedor;
	

	public List<Fornecedor> getListaFornecedor() {
		setListaFornecedor(fornecedorDAO.listar());
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public FornecedorMB() {
		super();
		fid="";
		nome="";
	}	
	
	
	public String cadastrar() {
		
		
		
		Fornecedor c= fornecedorDAO.buscarFornecedorNome(nome);
		
		
		if (c == null) {
			Fornecedor temp=new Fornecedor();
			
			temp.setFname(nome);
			
			fornecedorDAO.salvar(temp);
			fornecedor = new Fornecedor();
			FacesMessage msg = new FacesMessage("Fornecedor Cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nome="";
			return null;
		} else {
			FacesMessage msg = new FacesMessage("Nome Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nome="";
			return null;
		}
		
		
	}
}
