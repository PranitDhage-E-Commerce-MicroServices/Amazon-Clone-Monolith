package com.web.ecomm.app.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    String FIND_ALL_VALID_TOKEN_BY_USER = """
            SELECT t FROM Token t
            INNER JOIN User u on t.user.userId = u.userId
            WHERE u.userId = :id AND (t.expired = false OR t.revoked = false)
            """;

    @Query(value = FIND_ALL_VALID_TOKEN_BY_USER, nativeQuery = true)
    List<Token> findAllValidTokenByUser(@Param("id") Integer id);

    Optional<Token> findByToken(String token);
}