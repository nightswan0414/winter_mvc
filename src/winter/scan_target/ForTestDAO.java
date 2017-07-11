package winter.scan_target;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ForTestDAO {

	@Autowired
	public SqlSessionTemplate sqlSession = null;
	
	public String findTheTime() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ForTest.findTheTime");
	}
}
