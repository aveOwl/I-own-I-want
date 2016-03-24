package com.iowniwant.dao.implementation;


import com.iowniwant.model.Goal;
import com.iowniwant.util.DataBaseManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sulfur on 24.03.16.
 */

/*
    Таковыми будут реализации методов класса GoalsDAO в зависимости от обьектов переданных в
качестве параметров его методам
*/
public class GoalsDAO extends AbstractDaoImpl<Goal>{

    DataBaseManager dbManager = new DataBaseManager();

    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1,entity.getTitle());
            prepStatement.setDouble(2,entity.getCost());
            prepStatement.setString(3,entity.getDescription());
            prepStatement.setString(4,entity.getPubdate());
            prepStatement.setString(5,entity.getNotes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1,entity.getTitle());
            prepStatement.setDouble(2,entity.getCost());
            prepStatement.setString(3,entity.getDescription());
            prepStatement.setString(4,entity.getPubdate());
            prepStatement.setString(5,entity.getNotes());
            prepStatement.setInt(6,entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getGetByNickQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.goal");
    }

    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.goal.by.id");
    }

    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.goal");
    }

    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.by.id.goal");
    }

    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.goal");
    }

    @Override
    public Goal getEntity(ResultSet resultSet) {
//        зачем try catch если он реализован в месте вызова функции
        try {
            return new Goal(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
