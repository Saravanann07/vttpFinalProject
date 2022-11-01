package vttp2022batch1.finalProjectServer.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022batch1.finalProjectServer.models.User;

@Repository
public class UserRepository {

    public static final String SQL_FIND_USER_BY_USERNAME = 
    "select * from users where username =?";

    public static final String SQL_ADD_USER = 
    "insert into users (email, username, password) values (?, ?, sha1(?))";

    public static final String SQL_CHECK_USER_EXISTS = "select count(*) as login_success from users where username = ? and password = sha1(?)"; 

    public static final String SQL_SEARCH_USER = "select * from users where username = ? and password = sha1(?)";

    public static final String SQL_SEARCH_USER_ID = "select user_id from users where username = ?";

    @Autowired
    private JdbcTemplate template;

    public int addUser(User user){
        int count = template.update(SQL_ADD_USER, user.getEmail(), user.getUsername(), user.getPassword());
        return count;
    }

    public int countUsersByNameAndPassword(User user){
        SqlRowSet rs = template.queryForRowSet(SQL_CHECK_USER_EXISTS, user.getUsername(), user.getPassword());
        if (!rs.next())
            return 0;
        return rs.getInt("login_success");
    }

    public User getUser (User user){
        SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_USER, user.getUsername(), user.getPassword());
        if (!rs.next())
            return null;
       User user1 = new User();
       user1.setUserId(rs.getInt("user_id"));
       user1.setUsername(rs.getString("username"));
       user1.setPassword(rs.getString("password"));
        return user1;

    }

    public User getUser1 (String username, String password){

        System.out.println(username);
        System.out.println(password);

        SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_USER, username, password);
        if (!rs.next()) {
            System.out.println(">>>>> error");
            return null;
        }
            
       rs.next();
       User user1 = new User();
       user1.setUserId(rs.getInt("user_id"));
       user1.setUsername(rs.getString("username"));
       user1.setPassword(rs.getString("password"));
        return user1;
    }

    public Integer getUserId (String username){
        SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_USER_ID, username);
        if (!rs.next())
            return null;
    //    User user1 = new User();
    //    user1.setUserId(rs.getInt("user_id"));
    //    user1.setUsername(rs.getString("username"));
    //    user1.setPassword(rs.getString("password"));
        return rs.getInt("user_id");

    }





    
}
