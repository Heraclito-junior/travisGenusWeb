package controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;

//import java.util.ArrayList;

//import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaContemDAO;
import dao.VendaDAO;
import dominio.Categoria;
import dominio.Cliente;
import dominio.Funcionario;
import dominio.Produto;
import dominio.Venda;
import dominio.VendaContem;

@ManagedBean
@SessionScoped
public class VendaMB {
	@Inject
	private ControleMB ctrluser;
	@Inject
	private VendaDAO vendaDao;	
	@Inject
	private VendaContemDAO vendaContemDao;
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	
	
	
	private List<Venda> listaVenda;
	
	public List<Venda> getListaVenda() {
		setListaVenda(vendaDao.listar());
		return listaVenda;
	}

	
	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
	private Venda nVenda;
	public Venda getnVenda() {
		return nVenda;
	}

	public void setnVenda(Venda nVenda) {
		this.nVenda = nVenda;
	}




	private Funcionario Vendedor;
	private Produto produto;
	long idClientela;
	
	public long getIdClientela() {
		return idClientela;
	}

	public void setIdClientela(long idClientela) {
		this.idClientela = idClientela;
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
	
	
	
	private List<Produto> listaProd;
	
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	
	public VendaMB() {
		//produto = new Produto();
		Vendedor=new Funcionario();
		ctrluser=new ControleMB();
		nVenda=new Venda();
		categoriaAuxiliar= new Categoria();
		listaProd = new ArrayList<Produto>(); 
		nomeProdutos=new ArrayList<String>();
		quantidadeProdutos=new ArrayList<Double>();
		PrecoProdutos=new ArrayList<Double>();
		total=0.0;
		idClientela=1;
		
	}
		
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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
	
	
	
	

	public void adicionarAoCarrinho() {
		
		if(nomeProdCarrinho.equals("")){
			return;
		}
		if(quntProdCarrinho<=0){
			
			FacesMessage msg = new FacesMessage("Coloque uma quantidade de produtos maior que 0");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		double ValorTruncado=truncarQuantidade(quntProdCarrinho);
		quntProdCarrinho=ValorTruncado;
		
		
		Double auxiliar2=quntProdCarrinho;
		
		produto=vendaDao.buscarProdutoNome(nomeProdCarrinho);
		
		
		if(produto==null){
			
			FacesMessage msg = new FacesMessage("Produto não cadastrado");
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
		
		
		
				
		if(produto.getQuantidade()<quntProdCarrinho){
			FacesMessage msg = new FacesMessage("Quantidade insuficiente no estoque, o maximo é "+ produto.getQuantidade());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return;
		}
		
		
		
		if(swit==-1){
		nomeProdutos.add(nomeProdCarrinho);
		quantidadeProdutos.add(quntProdCarrinho);
		PrecoProdutos.add(produto.getPreco());
		}else{
			quantidadeProdutos.set(swit, quntProdCarrinho);
		}
		
		
		tabelaVenda.clear();
		total=0.0;
		
		
		for(int j=0;j<nomeProdutos.size();j++){
			
			double ValorTotalAnterior=(quantidadeProdutos.get(j)*PrecoProdutos.get(j));
			
			double arredondarQuantidade=truncarQuantidade(quantidadeProdutos.get(j));
			double arredondarTotal=truncarValor(ValorTotalAnterior);
			total=total+arredondarTotal;
			addAction(nomeProdutos.get(j),arredondarQuantidade,PrecoProdutos.get(j),arredondarTotal);
			
			
			
			
			
			
			
		}
		
		//addAction();
		//total=truncar(total);
		
		
		
		nomeProdCarrinho="";
		quntProdCarrinho=0;
		//total=total+valorTotal;
		
		return; 
	}
	
	public void remover() {
		if(nomeProdCarrinho.equals("")){
			return;
		}
		
		
		
		quntProdCarrinho=truncarQuantidade(quntProdCarrinho);
		
		
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
		
		
		if(!nomeProdutos.contains(tempNome)){
		
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
		
		
		
		
		
		
		
		
		
		
		
		
		int swit=-1;
		
		
		double auxiliarTansformacao;
		if(nomeProdutos.contains(tempNome)){
			swit=nomeProdutos.indexOf(tempNome);
			
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
			
			
						
			strings.clear();
			tabelaVenda.clear();
			total=0.0;
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
		if(total==0.0){
			FacesMessage msg = new FacesMessage("Venda sem produtos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", msg);
			return null;
		}
		Produto produtoTemp=new Produto();
		//System.out.println(idClientela);
		Cliente clienteTemp=new Cliente();
		double auxiliararitmetico;
		if(clienteDAO.buscarClienteCPF(idClientela)==null){
			
			if(idClientela==1){
				long temp=1;
				dud=new Cliente(temp,"Nao especificado");
				clienteDAO.salvar(dud);
				nVenda.setIDcliente(dud);
			}else{
				FacesMessage msg = new FacesMessage("Cliente não existe");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("", msg);
				return null;
			}
		}
		
		
		clienteTemp=clienteDAO.buscarClienteCPF(idClientela);
		
		
		
		Vendedor=ctrluser.recuperarUsuario();
		
		
		nVenda.setIDvendedor(Vendedor);
		nVenda.setValorTotal(total);
		nVenda.setIDcliente(clienteTemp);
		System.out.println("macarena");
		System.out.println(clienteTemp.getCPF());


		
		
		vendaDao.salvar(nVenda);
		
		for(int i=0;i<nomeProdutos.size();i++){
			VendaContem vendaContemTEMP=new VendaContem();
			Produto temp2=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			vendaContemTEMP.setVenda_id(nVenda);
			vendaContemTEMP.setQuantidadeVenda(quantidadeProdutos.get(i));
			vendaContemTEMP.setNomeProduto(temp2);
			vendaContemTEMP.setPrecoNoMomento(PrecoProdutos.get(i));
			produtoTemp=produtoDAO.buscarProdutoNome(nomeProdutos.get(i));
			auxiliararitmetico=produtoTemp.getQuantidade()-quantidadeProdutos.get(i);
			produtoTemp.setQuantidade(auxiliararitmetico);
			produtoDAO.atualizar(produtoTemp);

			vendaContemDao.salvar(vendaContemTEMP);
		}
		
		strings.clear();
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		Vendedor=new Funcionario();
		//ctrluser=new ControleMB();
		nVenda=new Venda();
		categoriaAuxiliar= new Categoria();		
		total=0.0;
		idClientela=1;
		
		
		total=0.0;
		
		return "/interna/sucesso.jsf";
	}
	
	
	public String voltar() {
		nomeProdutos.clear();
		quantidadeProdutos.clear();
		PrecoProdutos.clear();
		tabelaVenda.clear();
		strings.clear();
		total=0.0;
		
		return "/interna/MenuVendedor.jsf";
		
		
	}

	private static final ArrayList<ProdutoModificado> tabelaVenda =
			new ArrayList<ProdutoModificado>();

		public ArrayList<ProdutoModificado> getTabelaVenda() {

			return tabelaVenda;

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
			
			
			

			tabelaVenda.add(auxiliarDataTable);
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
		
		public String voltarAVenda(){
			return "/interna/cadastrarVenda.jsf";
		}
		public String listaProdutos(){
			return "/interna/listaProdutosVenda.jsf";
		}
	
	
	
}