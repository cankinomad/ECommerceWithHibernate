package org.berka.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.berka.repository.enums.ECinsiyet;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Musteri.findAll", query = "Select m from Musteri m"),
        @NamedQuery(name = "Musteri.findByAd", query = "Select m from Musteri m WHERE lower(m.ad) like lower(:birdegiskenadi)"),   //parametre disaridan gelecekse onuna : koyuyorsun
        //Ilike gibi yapmak istersen lower yap hepsini oyle eslestir
        @NamedQuery(name = "Musteri.findById", query = "Select m from Musteri m where m.id= :musteriid"),
        @NamedQuery(name = "Musteri.getCount", query = "Select count(m) from Musteri m"),
        @NamedQuery(name = "Musteri.getAdSoyad", query = "Select concat(m.ad,' ',m.soyad) from Musteri m")  //tuple ile ayri ayri yapmayi dene

})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblmusteri")
@Entity
public class Musteri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String ad;


    @Lob
    Byte[] resim;

    @Temporal(TemporalType.TIMESTAMP)
    Date kayitTarihi;

    Long baslangicTarihi;

    String soyad;
    @Transient
    String adSoyad;
    @ElementCollection
    List<String> telefonNumaralari;

    @Embedded
    Iletisim iletisim;

    @Embedded
    BaseEntity baseEntity;

    @Enumerated(EnumType.STRING)
    ECinsiyet cinsiyet;
}
