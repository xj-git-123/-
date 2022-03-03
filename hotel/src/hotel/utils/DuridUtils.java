package hotel.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DuridUtils {
    //私有成员 DataSource
    private static DataSource ds;
    static {
        try {
            //加载数据库配置文件
            Properties pro =new Properties();
            pro.load(DuridUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            //获取DataSource 对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

    //获取数据库连接池对象
    public static DataSource getDataSource(){
        return ds;
    }

    //释放数据库资源
    public static void close(ResultSet rs, Statement sta,Connection conn){
        if (null!=rs){
            try{
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null!=sta){
            try{
                sta.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null!=conn){
            try{
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    //释放数据库连接资源
    public static void close(Statement sta,Connection conn){
       close(null,sta,conn);
    }
}
