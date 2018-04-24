import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDemo {
    @Test
    public void testcase1(){
        try {
            Assert.assertEquals(1,1);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.assertEquals(1,1);
    }
    @Test
    public void testcase2(){
        Assert.assertEquals(1,1);
    }
}