package hsim.mapper.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsersModel extends MapAuditModel {
    private String name;
    private String email;
    private Integer age;
    private String gender;
    private List<String> strs;
}
