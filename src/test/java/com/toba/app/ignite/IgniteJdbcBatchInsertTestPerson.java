package com.toba.app.ignite;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IgniteJdbcBatchInsertTestPerson {

    private static JdbcTemplate igniteJdbcTemplate;

    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        HikariDataSource igniteDs;
//        config.setJdbcUrl( "jdbc:ignite:thin://127.0.0.1" );
//        config.setUsername( "root" );
//        config.setPassword( "whdkwhdkznd" );
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
        config.setUsername( "root" );
        config.setPassword( "whdkwhdkznd!23" );
        config.setDriverClassName( "com.mysql.cj.jdbc.Driver" );

        config.setMaximumPoolSize(10);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );

        igniteDs = new HikariDataSource( config );

        igniteJdbcTemplate = new JdbcTemplate(igniteDs);


        Random randCity = new Random();
        Random randAge = new Random();
        Random randCom = new Random();
        String[] company = {"naver", "kakao", "google", "facebook"};

        List<Person> dataList = new ArrayList<>();
        for (int ii = 5000000; ii < 10000001; ii++) {
            Person person = new Person();
            person.setId(ii);
            person.setCityId(randCity.nextInt(20));
            person.setName("name-"+ii);
            person.setAge(randAge.nextInt(200));
            person.setCompany(company[randCom.nextInt(company.length)]);
            System.out.println("person: "+person.toString());
            dataList.add(person);
            if ((ii % 50000) == 0) {
                batchUpdate(dataList);
                dataList.clear();
            }
        }

    }

    public static int[] batchUpdate(final List<Person> persons) {
        int[] updateCounts = igniteJdbcTemplate.batchUpdate(
                "insert into Person values (?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, persons.get(i).getId());
                        ps.setInt(2, persons.get(i).getCityId());
                        ps.setString(3, persons.get(i).getName());
                        ps.setInt(4, persons.get(i).getCityId());
                        ps.setString(5, persons.get(i).getCompany());
                    }

                    public int getBatchSize() {
                        return persons.size();
                    }
                } );
        return updateCounts;
    }
}
