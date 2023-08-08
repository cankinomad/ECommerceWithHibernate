package org.berka.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BaseEntity {
    Long olusturmatarihi;
    Long guncellemetarihi;
    Integer state;
}
