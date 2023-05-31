package com.construcao.financiase.user.repository;

import com.construcao.financiase.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
