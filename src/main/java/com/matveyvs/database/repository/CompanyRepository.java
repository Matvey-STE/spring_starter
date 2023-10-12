package com.matveyvs.database.repository;

import com.matveyvs.entity.Company;
import com.matveyvs.exception.DaoException;
import com.matveyvs.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class CompanyRepository {
    private static final String SAVE_SQL = """
            INSERT INTO company
            (name)
            VALUES (?)
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name
            FROM company WHERE id = ?;
            """;
    private static final String UPDATE_BY_ID = """
            UPDATE company
            SET   name = ?
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM company
            WHERE id = ?
            """;

    public Company save(Company company) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            Integer id = null;
            if (keys.next()) {
                id = keys.getInt("id");
            }
            return Company.builder()
                    .id(id)
                    .name(company.getName())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    public boolean update(Company company) {
        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, company.getName());
            statement.setInt(2, company.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.open();
             var statement =
                     connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Company buildCompany(ResultSet result) throws SQLException {
        return Company.builder()
                .id(result.getInt("id"))
                .name(result.getString("name"))
                .build();
    }
}
