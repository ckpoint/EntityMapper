package hsim.mapper.entity.domain;

import hsim.mapper.entity.annotation.IgnoreUpdateFromObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Users extends MapAuditEntity {

    private String name;
    private String email;
    private Integer age;
    private Gender gender;

    @IgnoreUpdateFromObj
    private List<String> strs;
}
