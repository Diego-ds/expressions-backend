package com.perficient.expressions.services.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;


@Service
public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    public RowServiceImp (IRow rowRepository) {
    	this.rowRepository = rowRepository;
    }
    
    @Override
    public FindIterable<Document> applyQuery(String rule) throws IllegalArgumentException {
    	
        if (rule==null || rule.length() == 0 || rule.equals("")) {
        	throw new IllegalArgumentException("Rule cannot be null or empty");
        }
        
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

            }else if(!tempExp.equalsIgnoreCase("and") && !tempExp.equalsIgnoreCase("or")){
                String[] parts = expression.split(" ");
                String column = parts[0];
                String operator = parts[1];
                String value = parts[2];

                if(value.charAt(0)=='\''){
                    tempFilter = createStringFilter(column, operator, value);

                }else if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
                    tempFilter = createBooleanFilter(column, operator, value);

                }else if(value.matches("-?\\d+(\\.\\d+)?")){
                    tempFilter = createNumericFilter(column, operator, value);

                }else{
                    tempFilter = createColumnsFilter(column, operator, value);
                }
            }
               
            if(filter==null){
                filter = tempFilter;
            }else if(operation.equalsIgnoreCase("and")){
                filter = Filters.and(filter,tempFilter);
                operation = "";
            }else if(operation.equalsIgnoreCase("or")){
                filter = Filters.or(filter,tempFilter);
                operation = "";
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
        column = column.replace("$", "");
        value = value.replace("$", "");
        double numericValue = Double.parseDouble(value);
        
        switch (operator) {
            case "=":
                return Filters.eq(column, numericValue);
            case "!=":
                return Filters.ne(column, numericValue);
            case ">":
                return Filters.gt(column, numericValue);
            case "<":
                return Filters.lt(column, numericValue);
            default:
                return null;
        }
    }

    private Bson createStringFilter(String column, String operator, String value) {
        column = column.replace("$", "");
        value = value.replace("'", "");

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
        column = column.replace("$", "");
        value = value.replace("$", "");
        return Filters.eq(column, Boolean.parseBoolean(value));
    }

    private Bson createColumnsFilter(String column, String operator, String value) {
        column = column.replace("$", "");
        value = value.replace("$", "");

        if(operator.equalsIgnoreCase("=")){
            operator="==";
        } 
        
        column = "this." + column;
        value = "this." + value;
        operator = " " + operator + " ";

        return Filters.where(column + operator + value);
    }

    @Override
    public FindIterable<Document> findAll() {
        return rowRepository.findAll();
    }
    
    public ArrayList<Document> IterableToList(FindIterable<Document> documents) {
 
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Document> list = new ArrayList<>();

        documents.map(d -> mapper.convertValue(d, Document.class)).into(list);
        return list;
    }

    @Override
    public Document getColumnNames() {
        Document document = rowRepository.findAll().first();

        Set<String> keys = document.keySet();
        List<String> values = new ArrayList<>();

        for (Object value : document.values()) {
            values.add(value.getClass().getSimpleName());
        }  

        Map<String, String> columnNames = new HashMap<>();

        int i = 0;
        for (String key : keys) {
            columnNames.put(key, values.get(i++));
        }
                    
        return new Document(columnNames);
    }

}
