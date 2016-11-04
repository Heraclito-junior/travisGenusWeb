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
import dominio.ControleFornecedor;
import dominio.Fornecedor;
import dominio.Funcionario;

@Stateless
public class ControleFornecedorDAO {
	@PersistenceContext
	private EntityManager em;
	
	public ControleFornecedorDAO(){
		
	}
		
	
	
	public void atualizarFornecedor(String login) {
		
		String qs = "select c from ControleFornecedor c where c.idControle = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", 1);
		
		try {
			q.getSingleResult();
			ControleFornecedor temp = new ControleFornecedor(1,login);
			em.merge(temp);
			
		} catch (NoResultException e) {
			ControleFornecedor temp = new ControleFornecedor(1,login);
			em.persist(temp);
		}
		
		
		return;
	}
	
	

	
	public ControleFornecedor recFornecedor() {
		
		String qs = "select c from ControleFornecedor c where c.idControle = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", 1);
		
		return (ControleFornecedor) q.getSingleResult();
	}
	

	
	public Fornecedor conversor2(ControleFornecedor ctr) {
		
		
		String qs = "select c from Fornecedor c where c.Fname = :id";
		Query q = em.createQuery(qs);
		q.setParameter("id", ctr.getNomeFornecedor());
		
		return (Fornecedor) q.getSingleResult();
	}
	

	
	public Fornecedor FornecedorPorNome(String cpf) {
		
		
		String qs = "select c from Fornecedor c where c.Fname = :cpf";
		Query q = em.createQuery(qs);
		q.setParameter("cpf", cpf);
		
		return (Fornecedor) q.getSingleResult();
	}
	
	

}
