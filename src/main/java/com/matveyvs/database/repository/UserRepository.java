package com.matveyvs.database.repository;

import com.matveyvs.entity.Role;
import com.matveyvs.entity.User;
import com.matveyvs.exception.DaoException;
import com.matveyvs.utils.ConnectionManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository {
    CompanyRepository companyRepository;
    private static final String SAVE_SQL = """
            INSERT INTO users
            (birth_date, firstname, lastname, role, username, company_id)
            VALUES (?,?,?,?,?,?)
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, birth_date, firstname, lastname, role, username, company_id
            FROM  users WHERE id = ?
            """;
    private static final String UPDATE_BY_ID = """
            UPDATE users
            SET   birth_date = ?, firstname = ?, lastname = ?,
            role = ?, username = ?, company_id = ?
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    public User save(User user) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setUserIntoStatement(user, statement);
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            Long id = null;
            if (keys.next()) {
                id = keys.getLong("id");
            }
            return User.builder()
                    .id(id)
                    .birthDate(user.getBirthDate())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .userName(user.getUserName())
                    .company(user.getCompany())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    public boolean update(User user) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(UPDATE_BY_ID)) {
            setUserIntoStatement(user, statement);
            statement.setLong(7, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.open();
             var statement =
                     connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("id"))
                .birthDate(result.getTimestamp("birth_date"))
                .firstName(result.getString("firstname"))
                .lastName(result.getString("lastname"))
                .role(Role.valueOf(result.getString("role")))
                .userName(result.getString("username"))
                .company(companyRepository.findById(result.getInt("company_id"),
                                result.getStatement().getConnection())
                        .orElse(null))
                .build();
    }

    private static void setUserIntoStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, user.getBirthDate());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, String.valueOf(user.getRole()));
        statement.setString(5, user.getUserName());
        statement.setInt(6, user.getCompany().getId());
    }
}
