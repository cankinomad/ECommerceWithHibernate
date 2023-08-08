package org.berka.controller;

import org.berka.repository.entity.Musteri;
import org.berka.repository.entity.Urun;
import org.berka.utility.HibernateUtility;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CriteriaOrnekKullanimlar {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public CriteriaOrnekKullanimlar() {
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Musteri> findAll() {
        CriteriaQuery<Musteri> criteriaQuery = criteriaBuilder.createQuery(Musteri.class); //donus tipi
        Root<Musteri> from = criteriaQuery.from(Musteri.class); // hangi siniftan alcaksin
        criteriaQuery.select(from);
        List<Musteri> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        resultList.forEach(x->{
            System.out.println(x.getId()+" "+ x.getAd()+" "+x.getSoyad());
        });
        return resultList;
    }

    public void selectFromOneColumn() {
        CriteriaQuery<String> criteria = criteriaBuilder.createQuery(String.class);
        Root<Musteri> from = criteria.from(Musteri.class);
        criteria.select(from.get("ad"));
        criteria.where(criteriaBuilder.equal(from.get("soyad"), "Okay"));

        List<String> resultList = entityManager.createQuery(criteria).getResultList();
        resultList.forEach(System.out::println);
    }

    public void selectManyColumn() {
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Musteri> from = criteriaQuery.from(Musteri.class);
        Path<Long> idPath = from.get("id");
        Path<String> adPath = from.get("ad");
        Path<String> soyadPath = from.get("soyad");

        criteriaQuery.select(criteriaBuilder.array(idPath, adPath, soyadPath));
        List<Object[]> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        resultList.stream().forEach(x->{
            for (Object o : x) {
                System.out.print(o+" ");
            }
            System.out.println();
        });
    }

    public void tupleOrnek(){
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Musteri> root = criteriaQuery.from(Musteri.class);
        Path<Integer> idPath = root.get("id");
        Path<String> adPath = root.get("ad");
        Path<String> soyadPath = root.get("soyad");

        criteriaQuery.multiselect(idPath, adPath, soyadPath);
        List<Tuple> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        resultList.stream().forEach(x->{

            System.out.println(x.get(idPath)+" "+x.get(1)+" "+x.get(2)); //yukarida verdigin indexlere gider

            System.out.println();
        });

    }
    public List<Tuple> manyRoot(){
        CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);
        Root<Musteri> musteriRoot = criteria.from(Musteri.class);
        Root<Urun> urunRoot = criteria.from(Urun.class);

        criteria.multiselect(musteriRoot, urunRoot);
        List<Tuple> tupleList=entityManager.createQuery(criteria).getResultList();
        return tupleList;
    }

    //select * from tblmusteri where ad=""

    public void parametreKullanim(String musteriAd) {
        CriteriaQuery<Musteri> criteriaQuery = criteriaBuilder.createQuery(Musteri.class);
        Root<Musteri> root = criteriaQuery.from(Musteri.class);

        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("ad"), parameter));

        TypedQuery<Musteri> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setParameter(parameter, musteriAd);
        List<Musteri> resultList = typedQuery.getResultList();
        resultList.forEach(x->{
            System.out.println("id: "+x.getId()+"- isim: "+x.getAd());
        });
    }

    public void findByUrunAd(String urunad) {
        CriteriaQuery<Urun> criteriaQuery = criteriaBuilder.createQuery(Urun.class);
        Root<Urun> root = criteriaQuery.from(Urun.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("ad"), parameter));

        TypedQuery<Urun> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(parameter, urunad);
        Urun singleResult = typedQuery.getSingleResult();
        System.out.println("id: "+singleResult.getId()+" ad: "+singleResult.getAd()+" fiyat: "+singleResult.getFiyat());
    }

    public void predicateKullanimi(){
        CriteriaQuery<Musteri> criteriaQuery = criteriaBuilder.createQuery(Musteri.class);
        Root<Musteri> root = criteriaQuery.from(Musteri.class);

        Predicate predicate1= criteriaBuilder.and(  //parantez icindeki where ad like 'G%' AND soyad is not null kismini yapiyor
                criteriaBuilder.like(root.get("ad"),"B%"),criteriaBuilder.isNotNull(root.get("soyad")));

        Predicate predicate2 = criteriaBuilder.greaterThan(root.get("id"), 4);      //2 tane predicate hazirladik.  //sartlariuni yaziyorsun ve sorgunu assagida ona gore where icine or veya and ile tanimliyorsun

        criteriaQuery.where(criteriaBuilder.or(predicate1, predicate2));

        List<Musteri> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        resultList.forEach(x->{
            System.out.println(x.getId()+"-"+x.getAd());
        });

    }

    public void groupByKullanimi(){
        CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);
        Root<Musteri> root = criteria.from(Musteri.class);


        criteria.groupBy(root.get("cinsiyet"));
        criteria.multiselect(root.get("cinsiyet"), criteriaBuilder.count(root));

        List<Tuple> resultList = entityManager.createQuery(criteria).getResultList();
        resultList.forEach(x->{
            System.out.println(x.get(0)+"-"+x.get(1));
        });

    }

    public void nativeSqlQueryKullanimi() {
        List<Object[]> resultList = entityManager.createNativeQuery("Select * from tblmusteri").getResultList();
        resultList.forEach(x->{
            System.out.println(Arrays.asList(x).get(0)+"-"+Arrays.asList(x).get(1));
        });
    }

    public void namedQueryFindAll(){
        TypedQuery<Musteri> typedQuery = entityManager.createNamedQuery("Musteri.findAll", Musteri.class);
        List<Musteri> resultList = typedQuery.getResultList();
        resultList.forEach(x->{
            System.out.println(x.getId()+"-"+x.getAd()+"-"+x.getSoyad());
        });
    }

    public void namedQueryFindByAd(String ad){
        TypedQuery<Musteri> typedQuery = entityManager.createNamedQuery("Musteri.findByAd", Musteri.class);
        typedQuery.setParameter("birdegiskenadi", ad); //sorgudaki degisken adini yaz sonra aldigin parametreyi yaz
        List<Musteri> resultList = typedQuery.getResultList();
        resultList.forEach(x->{
            System.out.println(x.getId()+"-"+x.getAd()+"-"+x.getSoyad());
        });
    }

    public void namedQueryFindById(Long id){
        TypedQuery<Musteri> typedQuery = entityManager.createNamedQuery("Musteri.findById", Musteri.class); //donustip
        typedQuery.setParameter("musteriid", id);

        Optional<Musteri> sonuc;

        try {
            Musteri x= typedQuery.getSingleResult();
            sonuc=Optional.of(x);

        } catch (Exception e) {
            System.out.println("Bir hata oldu");
            sonuc = Optional.empty();
        }
        if (sonuc.isPresent()) {
            System.out.println(sonuc.get().getId()+"-"+sonuc.get().getAd()+"-"+sonuc.get().getSoyad());
        }

    }

    public void namedQueryGetCount(){
        TypedQuery<Long> typedQuery = entityManager.createNamedQuery("Musteri.getCount", Long.class); //donustip    //nasil musteriden cekiyor.
        Long singleResult = typedQuery.getSingleResult();
        System.out.println("Musteri sayisi: "+singleResult);
    }

    public void namedQueryGetAdSoyad(){
        TypedQuery<String> typedQuery = entityManager.createNamedQuery("Musteri.getAdSoyad", String.class); //donus tipini yaz.
        List<String> resultList = typedQuery.getResultList();
        for (String s : resultList) {
            System.out.println(s);
        }
    }




    public void namedQueryFindAllPagination(int page,int count){
        TypedQuery<Musteri> typedQuery = entityManager.createNamedQuery("Musteri.findAll", Musteri.class);
        typedQuery.setMaxResults(page);//bir kerede gosterilecek kayit sayisi
        typedQuery.setFirstResult(page*count);//gosterecegi kaydin ilk indexi (skip metodu gibi)
        List<Musteri> resultList = typedQuery.getResultList();
        resultList.forEach(x->{
            System.out.println(x.getId()+"-"+x.getAd()+"-"+x.getSoyad());
        });
    }
}
