import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/5/9 10:04 AM <br/>
 * @Author: youzhihao
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Map<String, String> tableNameMap = new HashMap<>();
        tableNameMap.put("tab1", "table1");
        tableNameMap.put("tab2", "table2");
        CustomStatementVisitor visitor=new CustomStatementVisitor(tableNameMap);
        Statement stmt1 = CCJSqlParserUtil.parse("SELECT xxxxx FROM tab1 as t1,tab2 as t2");
        stmt1.accept(visitor);
        System.out.println(stmt1.toString());
        Statement stmt2 = CCJSqlParserUtil.parse("update tab2 set name='youzhihao'");
        stmt2.accept(visitor);
        System.out.println(stmt2.toString());
        Statement stmt3 = CCJSqlParserUtil.parse("insert into tab1 (id,name)VALUES(100,'youzhihao')");
        stmt3.accept(visitor);
        System.out.println(stmt3.toString());






    }


}
