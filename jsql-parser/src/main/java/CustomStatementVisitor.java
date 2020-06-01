import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/5/9 11:06 AM <br/>
 * @Author: youzhihao
 */
public class CustomStatementVisitor extends StatementVisitorAdapter {

    private Map<String, String> tableNameMap;

    private TablesNamesFinder finder = new TablesNamesFinder() {
        @Override
        public void visit(Table tableName) {
            String originName = tableName.getName();
            if (tableNameMap.containsKey(originName)) {
                tableName.setName(tableNameMap.get(tableName.getName()));
            }

        }
    };


    public CustomStatementVisitor(Map<String, String> tableNameMap) {
        this.tableNameMap = tableNameMap;
    }


    @Override
    public void visit(Select select) {
        select.getSelectBody().accept(finder);
    }

    @Override
    public void visit(Update update) {
        replaceTableName(update.getTable());
    }

    @Override
    public void visit(Delete delete) {
        replaceTableName(delete.getTable());
    }

    @Override
    public void visit(Insert insert) {
        replaceTableName(insert.getTable());
    }

    @Override
    public void visit(Alter alter) {
        replaceTableName(alter.getTable());
    }

    public void setTableNameMap(Map<String, String> tableNameMap) {
        this.tableNameMap = tableNameMap;
    }

    public void replaceTableName(Table originTable) {
        String originTableName = originTable.getName();
        if (tableNameMap.containsKey(originTableName)) {
            originTable.setName(tableNameMap.get(originTableName));
        }
    }

}
