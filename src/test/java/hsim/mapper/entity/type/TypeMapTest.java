package hsim.mapper.entity.type;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class TypeMapTest {

    @Test
    public void longCastTest() {
        Integer input = 1;
        Long result = TypeMap.cast(input, Long.class);
        Assert.assertTrue(result.equals(1L));
    }

    @Test
    public void doubleCastTest_2() {
        Integer input = 1;
        Double result = TypeMap.cast(input, Double.class);
        Assert.assertTrue(result.equals(1.0));
    }

    @Test
    public void enumCastTest() {
        String value = "TESTA";
        TestEnum result = TypeMap.cast(value, TestEnum.class);
        Assert.assertTrue(result.equals(TestEnum.TESTA));

        value = "TESTB";
        result = TypeMap.cast(value, TestEnum.class);
        Assert.assertTrue(result.equals(TestEnum.TESTB));

    }

    @Test
    public void enumToStringTest() {
        TestEnum value = TestEnum.TESTA;

        String result = TypeMap.cast(value, String.class);
        Assert.assertTrue(result.equals("TESTA"));

    }


    @Test
    public void stringCastTest() {
        Long value = 1234567L;

        String result = TypeMap.cast(value, String.class);
        Assert.assertTrue(result.equals("1234567"));
    }

    @Test
    public void dateCastTest_01() {
        Date value = new Date();

        Date result = TypeMap.cast(value, Date.class);

        Assert.assertTrue(value != result);
        Assert.assertTrue(value.getTime() == result.getTime());
    }

    @Test
    public void dateCastTest_02() {
        Date value = new Date();

        Date result = TypeMap.cast(value.getTime(), Date.class);

        Assert.assertTrue(value != result);
        Assert.assertTrue(value.getTime() == result.getTime());
    }

    @Test
    public void dateCastTest_03() {

        DateTime value = new DateTime();

        Date result = TypeMap.cast(value, Date.class);

        Assert.assertTrue(result.getTime() == value.toDate().getTime());
    }


    @Test
    public void dateCastTest_04() {

        String value = "2019-01-01";

        Date result = TypeMap.cast(value, Date.class);

        DateTime dateTime = new DateTime("2019-01-01");

        Assert.assertTrue(result.getTime() == dateTime.toDate().getTime());
    }
}