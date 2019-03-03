package hsim.mapper.entity.domain;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Users extends MapAuditEntity {

    private String name;
    private String email;
    private int age;
    private Gender gender;
}
