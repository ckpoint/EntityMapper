package hsim.mapper.entity.repository;

import hsim.mapper.entity.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsersRepository extends JpaRepository<Users, Long> {

}
