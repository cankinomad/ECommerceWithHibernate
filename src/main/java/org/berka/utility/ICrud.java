package org.berka.utility;


import java.util.List;
import java.util.Optional;

public interface ICrud<T, ID> extends IMyRepository<T, ID> {  //icrud icinde de tanimlaman lazim <T,ID>'yi
    //Kaydetme islemleri
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);    //collectionlarin en ust yapisi. FArkli yapilar gelebilir sadece list gelmeyebilir o yuzden

    //Silme islemleri
    boolean delete(T entity);

    boolean deleteById(ID id); //integer mi long mu? id'nin ne gelecegini bilmedigimizden boyle yapiyoruz

    //Arama ve listeleme islemleri.

    Optional<T> findById(ID id);    //geriye bos donme ihtimali olan durumlarda optional yap

    /**
     * Bu metod entity icinde bulunan herhangi  bir alana(field kolon) gore kendisi otomatik sorgu yapacak
     * Reflection API kullanilacak.
     * @param entity
     * @return
     */
    List<T> findByEntity(T entity);

    boolean existById(ID id);

    List<T> findAll();

    List<T> findAllByColumnNameAndValue(String column, String columnValue);


}
