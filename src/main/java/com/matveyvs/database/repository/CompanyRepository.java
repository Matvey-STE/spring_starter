package com.matveyvs.database.repository;

import com.matveyvs.entity.Company;
import com.matveyvs.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CompanyRepository {

    private static final String FIND_BY_ID_SQL = """
            SELECT id, name
            FROM company WHERE id = ?;
            """;

    public Optional<Company> findById(Integer id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Company company = null;
            if (result.next()) {
                company = buildCompany(result);
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Company> findById(Integer id) {
        try (var connection = ConnectionManager.open()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Company buildCompany(ResultSet result) throws SQLException {
        return Company.builder()
                .id(result.getInt("id"))
                .name(result.getString("name"))
                .build();
    }
}
