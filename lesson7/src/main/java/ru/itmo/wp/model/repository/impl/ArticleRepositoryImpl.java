package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArticleRepositoryImpl extends BasicRepositoryImpl<Article> implements ArticleRepository {
    @Override
    public Article find(long id) {
        return (Article) abstractSelectQuery("SELECT * FROM Article WHERE id=?", Collections.singletonList(id), this::toObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> findNotHidden() {
        return (List<Article>) abstractSelectQuery("SELECT * FROM Article WHERE hidden=0 ORDER BY creationTime DESC", null, this::addAll);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> findAllByUserId(long userId) {
        return (List<Article>) abstractSelectQuery("SELECT * FROM Article WHERE userId=?", Collections.singletonList(userId), this::addAll);
    }

    @Override
    public void updateHidden(long articleId, boolean hidden) {
        abstractUpdateQuery("UPDATE Article SET hidden=? WHERE id=?", Arrays.asList(hidden, articleId));
    }

    @Override
    public void save(Article article) {
        abstractInsertQuery("INSERT INTO `Article` (`userId`, `title`, `text`, `creationTime`, `hidden`) VALUES (?, ?, ?, NOW(), ?)", article, null);
    }

    @Override
    protected void makeStatement(PreparedStatement statement, Article obj, List<Object> args) throws SQLException {
        statement.setLong(1, obj.getUserId());
        statement.setString(2, obj.getTitle());
        statement.setString(3, obj.getText());
        statement.setBoolean(4, obj.isHidden());
    }

    @Override
    protected void setGeneratedFields(Article obj, ResultSet generatedKeys) throws SQLException {
        obj.setId(generatedKeys.getLong(1));
        obj.setCreationTime(find(obj.getId()).getCreationTime());
    }
}
