package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public void updateAdmin(long id, boolean admin) {
        abstractUpdateQuery("UPDATE User SET admin=? WHERE id=?", Arrays.asList(admin, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return (List<User>) abstractSelectQuery("SELECT * FROM User ORDER BY id DESC", null, this::addAll);
    }

    @Override
    public void save(User user, String passwordSha) {
        abstractInsertQuery("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`, `admin`) VALUES (?, ?, ?, NOW(), ?)", user, Collections.singletonList(passwordSha));
    }

    @Override
    protected void makeStatement(PreparedStatement statement, User obj, List<Object> args) throws SQLException {
        statement.setString(1, obj.getLogin());
        statement.setString(2, obj.getEmail());
        statement.setString(3, (String) args.get(0));
        statement.setBoolean(4, obj.isAdmin());
    }

    @Override
    protected void setGeneratedFields(User obj, ResultSet generatedKeys) throws SQLException {
        obj.setId(generatedKeys.getLong(1));
        obj.setCreationTime(find(obj.getId()).getCreationTime());
    }
}
