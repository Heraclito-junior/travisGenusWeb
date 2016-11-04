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
import dao.FornecedorDAO;
import dominio.Categoria;
import dominio.Contato;
import dominio.Fornecedor;

@ManagedBean
@SessionScoped
public class comecarMB {
	private Categoria categoria;
	
	@Inject
	private ControleFornecedorMB controleFornecedorMB;
	
	@Inject
	private FornecedorDAO fornecedorDao;
	
	private List<Categoria> listaCategoria;
	String fornecedor;
	
	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	
	


	public comecarMB() {
		
		fornecedor="";
	}
	
	
	
	
	public String mudarFornecedor() {
		
		Fornecedor c = fornecedorDao.buscarFornecedorNome(fornecedor);
				
		if (c == null) {
			FacesMessage msg = new FacesMessage("Fornecedor Não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
			return null;
		}
		controleFornecedorMB.mudarFornecedor(fornecedor);
		return "/interna/CadastrarFatura.jsf";
	}
	


	
}
