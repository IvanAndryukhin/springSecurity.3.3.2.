package net.javacode.spring_311.repositories;

import net.javacode.spring_311.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = ?1")
    Optional<User> findByUsername(@NonNull String username);

    @NonNull
    List<User> findAll();

    @NonNull
    Optional<User> findById(@NonNull Long id);

    void deleteById(@NonNull Long id);

    boolean existsByUsername(@NonNull String username);
}
