package org.berka.utility;


import java.util.List;
import java.util.Optional;

public class MyFactoryService<R extends  MyFactoryRepository,T,ID> implements IService<T, ID>{  //degisken olarak hangi repository gelcegini bilmediginden
    //direk myfactoryRepository extend alan bir repository gelsin diyoruz.


    R repository;

    public MyFactoryService(R repository) {
        this.repository = repository;
    }

    @Override
    public <S extends T> S save(S entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        repository.saveAll(entities);
        return entities;
    }

    @Override
    public boolean delete(T entity) {
        return repository.delete(entity);

    }

    @Override
    public boolean deleteById(ID id) {
        return repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findByEntity(T entity) {
        return repository.findByEntity(entity);
    }

    @Override
    public boolean existById(ID id) {
        return repository.existById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllByColumnNameAndValue(String column, String columnValue) {
        return repository.findAllByColumnNameAndValue(column, columnValue);
    }
}
