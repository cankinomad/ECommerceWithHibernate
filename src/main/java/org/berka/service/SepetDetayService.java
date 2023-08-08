package org.berka.service;

import org.berka.repository.MusteriRepository;
import org.berka.repository.SepetDetayRepository;
import org.berka.repository.entity.Musteri;
import org.berka.repository.entity.SepetDetay;
import org.berka.utility.MyFactoryService;

public class SepetDetayService extends MyFactoryService<SepetDetayRepository, SepetDetay,Long> {
    public SepetDetayService() {
        super(new SepetDetayRepository());
    }
}
