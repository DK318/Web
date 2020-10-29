package ru.itmo.wp.model.repository.impl;

import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

    @Override
    public User find(long id) {
        return (User) abstractSelectQuery("SELECT * FROM User WHERE id=?", Collections.singletonList(id), this::toObject);
    }

    @Override
    public User findByLogin(String login) {
        return (User) abstractSelectQuery("SELECT * FROM User WHERE login=?", Collections.singletonList(login), this::toObject);
    }

    @Override
    public User findByEmail(String email) {
        return (User) abstractSelectQuery("SELECT * FROM User WHERE email=?", Collections.singletonList(email), this::toObject);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return (User) abstractSelectQuery("SELECT * FROM User WHERE login=? AND passwordSha=?", Arrays.asList(login, passwordSha), this::toObject);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return (User) abstractSelectQuery("SELECT * FROM User WHERE email=? AND passwordSha=?", Arrays.asList(email, passwordSha), this::toObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return (List<User>) abstractSelectQuery("SELECT * FROM User ORDER BY id DESC", null, this::addAll);
    }

    @Override
    public Long findCount() {
        return ((Number) abstractSelectQuery("SELECT * FROM User", null, (ResultSetMetaData metaData, ResultSet resultSet) -> ((SelectResultSet) resultSet).getDataSize())).longValue();
    }

    @Override
    public void save(User user, String passwordSha) {
        abstractInsertQuery("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())", user, Collections.singletonList(passwordSha));
    }

    @Override
    protected User toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    protected void makeStatement(PreparedStatement statement, User obj, List<Object> args) throws SQLException {
        statement.setString(1, obj.getLogin());
        statement.setString(2, obj.getEmail());
        statement.setString(3, (String) args.get(0));
    }

    @Override
    protected void setGeneratedFields(User obj, ResultSet generatedKeys) throws SQLException {
        obj.setId(generatedKeys.getLong(1));
        obj.setCreationTime(find(obj.getId()).getCreationTime());
    }
}
