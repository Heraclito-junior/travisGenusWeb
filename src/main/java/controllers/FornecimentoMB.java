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

import controllers.VendaMB.ProdutoModificado;
import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Categoria;
import dominio.Cliente;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;

@ManagedBean
@SessionScoped
public class FornecimentoMB {

	@Inject
	private VendaDAO vendaDao;	
	@Inject
	private FornecedorDAO fornecedorDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	@Inject
	private ForneceProdutoDAO forneceProdutoDAO;
	
	
	
	private List<Venda> listaVenda;
	
	public List<Venda> getListaVenda() {
		setListaVenda(vendaDao.listar());
		return listaVenda;
	}

	
	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
	private ForneceProduto nFornece;
	




	public ForneceProduto getnFornece() {
		return nFornece;
	}


	public void setnFornece(ForneceProduto nFornece) {
		this.nFornece = nFornece;
	}

	
	private Produto produto;
	String nomeFornecedor;
	
	

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}


	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	String nomeCategoria;
	Cliente dud;
	Categoria categoriaAuxiliar;
	List<String> nomeProdutos;
	List<Double> quantidadeProdutos;
	List<Double> PrecoProdutos;

	Double total;
	String nomeProdCarrinho;
	double quntProdCarrinho;
	
	List<String> strings = new ArrayList<String>();
	
	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}




	public double getQuntProdCarrinho() {
		return quntProdCarrinho;
	}

	public void setQuntProdCarrinho(double quntProdCarrinho) {
		this.quntProdCarrinho = quntProdCarrinho;
	}




	
	public String getNomeProdCarrinho() {
		return nomeProdCarrinho;
	}

	public void setNomeProdCarrinho(String nomeProdCarrinho) {
		this.nomeProdCarrinho = nomeProdCarrinho;
	}


	
	
	double valorTotal;
	
	
	
	
	
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	
	
	
	public FornecimentoMB() {
		nFornece=new ForneceProduto();
		categoriaAuxiliar= new Categoria();
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
		total=0.0;
		
	}
		
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void adicionarAoCarrinho() {
		
		
		if(nomeProdCarrinho.equals("")){
			return;
		}
		if(quntProdCarrinho<=0){
			
			FacesMessage msg = new FacesMessage("Coloque um valor maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		
		
		
		produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}
		
		String tempNome=nomeProdCarrinho;
		int swit=-1;
		
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
		}
		
		
		
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		
		PrecoProdutos.add(quntProdCarrinho);
		}else{
			
			PrecoProdutos.set(swit, quntProdCarrinho);
			
			
			
			
		}
		
		tabelaFatura.clear();
		total=0.0;
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			System.out.println(PrecoProdutos.get(j));
			double arredondarPreco=truncarValor(PrecoProdutos.get(j));
			
			
			
			addAction(nomeProdutos.get(j),arredondarPreco);
			
			
			
			
			
			
			
		}
		
		

		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		return; 
	}
	
	public void remover() {
		if(nomeProdCarrinho.equals("")){
			return;
		}
		
		
		produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}
		
		String tempNome=produto.getNome();
		
		
		if(nomeProdutos.contains(tempNome)){
		}else{
			FacesMessage msg = new FacesMessage("Produto não está no carrinho");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;				
		}
		
		
		
		
		
		
		
		
		int swit=-1;
	
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
				nomeProdutos.remove(swit);
				PrecoProdutos.remove(swit);	
			}
			
			
			
			
			strings.clear();
			tabelaFatura.clear();
			
			for(int j=0;j<nomeProdutos.size();j++){
				
				double arredondarPreco=truncarValor(PrecoProdutos.get(j));
				addAction(nomeProdutos.get(j),arredondarPreco);
				
				
				
			}
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			
			
			
			
			
			
			return; 
			
		}
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String finalizarFornecimento() {
		if(nomeProdutos.size()==0){
			FacesMessage msg = new FacesMessage("Fornecimento sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		
		
		
		Fornecedor fornecedorTemp=new Fornecedor();
		fornecedorTemp=fornecedorDAO.buscarFornecedorNome(nomeFornecedor);
		
		if(fornecedorDAO.buscarFornecedorNome(nomeFornecedor)==null){
			
			if(nomeFornecedor==""){
				FacesMessage msg = new FacesMessage("Digite um nome para o Fornecedor");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return null;
			}else{
				FacesMessage msg = new FacesMessage("Fornecedor não existe");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return null;
			}
		}
		
		
		fornecedorTemp=fornecedorDAO.buscarFornecedorNome(nomeFornecedor);
		
		for(int i=0;i<nomeProdutos.size();i++){
			
			nFornece.setFid(fornecedorTemp);
			Produto temp=new Produto();
			temp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			nFornece.setProdutoFornecimento(temp);
			double arredondar=truncarValor(PrecoProdutos.get(i));
			nFornece.setPrecoNoMomento(arredondar);
			
			ForneceProduto u=forneceProdutoDAO.existeComb(fornecedorTemp, temp);
			
			if(u==null){
				forneceProdutoDAO.salvar(nFornece);
				
			}else{
				
				forneceProdutoDAO.remover(u);
				forneceProdutoDAO.salvar(nFornece);
			}
			nFornece=new ForneceProduto();
			
			
		
		}
		
		strings.clear();
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		nFornece=new ForneceProduto();

		nomeFornecedor="";
				
		return "/interna/sucesso.jsf";
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
	
	
	public String novo() {
		return "/interna/cadastra.jsf";
	}
	
	public String addAction(String nome, double preco) {

		
		Produto order=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
		if(order==null){
			FacesMessage msg = new FacesMessage("Não existe produto com esse nome");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
							
			return null;
		}
		
		ParNomePreco auxiliarDataTable=new ParNomePreco(nome,preco);
		
		
		

		tabelaFatura.add(auxiliarDataTable);
		return null;
	}
	
	private static final ArrayList<ParNomePreco> tabelaFatura =
			new ArrayList<ParNomePreco>();

		public ArrayList<ParNomePreco> getTabelaFatura() {

			return tabelaFatura;

		}
	
	public static class ParNomePreco{
		public ParNomePreco() {
			super();
		}

		String nome;
		double Preco;
		

		public ParNomePreco(String nome, double preco) {
			super();
			this.nome = nome;
			Preco = preco;
			
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public double getPreco() {
			return Preco;
		}

		public void setPreco(double preco) {
			Preco = preco;
		}


		
	}
	
	
	
}