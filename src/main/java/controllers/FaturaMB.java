package controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import dao.ClienteDAO;
import dao.FaturaContemDAO;
import dao.FaturaDAO;
import dao.ForneceProdutoDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Categoria;
import dominio.Cliente;
import dominio.Fatura;
import dominio.FaturaReferenteProduto;
import dominio.ForneceProduto;
import dominio.Fornecedor;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;
import dominio.VendaContem;

@ManagedBean
@SessionScoped
public class FaturaMB {
	@Inject
	private ControleMB ctrluser;
	
	@Inject
	private ControleFornecedorMB ctrlFornece;
	@Inject
	private VendaDAO vendaDao;	


	@Inject
	private ProdutoDAO produtoDAO;

	@Inject
	private FaturaDAO faturaDao;
	

	@Inject
	private FaturaContemDAO faturaContemDao;
	@Inject
	private ForneceProdutoDAO forneceProdutoDao;
	
	private List<Fatura> listaFatura;
	
	public List<Fatura> getListaFatura() {
		setListaFatura(faturaDao.listar());
		return listaFatura;
	}

	
	public void setListaFatura(List<Fatura> listaFatura) {
		this.listaFatura = listaFatura;
	}
	
	private Fatura nFatura;
	
	
	public Fatura getnFatura() {
		return nFatura;
	}

	public void setnFatura(Fatura nVenda) {
		this.nFatura = nVenda;
	}




	private Funcionario Vendedor;
	private Produto produto;
	

	String nomeCategoria;
	Cliente dud;
	Categoria categoriaAuxiliar;
	List<String> nomeProdutos;
	List<Double> quantidadeProdutos;
	List<Double> PrecoProdutos;
	
	Fornecedor fornecedor;

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
	
	
	
	private List<Produto> listaProd;
	
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	
	public FaturaMB() {
		
		Vendedor=new Funcionario();
		ctrluser=new ControleMB();
		nFatura=new Fatura();
		categoriaAuxiliar= new Categoria();
		listaProd = new ArrayList<Produto>(); 
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
		total=0.0;
		fornecedor=new Fornecedor();
		
	}
		
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void adicionarAoCarrinho() {
		fornecedor=ctrlFornece.recuperarFornecedor();

		if(nomeProdCarrinho.equals("")){
			return;
		}
		if(quntProdCarrinho<=0){
			
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		Double auxiliar2=quntProdCarrinho;
		
		produto=produtoDAO.buscarProdutoNome(nomeProdCarrinho);

		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}
		ForneceProduto fornecimento_temporario=new ForneceProduto();
		
		fornecimento_temporario=forneceProdutoDao.existeComb(fornecedor, produto);
		
		if(fornecimento_temporario==null){
			
			FacesMessage msg = new FacesMessage("Fornecedor não fornece esse produto");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
			
		}
		
		
		
		String tempNome=produto.getNome();
		int swit=-1;
		
		
		if(quntProdCarrinho % 1 != 0 && produto.isFracionavel()==false){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;
		}
		
		
		
		
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
		}
		
		if(swit!=-1){
			Double auxiliar=(Double) quantidadeProdutos.get(swit);
			quntProdCarrinho=quntProdCarrinho+auxiliar;
		}
		
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		quantidadeProdutos.add(quntProdCarrinho);
		PrecoProdutos.add(fornecimento_temporario.getPrecoNoMomento());
		}else{
			quantidadeProdutos.set(swit, quntProdCarrinho);
			PrecoProdutos.set(swit,fornecimento_temporario.getPrecoNoMomento());
			
			
		}
		total=0.0;
		tabelaFatura.clear();
		
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
			
			double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
			double arredondarTotal=truncarValor(ValorTotalAnterior);
			total=total+arredondarTotal;
			addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondarTotal);
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		//total=total+auxiliar2*fornecimento_temporario.getPrecoNoMomento();
		total=truncarValor(total);
		//total=(total*100)/100;
		
		
		
		return; 
	}
	
	public void remover() {
		fornecedor=ctrlFornece.recuperarFornecedor();
		
		
		if(nomeProdCarrinho.equals("")){
			return;
		}
		if(quntProdCarrinho<=0){
			
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		Double auxiliar2=quntProdCarrinho;
		
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
		
		if(quntProdCarrinho % 1 != 0 && produto.isFracionavel()==false){
			FacesMessage msg = new FacesMessage("Produto não Fracionavel");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			quntProdCarrinho=0.0;
			return;
		}
		
		
		ForneceProduto fornecimento_temporario=new ForneceProduto();
		
		fornecimento_temporario=forneceProdutoDao.existeComb(fornecedor, produto);

		
		int swit=-1;
		double auxiliarTansformacao;
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			System.out.println("quantidade ");
			System.out.println(quantidadeProdutos.get(swit));
			
			if(quantidadeProdutos.get(swit)<quntProdCarrinho){
				FacesMessage msg = new FacesMessage("Não há tanto produto no carrinho para remover");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return;				
			}
			auxiliarTansformacao=quantidadeProdutos.get(swit);
			if(quntProdCarrinho==auxiliarTansformacao){
				nomeProdutos.remove(swit);
				quantidadeProdutos.remove(swit);
				PrecoProdutos.remove(swit);	
			}else{
				quantidadeProdutos.set(swit, auxiliarTansformacao-quntProdCarrinho);
			}
			
			
			
			
			total=0.0;
			tabelaFatura.clear();
			for(int j=0;j<nomeProdutos.size();j++){
				
				
				
				double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
				double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
				double arredondar=truncarValor(ValorTotalAnterior);
				
				total=total+arredondar;
				addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondar);
				
				
			}
			nomeProdCarrinho="";
			quntProdCarrinho=0;
			total=truncarValor(total);
			
			
			
			
			return; 
			
		}
		
		
		
		
		return;
	}

	
	public String finalizarAsCompras() {
		//fornecedor=ctrlFornece.recuperarFornecedor();
		
		
		
		
		
		if(total==0.0){
			FacesMessage msg = new FacesMessage("Venda sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		Produto produtoTemp=new Produto();
		//System.out.println(idClientela);
		
		Fornecedor fornecedorTemp= new Fornecedor();
		double auxiliararitmetico;
		
		
		
		//clienteTemp=clienteDAO.buscarClienteCPF(idClientela);
		fornecedorTemp=ctrlFornece.recuperarFornecedor();
		
		
		
		
		Vendedor=ctrluser.recuperarUsuario();
		
		
		nFatura.setIDFunc(Vendedor);
		nFatura.setValorTotal(total);
		nFatura.setFid(fornecedorTemp);
		nFatura.setValorTotal(total);

		
		
		faturaDao.salvar(nFatura);
		
		
		for(int i=0;i<nomeProdutos.size();i++){
			
			//ForneceProduto fornecimento_temporario=new ForneceProduto();
			
			//fornecimento_temporario=forneceProdutoDao.existeComb(fornecedorTemp, produto);
			
			Produto aux=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			
			FaturaReferenteProduto FaturaContemTEMP=new FaturaReferenteProduto();
			
			FaturaContemTEMP.setFaturaReferenteid(nFatura);
			
			//FaturaContemTEMP.setFaturaReferenteid(nFatura.getIDFat());
			FaturaContemTEMP.setProdutofaturaid(aux);
			FaturaContemTEMP.setPrecoNaHora(PrecoProdutos.get(i));
			FaturaContemTEMP.setQuantidadeFatura(quantidadeProdutos.get(i));
			
			
			produtoTemp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			auxiliararitmetico=produtoTemp.getQuantidade()+quantidadeProdutos.get(i);
			produtoTemp.setQuantidade(auxiliararitmetico);
			produtoDAO.atualizar(produtoTemp);

			faturaContemDao.salvar(FaturaContemTEMP);
		}
		
		strings.clear();
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		Vendedor=new Funcionario();
		nFatura=new Fatura();
		categoriaAuxiliar= new Categoria();		
		total=0.0;
		
		
		total=0.0;
		
		return "/interna/sucesso.jsf";
	}
	
	
	public String novo() {
		return "/interna/cadastra.jsf";
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public double truncarQuantidade(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.###");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	}
	
	public double truncarValor(double valorParaTruncar){
		double valorTruncado=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String ajudaParse=df.format(valorParaTruncar);
		ajudaParse=ajudaParse.replace(",", ".");
		valorTruncado=Double.parseDouble(ajudaParse);
		return valorTruncado;
		
	} 
	
	private static final ArrayList<ProdutoModificado> tabelaFatura =
			new ArrayList<ProdutoModificado>();

		public ArrayList<ProdutoModificado> getTabelaFatura() {

			return tabelaFatura;

		}
		
		public String addAction(String nome, double quantidade, double preco, double total) {

			//Produto order = new Produto(quntProdCarrinho,);
			Produto order=produtoDAO.buscarProdutoNome(nomeProdCarrinho);
			if(order==null){
				FacesMessage msg = new FacesMessage("Não existe produto com esse nome");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
								
				return null;
			}
			
			ProdutoModificado auxiliarDataTable=new ProdutoModificado(nome,quantidade,preco,total);
			
			
			

			tabelaFatura.add(auxiliarDataTable);
			return null;
		}
		
		public static class ProdutoModificado{
			public ProdutoModificado() {
				super();
			}

			String nome;
			double Preco;
			double quantidade;
			double total;

			public ProdutoModificado(String nome, double quantidade, double preco, double total) {
				super();
				this.nome = nome;
				Preco = preco;
				this.quantidade = quantidade;
				this.total = total;
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

			public double getQuantidade() {
				return quantidade;
			}

			public void setQuantidade(double quantidade) {
				this.quantidade = quantidade;
			}

			public double getTotal() {
				return total;
			}

			public void setTotal(double total) {
				this.total = total;
			}
			
		}
	
	
	
}