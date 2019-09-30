import com.util.DatabaseUtils;

import java.sql.Connection;

/**
 * @Description:TODO
 * @Author hjsoft
 * @Date 2019/9/30 11:31
 * @Version V1.0
 **/
public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        conn = DatabaseUtils.getConnection(conn);
        System.out.println("success");
    }
}