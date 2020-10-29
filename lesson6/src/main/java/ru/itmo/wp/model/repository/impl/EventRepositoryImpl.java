package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    public Event find(long id) {
        return (Event) abstractSelectQuery("SELECT * FROM Event WHERE id=?", Collections.singletonList(id), this::toObject);
    }

    @Override
    public void save(Event event) {
        abstractInsertQuery("INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())", event, null);
    }

    @Override
    protected Event toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    switch (resultSet.getString(i)) {
                        case "ENTER":
                            event.setType(Event.TYPE.ENTER);
                            break;
                        case "LOGOUT":
                            event.setType(Event.TYPE.LOGOUT);
                            break;
                        default:
                            // No operations.
                    }
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }

    @Override
    protected void makeStatement(PreparedStatement statement, Event obj, List<Object> args) throws SQLException {
        statement.setLong(1, obj.getUserId());
        statement.setString(2, obj.getType().name());
    }

    @Override
    protected void setGeneratedFields(Event obj, ResultSet generatedKeys) throws SQLException {
        obj.setId(generatedKeys.getLong(1));
        obj.setCreationTime(find(obj.getId()).getCreationTime());
    }
}
