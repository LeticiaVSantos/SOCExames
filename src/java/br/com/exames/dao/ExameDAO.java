/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.exames.dao;

import br.com.exames.model.Exame;
import br.com.exames.util.ConexaoBD;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Letícia Santos
 */
public class ExameDAO implements InterfaceDAO<Exame, Integer> {

    private Session session;

    @Override
    public boolean salvar(Exame objeto) {
        session = ConexaoBD.getInstance();
        Transaction transacao = null;

        try {
            transacao = session.beginTransaction();
            session.save(objeto);
            transacao.commit();
            return true;
        }
        catch (Exception e) {
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
    
    @Override
    public List<Exame> findAll(){
        try{
            session = ConexaoBD.getInstance();
            Query q = session.createQuery("SELECT e FROM Exame e ORDER BY nome ASC");

            List<Exame> exames = q.list();
  
            return exames;
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
        finally{
            session.close();
        }
    }

    @Override
    public Exame consultar(Integer chave) {
        session = ConexaoBD.getInstance();

        Exame exa = null;
        try {
            exa = (Exame)session.get(Exame.class, chave); 
        }
        catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        finally{
            session.close();
        }
        return exa;
    }

    @Override
    public boolean alterar(Exame objeto) {
        session = ConexaoBD.getInstance();
        Transaction transacao = null;

        try {
            Exame pac = (Exame)session.get(Exame.class, objeto.getIdExame()); 
            
            pac.setNome(objeto.getNome());
            pac.setValor(objeto.getValor());
            
            if(pac != null){
                transacao = session.beginTransaction();
                session.update(pac);
                transacao.commit();
                return true;
            }
        }
        catch (Exception e) {
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

    @Override
    public boolean excluir(Exame objeto) {
        session = ConexaoBD.getInstance();
        Transaction transacao = null;
      
        try {
            transacao = session.beginTransaction();
            session.delete(objeto);
            transacao.commit();
            return true;
        }
        catch (Exception e) {
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
}
