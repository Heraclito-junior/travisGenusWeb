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
import dao.FaturaContemDAO;
import dao.FaturaDAO;
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Categoria;
import dominio.Contato;
import dominio.Fatura;
import dominio.FaturaReferenteProduto;
import dominio.Venda;
import dominio.VendaContem;

@ManagedBean
@SessionScoped
public class FaturaContemMB {
	
	public FaturaContemMB() {
		super();
		listaFaturaContem = new ArrayList<FaturaReferenteProduto>();
		idFatura=0;
	}


	@Inject
	private FaturaDAO faturaDao;
	
	

	int idFatura;
	
	




	public int getIdFatura() {
		return idFatura;
	}

	public void setIdFatura(int idFatura) {
		this.idFatura = idFatura;
	}


	@Inject
	private FaturaContemDAO faturaContemDao;
	
	private List<FaturaReferenteProduto> listaFaturaContem;
	
	
	


	public List<FaturaReferenteProduto> getListaFaturaContem() {
		return listaFaturaContem;
	}

	public void setListaFaturaContem(List<FaturaReferenteProduto> listaFaturaContem) {
		this.listaFaturaContem = listaFaturaContem;
	}


	
	
	
	
	public void filtrar() {
		
		
		Fatura faturaTemp=faturaContemDao.buscarFaturaID(idFatura);
		
		if(faturaTemp==null){
			FacesMessage msg = new FacesMessage("Fatura não Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			idFatura=0;
		}
		
		
		if(faturaContemDao.buscarFaturasComProdutos(faturaTemp)!=null){
			setListaFaturaContem(faturaContemDao.buscarFaturasComProdutos(faturaTemp));
		}
		
	}
	
	public Fatura retornaPreco() {
		
		Fatura aux=new Fatura();
		aux=faturaContemDao.buscarFaturaID(idFatura);
		if(aux==null){
			return null;
		}
		
		
		
		return aux;
	}
		
	
	public String voltar() {
		idFatura=0;
		listaFaturaContem.clear();
		return "/interna/MenuGerente.jsf";
	}
	
	
	
	public String listar() {
		System.out.println("aqui");
		return "/interna/listaCategoria.jsf";
	}
}
