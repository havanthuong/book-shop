package vn.demo.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.demo.starter.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
