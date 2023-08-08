package org.berka.utility;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyFactoryRepository<T,ID> implements  ICrud<T, ID>{

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Session session;
    private Transaction transaction;

    private T t;

    public MyFactoryRepository(T t) {
        this.entityManager =HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.t = t;
    }
    public void openSession() {
        session=HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }
    public void closeSession() {
        transaction.commit();
        session.close();
    }
    @Override
    public <S extends T> S save(S entity) { //repositoryde T tipini veriyorsun ve o tipin disina cikmiyor artik o sayede parametre alirken musteri'den extend edilmemisler
                                            //alinmiyor ve daha guvenli oluyor.

        try {
            openSession();
            session.save(entity);
            closeSession();
            return entity;
        } catch (Exception e) {
            transaction.rollback();//hata olustugu an yapilan islemleri geri al.
            throw e;
        }

    }
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {

        try {
            openSession();
            entities.forEach(entity->{
                session.save(entity);
            });

            closeSession();
            return entities;
        } catch (Exception e) {
            transaction.rollback();//hata olustugu an yapilan islemleri geri al.
            throw e;
        }

    }
    @Override
    public boolean delete(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();//hata olustugu an yapilan islemleri geri al.
            return false;
        }

    }       //entitymanager ile remove etmeyi ogren session'a bagli kalma!!!
    @Override
    public boolean deleteById(ID id) {
        Optional<T> sonuc = findById(id);
        return delete(sonuc.get());

    }
    @Override
    public Optional<T> findById(ID id) {
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        //bi bakicaz...

        List<T> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        if (resultList.isEmpty())
            return Optional.empty();

            return Optional.ofNullable(resultList.get(0));



    }
    @Override
    public List<T> findByEntity(T entity) {
        try {
            // Musteri{id=null,ad="Ali",adres="Izmir"}
            //
            Field[] fields = entity.getClass().getDeclaredFields();//sinifin icindeki id,ad, adres alindi cunku o kadar bilgi verildi ornek olarak

            CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
            criteriaQuery.select(root);

            List<Predicate> kosulListesi = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); //bu adim sayesinde private alanlar da erisime acilir.
                if (fields[i].get(entity) != null && !fields[i].getName().equals("id")) {
                    if (fields[i].getType().isAssignableFrom(String.class)) {
                        kosulListesi.add(criteriaBuilder.like(root.get(fields[i].getName()), "%" + fields[i].get(entity) + "%"));
                    }
                    else if (fields[i].getType().isAssignableFrom(Integer.class)) {
                        kosulListesi.add(criteriaBuilder.equal(root.get(fields[i].getName()),fields[i].get(entity)));
                    }else {
                        kosulListesi.add(criteriaBuilder.equal(root.get(fields[i].getName()),fields[i].get(entity)));
                    }

                }
            }

            /*for (Predicate predicate : kosulListesi) {
                criteriaQuery.where(predicate);
            }*/ //sor burayi
            criteriaQuery.where(kosulListesi.toArray(new Predicate[]{}));

            return  entityManager.createQuery(criteriaQuery).getResultList();


        } catch (Exception e) {
            System.out.println("Beklenmedik bir hata. findByEntity."+ e.getMessage());
        }
        return null;
    }   //don bir daha bak bakmadan yazana kadar!!!!
    @Override
    public boolean existById(ID id) {
        return findById(id).isPresent();
    }
    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();


    }
    @Override
    public List<T> findAllByColumnNameAndValue(String column, String columnValue) {
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(column), columnValue));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
