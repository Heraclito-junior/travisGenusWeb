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

import dominio.Categoria;
import dominio.Contato;

@ManagedBean
@SessionScoped
public class CategoriaMB {
	private Categoria categoria;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	private List<Categoria> listaCategoria;
	
	
	


	public CategoriaMB() {
		categoria = new Categoria();
		listaCategoria = new ArrayList<Categoria>(); 
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

	public List<Categoria> getlistaCategoria() {
		setListaCategoria(categoriaDAO.listar());
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCatego) {
		this.listaCategoria = listaCatego;
	}
		
	
	
	public String cadastrar() {
		Categoria c = categoriaDAO.buscarCategoriaNome(categoria.getNome());
		if (c == null) {
			categoriaDAO.salvar(categoria);
			categoria = new Categoria();
			FacesMessage msg = new FacesMessage("Categoria Cadastrada");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			
			return null;
		} else {
			FacesMessage msg = new FacesMessage("Categoria Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		
		//return "/interna/listaCategoria.jsf";
	}
	
	public String voltar() {
		return "/interna/cadastraCategoria.jsf";
	}
	
	
	
	public String listar() {
		System.out.println("aqui");
		return "/interna/listaCategoria.jsf";
	}
}
