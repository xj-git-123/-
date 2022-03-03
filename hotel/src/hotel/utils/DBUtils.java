package hotel.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBUtils {
    /*
    * 使用dbUtils进行增删改
    * */
    public static int Update(String sql,Object...params){
        Connection connection=null;
        try{
            //建立连接
            connection =DuridUtils.getConnection();
            //创建执行SQL增删改查的对象
            QueryRunner queryRunner=new QueryRunner();
            //执行
            int update=queryRunner.update(connection,sql,params);
            return update;
        }
        catch (Exception e){
            throw  new RuntimeException();
        }
        finally {
            DuridUtils.close(null,null,connection);
        }
    }
    /*
    * 通用的。使用dbUtils测试查询单个记录
    * */
    public static Object QueryScalar(String sql,Object...params){
        Connection connection=null;
        try{
            //建立连接
            connection =DuridUtils.getConnection();
            //创建执行SQL增删改查的对象
            QueryRunner queryRunner=new QueryRunner();
            //执行
           Object obj=queryRunner.query(connection,sql,new ScalarHandler(),params);
            return obj;
        }
        catch (Exception e){
            throw  new RuntimeException();
        }
        finally {
            DuridUtils.close(null,null,connection);
        }
    }
    /*
    * 通用的使用dbUtils测试查询一条记录
    *
    * */
    public static <T> T QueryBean(String sql,Class<T> clazz,Object...params) throws SQLException {
    //建立连接
        Connection connection=DuridUtils.getConnection();
        //创建执行SQL增删改查对象
        QueryRunner queryRunner=new QueryRunner();
        //执行
        T query=queryRunner.query(connection,sql,new BeanHandler<T>(clazz),params);
        return query;
    }
    /*
    * 使用dbUtils测试查询多条记录
    *
    * */
    public static <T> List<T> QueryBeanList(String sql, Class<T> clazz, Object...params) throws SQLException {
        Connection connection = null;
        try {
            //建立连接
            connection = DuridUtils.getConnection();
            //创建执行SQL增删改查的对象
            QueryRunner queryRunner = new QueryRunner();
            //执行
            List<T> list = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), params);
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            DuridUtils.close(null, null, connection);
        }
    }
}
