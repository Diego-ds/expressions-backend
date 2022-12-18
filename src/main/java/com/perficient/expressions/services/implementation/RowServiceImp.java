package com.perficient.expressions.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

import ch.qos.logback.core.filter.Filter;
import lombok.val;

@Service
public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    @Override
    public FindIterable<Document> applyQuery(String rule) {

        Bson filter = createFilter(rule);
        return rowRepository.customQuery(filter);
    }

    private Bson createFilter(String rule){
        //String rule = "columna = 2 and (columna2 = hello or columna3 = world)";
        Bson filter = null;
        Bson tempFilter = null;
        String operation = "";

        List<String> expressions = extractExpressions(rule);
        for(String expression : expressions){
            String tempExp = expression.replace(" ", "");

            if(expression.charAt(0)=='('){
                expression = expression.substring(1, expression.length()-1);
                tempFilter = createFilter(expression);

                if(filter==null){
                    filter = tempFilter;
                    continue;
                }

                if(operation.equalsIgnoreCase("and")){
                    filter = Filters.and(filter,tempFilter);
                    operation = "";
                }else if(operation.equalsIgnoreCase("or")){
                    filter = Filters.or(filter,tempFilter);
                    operation = "";
                }

            }else if(!tempExp.equalsIgnoreCase("and") && !tempExp.equalsIgnoreCase("or")){
                String[] parts = expression.split(" ");
                String column = parts[0];
                String operator = parts[1];
                String value = parts[2];

                if(value.charAt(0)=='$'){
                    tempFilter = createColumnsFilter(column, operator, value);

                }else if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
                    tempFilter = createBooleanFilter(column, operator, value);

                }else if(value.matches("-?\\d+(\\.\\d+)?")){
                    tempFilter = createNumericFilter(column, operator, value);

                }else{
                    tempFilter = createStringFilter(column, operator, value);
                }

                if(filter==null){
                    filter = tempFilter;
                    continue;
                }

                if(operation.equalsIgnoreCase("and")){
                    filter = Filters.and(filter,tempFilter);
                    operation = "";
                }else if(operation.equalsIgnoreCase("or")){
                    filter = Filters.or(filter,tempFilter);
                    operation = "";
                }

            }else{
                operation = tempExp;

            }
        }
        return filter;
    }

    public List<String> extractExpressions(String rule) {
        List<String> expressions = new ArrayList<>();
        // Use a regular expression to find all substrings that consist of a word followed by an operator and another word or value,
        // or a logic connector (and or or) surrounded by spaces,
        // or a group of expressions surrounded by parentheses
        Pattern p = Pattern.compile("\\b\\w+\\s*[=!<>]+\\s*\\S+|\\s+(and|or)\\s+|\\((.*?)\\)");
        Matcher m = p.matcher(rule);
        while (m.find()) {
          expressions.add(m.group());
        }
        return expressions;
    }


    private Bson createNumericFilter(String column, String operator, String value) {
        switch (operator) {
            case "=":
                return Filters.eq(column, Double.parseDouble(value));
            case "!=":
                return Filters.ne(column, Double.parseDouble(value));
            case ">":
                return Filters.gt(column, Double.parseDouble(value));
            case "<":
                return Filters.lt(column, Double.parseDouble(value));
            default:
                return null;
        }
    }

    private Bson createStringFilter(String column, String operator, String value) {
        switch (operator) {
            case "=":
                return Filters.eq(column, value);
            case "!=":
                return Filters.ne(column, value);
            default:
                return null;
        }
    }

    private Bson createBooleanFilter(String column, String operator, String value) {
        return Filters.eq(column, Boolean.parseBoolean(value));
    }

    private Bson createColumnsFilter(String column, String operator, String value) {
        value = value.replace("$", "");

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
