package com.eray.util.framework;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDao implements BatchDao<T>
{
  @PersistenceContext
  protected EntityManager em;
  
  @Transactional
  public void batchInsert(List<T> list){
    for (int i = 0; i < list.size(); i++)
    {
      em.persist(list.get(i));
      if(i%30==0){
        em.flush();
        em.clear();
      }
    }
  }
  
  @Transactional
  public void batchUpdate(List<T> list){
    for (int i = 0; i < list.size(); i++)
    {
      em.merge(list.get(i));
      if(i%30==0){
        em.flush();
        em.clear();
      }
    }
  }
}
