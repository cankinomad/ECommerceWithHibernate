package org.berka.service;

import org.berka.repository.MusteriRepository;
import org.berka.repository.entity.Musteri;
import org.berka.utility.MyFactoryService;

public class MusteriService extends MyFactoryService<MusteriRepository, Musteri,Long> {
    public MusteriService() {
        super(new MusteriRepository());
    }
}
