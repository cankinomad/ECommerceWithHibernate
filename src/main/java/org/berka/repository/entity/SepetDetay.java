package org.berka.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblsepetdetay")
@Entity
public class SepetDetay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long sepetid;
    Long urunid;
    Integer adet;
    Double toplamfiyat;
    Integer kdv;
    Double kdvtutari;

    @Embedded
    BaseEntity baseEntity;
}
