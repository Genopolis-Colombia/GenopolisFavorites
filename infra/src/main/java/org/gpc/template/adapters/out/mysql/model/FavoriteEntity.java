package org.gpc.template.adapters.out.mysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private UUID userId;
    private UUID proteinId;
    private String fastaName;

}
