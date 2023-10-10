package com.matveyvs.database.repository;

import com.matveyvs.entity.User;
import com.matveyvs.utils.ConnectionManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository {
    CompanyRepository companyRepository;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, birth_date, firstname, lastname, role, username, company_id
            FROM  users WHERE id = ?
            """;

    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUser(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("id"))
                .birthDate(result.getTimestamp("birth_date"))
                .firstName(result.getString("firstname"))
                .lastName(result.getString("lastname"))
                .role(result.getString("role"))
                .userName(result.getString("username"))
                .company(companyRepository.findById(result.getInt("company_id"),
                                result.getStatement().getConnection())
                        .orElse(null))
                .build();
    }
}
