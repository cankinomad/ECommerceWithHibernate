package org.berka;

import org.berka.controller.CriteriaOrnekKullanimlar;
import org.berka.repository.UrunRepository;
import org.berka.repository.entity.Musteri;
import org.berka.repository.entity.Urun;
import org.berka.repository.enums.ECinsiyet;
import org.berka.service.MusteriService;
import org.berka.utility.HibernateUtility;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class ETicaretApplication {
    public static void main(String[] args) {
//        save();
      //  saveUrun();
//        list();
//        urunList();

        CriteriaOrnekKullanimlar cr = new CriteriaOrnekKullanimlar();

//        cr.findAll();
//        cr.parametreKullanim("Berk");
//    cr.findByUrunAd("Sut");

//        cr.tupleOrnek();
       // List<Tuple> tupleList = cr.manyRoot();

//        tupleList.forEach(x->{
//            System.out.println(x.get(0)+" "+x.get(1));
//        });

//        cr.predicateKullanimi();

//        cr.groupByKullanimi();

//        cr.nativeSqlQueryKullanimi();
//cr.namedQueryFindAll();
//        cr.namedQueryFindByAd("H%");
//        cr.namedQueryFindById(1L);
//    cr.namedQueryGetCount();

        // cr.namedQueryGetAdSoyad();
        // cr.namedQueryFindAllPagination(2,2);


//        MusteriRepository repository = new MusteriRepository();
        UrunRepository urunRepository = new UrunRepository();
        // repository.save(m1);
//        System.out.println(repository.findById(2));
        // repository.findAllByColumnNameAndValue("ad", "Engin");
        //repository.deleteById(10);
        //repository.delete(m1);

        MusteriService service = new MusteriService();

        service.findAll().forEach(System.out::println);

       /* Musteri m1 = Musteri.builder().ad("e").soyad("a").build();
        repository.findByEntity(m1).forEach(x->{
            System.out.println(x.getId()+"-"+x.getAd()+"-"+x.getSoyad());
        });*/

    }

    private static void criteriaList() {
        EntityManager entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Musteri> criteriaQuery = criteriaBuilder.createQuery(Musteri.class);
        Root<Musteri> root = criteriaQuery.from(Musteri.class);
        criteriaQuery.select(root);

        List<Musteri> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        resultList.forEach(m->{
            System.out.println("Musteri ad:"+m.getAd()+" soyad: "+m.getSoyad());
        });
    }


    public static void list(){
        Session session=HibernateUtility.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Musteri.class);  //artik kullanilmiyor.
        List<Musteri> musteriList = criteria.list();
        session.close();

        musteriList.forEach(m->{
            System.out.println("Musteri ad:"+m.getAd()+" soyad: "+m.getSoyad());
        });
    }

    public static void urunList() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Urun.class);
        List<Urun> list = criteria.list();
        session.close();
        list.forEach(x->{
            System.out.println(x.getAd()+" "+x.getFiyat());
        });
    }

    private static void save() {
        Session session= HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<String> telefonList = Arrays.asList("0555 333 33 33", "0532 444 44 44");

        Musteri musteri = Musteri.builder()
                .ad("Berk").soyad("Aktas").telefonNumaralari(telefonList).
                cinsiyet(ECinsiyet.ERKEK) .build();

        session.save(musteri);

        transaction.commit();
        session.close();
    }

    private static void saveUrun() {
        Session session= HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Urun urun= Urun.builder()
                .ad("Dondurma")
                .fiyat(33.99)
                .marka("Pinar")
                .build();

        session.save(urun);

        transaction.commit();
        session.close();
    }
}