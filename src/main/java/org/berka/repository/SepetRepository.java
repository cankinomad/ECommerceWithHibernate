package org.berka.repository;

import org.berka.repository.entity.Sepet;
import org.berka.utility.MyFactoryRepository;

public class SepetRepository extends MyFactoryRepository<Sepet,Long> {
    public SepetRepository() {
        super(new Sepet());
    }
}
