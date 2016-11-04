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
import dominio.Categoria;
import dominio.Cliente;
import dominio.Contato;

@ManagedBean
@SessionScoped
public class ClienteMB {
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	private Cliente cliente;
	
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	@Inject
	private ClienteDAO clienteDAO;
	
	private List<Cliente> listaCliente;
	
	String cpf;
	String nome;
	
	public ClienteMB() {
		super();
		cpf="";
		nome="";
	}

	
	public List<Cliente> getlistaCategoria() {
		setListaCliente(clienteDAO.listar());
		return listaCliente;
	}
	

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
		
	
	
	public String cadastrar() {
		
		long aux=Long.parseLong(cpf);
		
		Cliente c = clienteDAO.buscarClienteCPF(aux);
		if (c == null) {
			Cliente temp=new Cliente();
			temp.setCPF(aux);
			temp.setNome(nome);
			System.out.println("aux : "+aux);
			System.out.println("    nome : "+nome);
			clienteDAO.salvar(temp);
			cliente = new Cliente();
			FacesMessage msg = new FacesMessage("Cliente Cadastrado");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			cpf="";
			return null;
		} else {
			FacesMessage msg = new FacesMessage("CPF Ja Existe");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("", msg);
			cpf="";
			nome="";
			return null;
		}
		
		
	}
	
	public String voltar() {
		return "/interna/MenuVendedor.jsf";
	}
	
	
	
	public String listar() {
		return "/interna/listaCategoria.jsf";
	}
}
