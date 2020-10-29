package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl<Talk> implements TalkRepository {
    @Override
    public Talk find(long id) {
        return (Talk) abstractSelectQuery("SELECT * FROM Talk WHERE id=?", Collections.singletonList(id), this::toObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Talk> findAllByUserId(long userId) {
        return (List<Talk>) abstractSelectQuery("SELECT * FROM Talk WHERE targetUserId=? OR sourceUserId=? ORDER BY creationTime DESC", Arrays.asList(userId, userId), this::addAll);
    }

    @Override
    public void save(Talk talk) {
        abstractInsertQuery("INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())", talk, null);
    }

    @Override
    protected Talk toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }

    @Override
    protected void makeStatement(PreparedStatement statement, Talk obj, List<Object> args) throws SQLException {
        statement.setLong(1, obj.getSourceUserId());
        statement.setLong(2, obj.getTargetUserId());
        statement.setString(3, obj.getText());
    }

    @Override
    protected void setGeneratedFields(Talk obj, ResultSet generatedKeys) throws SQLException {
        obj.setId(generatedKeys.getLong(1));
        obj.setCreationTime(find(obj.getId()).getCreationTime());
    }
}
