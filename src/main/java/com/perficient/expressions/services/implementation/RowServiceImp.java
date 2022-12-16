package com.perficient.expressions.services.implementation;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

import lombok.val;

public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    @Override
    public FindIterable<Document> applyQuery(String rule) {
        // separamos la regla en expresiones y operadores
        // nombre = 'apellido'
        // nombre = apellido
        // si el texto no tiene comillas entonces es una columna

        // le llega convertido en peticion de mongodb
        // las une y la envía al metodo customQuery

        return rowRepository.customQuery(null);
    }

    private Bson createNumericFilter(String column, String operator, String value) {
        switch (operator) {
            case "=":
                return Filters.eq(column, value);
            case "!=":
                return Filters.ne(column, value);
            case ">":
                return Filters.gt(column, value);
            case "<":
                return Filters.lt(column, value);
            default:
                return null;
        }
    }

    private Bson createStringFilter(String column, String operator, String value) {

        return null;

    }

    private Bson createBooleanFilter(String column, String operator, String value) {
        return Filters.eq(column, Boolean.parseBoolean(value));
    }

    private Bson createColumnsFilter(String column, String operator, String value) {
        value = value.replace("'", "");

        column = "this." + column;
        value = "this." + value;
        operator = " " + operator + " ";

        return Filters.where(column + operator + value);
    }

    @Override
    public FindIterable<Document> findAll() {
        return rowRepository.findAll();
    }

}
