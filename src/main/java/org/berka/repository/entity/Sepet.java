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
@Table(name = "tblsepet")
@Entity
public class Sepet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long musteriid;
    Long date;
    Double toplamfiyat;
    Double toplamkdv;

    @Embedded
    BaseEntity baseEntity;
}
