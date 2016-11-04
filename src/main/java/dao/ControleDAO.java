package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Cliente;
import dominio.Contato;
import dominio.Controle;
import dominio.Fornecedor;
import dominio.Funcionario;

@Stateless
public class ControleDAO {
	@PersistenceContext
	private EntityManager em;
	
	public ControleDAO(){
		
	}
	
	public void outracoisa(){
		System.out.println("outra coisa");
	}

	

	public void atualizarUsuario(String login) {
		
		String qs = "select c from Controle c where c.idControle = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", 1);
		
		try {
			q.getSingleResult();
			Controle temp = new Controle(1,login);
			em.merge(temp);
			
		} catch (NoResultException e) {
			Controle temp = new Controle(1,login);
			em.persist(temp);
		}
		
		
		return;
	}
	
	

	
	
	public Controle recFunc() {
		
		String qs = "select c from Controle c where c.idControle = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", 1);
		
		return (Controle) q.getSingleResult();
	}
	

	
	public Funcionario conversor(Controle ctr) {
		
		
		String qs = "select c from Funcionario c where c.id = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", ctr.getIdFuncAtual());
		
		return (Funcionario) q.getSingleResult();
	}
	

	
	public Cliente clientePorCPF(long cpf) {
		
		
		String qs = "select c from Cliente c where c.CPF = :cpf";
		Query q = em.createQuery(qs);
		q.setParameter("cpf", cpf);
		
		return (Cliente) q.getSingleResult();
	}
	
	public Fornecedor FornecedorPorNome(String cpf) {
		
		
		String qs = "select c from Fornecedor c where c.Fname = :cpf";
		Query q = em.createQuery(qs);
		q.setParameter("cpf", cpf);
		
		return (Fornecedor) q.getSingleResult();
	}
	
	

}
