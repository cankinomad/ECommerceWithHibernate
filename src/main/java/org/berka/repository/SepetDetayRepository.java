package org.berka.repository;

import org.berka.repository.entity.SepetDetay;
import org.berka.utility.MyFactoryRepository;

public class SepetDetayRepository extends MyFactoryRepository<SepetDetay,Long> {
    public SepetDetayRepository() { super(new SepetDetay());
    }
}
