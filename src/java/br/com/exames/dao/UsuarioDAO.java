/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exames.dao;

import br.com.exames.model.Usuario;
import br.com.exames.util.ConexaoBD;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Letícia Santos
 */
public class UsuarioDAO implements InterfaceDAO<Usuario, String> {

    private Session session;

    public boolean verificaLogin(Usuario objeto) {
   
        session = ConexaoBD.getInstance();
        boolean exists = exists(objeto);

        return exists;
    }

    @Override
    public boolean salvar(Usuario objeto) {
        session = ConexaoBD.getInstance();
        Transaction transacao = null; //permite transacao com o BD 

        try {
            transacao = session.beginTransaction();
            session.save(objeto);
            transacao.commit();//faz a transacao
            return true;
        }
        catch (HibernateException e) {
            //cancela a transcao em caso de falha
            System.out.println("Erro: " + e.getMessage());
            if(transacao.isActive()){
                transacao.rollback();
            }
        }
        finally{
            session.close();
        }
        return false;
    }
    
    public boolean exists(Usuario objeto) {
        session = ConexaoBD.getInstance();
        Query query = session.createQuery("SELECT u FROM Usuario u WHERE u.nome=:nome AND senha=:senha");
        query.setParameter("nome", objeto.getNome());
        query.setParameter("senha", objeto.getSenha());
        try{
            Usuario usuario = (Usuario)query.uniqueResult();
            if(usuario == null){
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        finally{
            session.close();
        }
        return true;
    }

    @Override
    public Usuario consultar(String objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean alterar(Usuario objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean excluir(Usuario objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Usuario> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void cath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
