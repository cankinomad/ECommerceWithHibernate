package org.berka.service;

import org.berka.repository.SepetDetayRepository;
import org.berka.repository.UrunRepository;
import org.berka.repository.entity.SepetDetay;
import org.berka.repository.entity.Urun;
import org.berka.utility.MyFactoryService;

public class UrunService extends MyFactoryService<UrunRepository, Urun,Long> {
    public UrunService() {
        super(new UrunRepository());
    }
}
