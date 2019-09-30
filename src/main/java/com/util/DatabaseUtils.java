package com.util;

import javax.sql.RowSet;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.*;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * @Description:TODO
 * @Author hjsoft
 * @Date 2019/9/30 11:22
 * @Version V1.0
 **/
public class DatabaseUtils {

    protected static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
    // 连接服务器和数据库ServletUser
    protected static String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=mydatabase";
    protected static String userName = "sa"; // 默认用户名
    protected static String userPwd = "123"; // 密码

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        DatabaseUtils.driverName = driverName;
    }

    public static String getDbURL() {
        return dbURL;
    }

    public static void setDbURL(String dbURL) {
        DatabaseUtils.dbURL = dbURL;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        DatabaseUtils.userName = userName;
    }

    public static String getUserPwd() {
        return userPwd;
    }

    public static void setUserPwd(String userPwd) {
        DatabaseUtils.userPwd = userPwd;
    }

    /**
     * 获取数据库连接
     * @param conn
     * @return
     */
    public static Connection getConnection(Connection conn){
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);
        }catch (Exception e){
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭数据库连接一系列对象  guodd 2014-11-26
     * @param obj  支持类型：connection、rowset、resultset、statement、preparedstatement
     * @return boolean 是否成功
     */
    public static boolean closeDbObj(Object obj){
        boolean isClosed = true;

        if(obj==null) {
            return isClosed;
        }

        try{
            if(obj instanceof Connection){
                ((Connection)obj).close();
            }else if(obj instanceof RowSet){
                ((RowSet)obj).close();
            }else if(obj instanceof ResultSet){
                ((ResultSet)obj).close();
            }else if(obj instanceof Statement){
                ((Statement) obj).close();
            }else if(obj instanceof PreparedStatement){
                ((PreparedStatement) obj).close();
            }else{
                isClosed = false;
            }
        }catch(Exception e){
            e.printStackTrace();
            isClosed = false;
        }

        return isClosed;
    }


    /**
     * @Title closeIoResource
     * @Description 关闭IO资源
     * @param obj  支持类型：
     * <ul>
     * <li>InputStream</li>
     * <li>OutputStream</li>
     * <li>Reader</li>
     * <li>Writer</li>
     * <li>ZipFile(子类包括:java.util.jar.JarFile)</li>
     * <li>DocListener(子类包括:PdfWriter,DocWriter,com.lowagie.text.Document)</li>
     * <li>IndexWriter</li>
     * </ul>
     * @author dengcan
     * @return void
     */
    public static void closeIoResource(Object obj) {
        if (obj == null) {
            return;
        }

        try {
            if (obj instanceof InputStream) {
                ((InputStream) obj).close();
            } else if (obj instanceof OutputStream) {
                ((OutputStream) obj).close();
            } else if (obj instanceof Reader) {
                ((Reader) obj).close();
            } else if (obj instanceof Writer) {
                ((Writer) obj).close();
            } else if (obj instanceof ZipFile) {
                ((ZipFile) obj).close();
            } /*else if (obj instanceof DocListener) { // PdfWriter,DocWriter,com.lowagie.text.Document
                ((DocListener) obj).close();
            } else if (obj instanceof IndexWriter) {
                ((IndexWriter) obj).close();
            }*/
        } catch(java.io.IOException ex){
            //ClientAbortException是IOException ，抛出此异常和客户端浏览器有关但不影响功能，只是抛出来到控制台不好看，查网上资料针对此异常不做堆栈输出
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭与数据源的连接,释放资源.是closeIoResource和closeDbObj的并集
     * @param dataSource 可以是IO流也可以是数据库资源
     * @author lium
     */
    public static void closeResource(Object dataSource) {
        closeIoResource(dataSource);
        closeDbObj(dataSource);
    }

    public static void close(List resourceList){
        for(Object resource : resourceList){
            closeResource(resource);
        }
    }

}