package vn.hoidanit.laptopshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User save(User quy);

    List<User> findOneByEmail(String email);
     List<User> findByIdAndAddress(long id, String address);
    //  @Query("select u from user u where u.id=:id")
    //  List<User> moi(@Param("id")Long id);
    boolean existsByEmail(String email);

    User findByEmail(String email);
} 