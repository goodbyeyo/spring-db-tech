package study.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static study.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        // driverManager 는 커넥션을 획득할때마다 URL, USERNAME, PASSWORD를 계속 전달해야한다
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Pool 1개
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Pool 1개를 획득
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource - 항상 새로운 커넥션을 획득, DataSource을 받을수도 있다
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        userDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        userDataSource(dataSource);
        Thread.sleep(1000);
    }

   private void userDataSource(DataSource dataSource) throws SQLException{
        // dataSource를 사용하는 방식은 처음 객체를 생성할 때만 필요한 파라미터를 넘기고 커넥션을 획득할때는 호출만 한다
        Connection con1 = dataSource.getConnection();   // dataSource 인터페이스를 통해서 가져온다다
        Connection con2 = dataSource.getConnection();
//       Connection con3 = dataSource.getConnection();
//       Connection con4 = dataSource.getConnection();
//       Connection con5 = dataSource.getConnection();
//       Connection con6 = dataSource.getConnection();
//       Connection con7 = dataSource.getConnection();
//       Connection con8 = dataSource.getConnection();
//       Connection con9 = dataSource.getConnection();
//       Connection con10 = dataSource.getConnection();
//       Connection con11= dataSource.getConnection();

       // HikariCP Connection Pool에 대한 자세한 내용
       // https://github.com/brettwooldridge/HikariCP

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

}
