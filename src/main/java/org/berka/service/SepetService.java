package org.berka.service;

import org.berka.repository.SepetDetayRepository;
import org.berka.repository.SepetRepository;
import org.berka.repository.entity.Sepet;
import org.berka.repository.entity.SepetDetay;
import org.berka.utility.MyFactoryService;

public class SepetService extends MyFactoryService<SepetRepository, Sepet,Long> {
    public SepetService() {
        super(new SepetRepository());
    }
}
