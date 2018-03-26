package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;


public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }


    public static String getHash(String originalString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    public UsersDataSet getById(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3),result.getString(4));
        });
    }

    public UsersDataSet tryToLogin(String login, String password) throws SQLException, NoSuchAlgorithmException {
        password = getHash(password);
        return executor.execQuery("select * from users where user_name='" + login + "'" +
                "and password='" + password + "'", result ->{
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3),result.getString(4));
        });
    }

    public long getUserId(String name) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + name + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public boolean checkIfEmailExist(String email) throws  SQLException{
        boolean existance;
       return executor.execQuery("SELECT " +
                "  users, \n" +
                "  CASE WHEN EXISTS (SELECT  FROM users WHERE email = " + email +")\n" +
                "       THEN 'TRUE' \n" +
                "       ELSE 'FALSE'\n"
                ,result -> {
             result.next();
             return new Boolean(result.getBoolean(2));
         });
    }

    public UsersDataSet getUserByEmail(String email) throws SQLException{

        return executor.execQuery("select * from users where email=" + email, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3),result.getString(3));
        });
    }

    public UsersDataSet getUser(String login) throws SQLException{

            return executor.execQuery("select * from users where user_name=" + login, result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3),result.getString(4));
            });
    }

    public void insertUser(String name,String email, String password) throws SQLException, NoSuchAlgorithmException {
        password = getHash(password);
        executor.execUpdate("insert into users (user_name, email, password) values ('" + name + "'," + "'" + email + "'," +  "'" + password + "'" +")");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, user_name varchar(256), email varchar(256), password varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }

}
