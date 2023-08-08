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
@Table(name = "tblurun")
@Entity
public class Urun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String ad;
    String marka;
    Double fiyat;
    @Embedded
    BaseEntity baseEntity;
}
