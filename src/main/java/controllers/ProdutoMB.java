package controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

//import java.util.ArrayList;

//import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dao.CategoriaDAO;
//import dao.ContatoDAO;
import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dominio.Categoria;
import dominio.Contato;
import dominio.Funcionario;
import dominio.Produto;

@ManagedBean
@SessionScoped
public class ProdutoMB {
	public boolean isMudarpreco() {
		return mudarpreco;
	}

	public void setMudarpreco(boolean mudarpreco) {
		this.mudarpreco = mudarpreco;
	}

	private Produto produto;
	String nomeCategoria;
	String produtoNA;
	String produtoN;
	double produtoP;
	
	
	
	
	
	
	
	boolean mudarpreco=false;
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	Categoria categoriaAuxiliar;
	
	
	@Inject
	private ProdutoDAO ProdutoDao;
	@Inject
	private CategoriaDAO categDao;
	
	private List<Produto> listaProd;
	
	
	
	public List<Produto> getListaProd() {
		setListaProd(ProdutoDao.listar());
		return listaProd;
	}

	public void setListaProd(List<Produto> listaProd) {
		this.listaProd = listaProd;
	}



	
	
	
	
	
	
	public ProdutoMB() {
		produto = new Produto();
		categoriaAuxiliar= new Categoria();
		listaProd = new ArrayList<Produto>(); 
		produtoN="";
		produtoNA="";
		produtoP=0.0;
		nomeCategoria="";
		mudarpreco=false;

	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto prod) {
		this.produto = prod;
	}

	public String cadastrar() {
		categoriaAuxiliar=ProdutoDao.retornaCategoria(nomeCategoria);
		if(produto.getPreco()<=0.0){
			FacesMessage msg = new FacesMessage("Valor precisa ser maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			//nomeCategoria="";
			return null;
			
		}
		if(categoriaAuxiliar==null){
			FacesMessage msg = new FacesMessage("categoria nao existe");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeCategoria="";
			return null;
		}
		double verificacaoQuantidadeFracionavel=produto.getQuantidade();
		
		if(verificacaoQuantidadeFracionavel% 1 != 0 &&produto.isFracionavel()==false){
			FacesMessage msg = new FacesMessage("Quantidade fracionada e o Produto está sendo criado como não fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			//nomeCategoria="";
			return null;
		}
		
		
		double trucarvalor=truncarValor(produto.getPreco());
		
		produto.setPreco(trucarvalor);
		
		double trucarQuantidade=truncarQuantidade(produto.getQuantidade());
		
		produto.setQuantidade(trucarQuantidade);
		
		
		produto.setIDcateg(categoriaAuxiliar);
		
		
		
		
		Produto c = ProdutoDao.buscarProdutoNome(produto.getNome());
		
		if (c == null) {
			ProdutoDao.salvar(produto);
		} else {
			FacesMessage msg = new FacesMessage("produto ja existe");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeCategoria="";
			return null;
		}
		produto = new Produto();
		FacesMessage msg = new FacesMessage("produto cadastrado");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage("", msg);
		nomeCategoria="";
		return null;
	}
	public void alterar(){
		Categoria categoria=new Categoria();
		Produto temp=ProdutoDao.buscarProdutoNome(produtoNA);
		if(temp==null){
			FacesMessage msg = new FacesMessage("Produto Nao encontrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeCategoria="";
			return;
		}
		if(produtoP<=0.0&&mudarpreco==true){
			FacesMessage msg = new FacesMessage("Coloque um numero maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeCategoria="";
			return;
		}
		if(!nomeCategoria.equals("")){
			categoria=categDao.buscarCategoriaNome(nomeCategoria);
			if(categoria==null){
				FacesMessage msg = new FacesMessage("Categoria Não Cadastrada");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				nomeCategoria="";
				return;
			}
		}
		
		produtoP=truncarValor(produtoP);		
		
		
		
		
		
		if(!produtoN.equals("")){
			temp.setNome(produtoN);
		}
		
		if(!nomeCategoria.equals("")){
			temp.setIDcateg(categoria);
		}
		if(!produtoN.equals("") && produtoP==0.0 && !nomeCategoria.equals("")){
			FacesMessage msg = new FacesMessage("Nada para Atualizar");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			nomeCategoria="";
			return;
		}
		
		
		
		
		ProdutoDao.atualizar(temp);
		
		FacesMessage msg = new FacesMessage("Produto Atualizado");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage("", msg);
		produtoN="";
		produtoNA="";
		produtoP=0.0;
		nomeCategoria="";

		mudarpreco=false;
		return;
		
		
	}
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public void voltarMenuDeAlterar(){
		produtoN="";
		produtoNA="";
		produtoP=0.0;
		nomeCategoria="";
		mudarpreco=false;

		
	}

	public String getProdutoNA() {
		return produtoNA;
	}

	public void setProdutoNA(String produtoNA) {
		this.produtoNA = produtoNA;
	}

	public String getProdutoN() {
		return produtoN;
	}

	public void setProdutoN(String produtoN) {
		this.produtoN = produtoN;
	}

	public double getProdutoP() {
		return produtoP;
	}

	public void setProdutoP(double produtoP) {
		this.produtoP = produtoP;
	}
	

}