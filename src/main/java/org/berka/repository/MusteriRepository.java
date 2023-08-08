package org.berka.repository;

import org.berka.repository.entity.Musteri;
import org.berka.utility.MyFactoryRepository;

public class MusteriRepository extends MyFactoryRepository<Musteri,Long> {

    public MusteriRepository() {
        super(new Musteri());
    }
}
