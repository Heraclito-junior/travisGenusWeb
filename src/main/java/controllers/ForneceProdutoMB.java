package controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dao.ForneceProdutoDAO;

import dao.VendaContemDAO;
import dao.VendaDAO;

import dominio.ForneceProduto;
import dominio.Fornecedor;


@ManagedBean
@SessionScoped
public class ForneceProdutoMB {
	
	public ForneceProdutoMB() {
		super();
		listaForneceProduto = new ArrayList<ForneceProduto>();
		idFornecimento="";
	}
	
	String idFornecimento;
	
	




	public String getIdFornecimento() {
		return idFornecimento;
	}

	public void setIdFornecimento(String idFornecimento) {
		this.idFornecimento = idFornecimento;
	}



	@Inject
	private ForneceProdutoDAO forneceContemDao;
	
	private List<ForneceProduto> listaForneceProduto;
	
	
	


	public List<ForneceProduto> getListaVendaContem() {
		return listaForneceProduto;
	}

	public void setListaVendaContem(List<ForneceProduto> listaVendaContem) {
		this.listaForneceProduto = listaVendaContem;
	}

	
	public void filtrar() {
		
		Fornecedor fornecedorTemporario=forneceContemDao.buscarFornecedorpeloNome(idFornecimento);
		
		
		if(fornecedorTemporario==null){
			FacesMessage msg = new FacesMessage("Fornecedor não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}
		
		
		if(forneceContemDao.buscarFornecimentoComFornecedor(fornecedorTemporario)!=null){
			setListaVendaContem(forneceContemDao.buscarFornecimentoComFornecedor(fornecedorTemporario));
		}
		if(listaForneceProduto.size()==0){
			FacesMessage msg = new FacesMessage("Fornecedor não Fornece Nenhum produto");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
		}
		
	}
	

		
	
	public String voltar() {
		idFornecimento="";
		listaForneceProduto.clear();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
	public String listar() {
		System.out.println("aqui");
		return "/interna/listaCategoria.jsf";
	}
}
