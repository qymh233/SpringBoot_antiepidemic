package antiesys.antiepidemic;
import antiesys.antiepidemic.mapper.AdminInter;
import antiesys.antiepidemic.mapper.GoodsInter;
import antiesys.antiepidemic.mapper.ReportInter;
import antiesys.antiepidemic.mapper.UserInter;
import antiesys.antiepidemic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class demotest {
    @Autowired
    UserInter userInter;
    @Autowired
    AdminInter adminInter;
    @Autowired
    GoodsInter goodsInter;
    @Autowired
    ReportInter reportInter;
    @Autowired
    UserService userService;
    @Test
    public void test1(){
        System.out.println(userService.UserLogin(101040011,"111111"));
    }
}
