package org.berka.repository;

import org.berka.repository.entity.Urun;
import org.berka.utility.MyFactoryRepository;

public class UrunRepository extends MyFactoryRepository<Urun,Long> {
    public UrunRepository() {
        super(new Urun());
    }
}
