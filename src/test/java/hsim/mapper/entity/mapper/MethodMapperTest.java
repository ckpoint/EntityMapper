package hsim.mapper.entity.mapper;

import hsim.mapper.entity.domain.MapAuditEntity;
import hsim.mapper.entity.domain.MapEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.transaction.NotSupportedException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class MethodMapperTest {

    @Test
    public void fieldNameTest_01() throws NoSuchMethodException, NotSupportedException {
        Method method = MapEntity.class.getMethod("getId");

        MethodMapper methodMapper = new MethodMapper(method);

        Assert.assertTrue(methodMapper.isGetter());
        Assert.assertTrue(methodMapper.getFieldName().equals("id"));
    }

    @Test
    public void fieldNameTest_02() throws NoSuchMethodException, NotSupportedException {
        Method method = MapEntity.class.getMethod("setId", Long.class);

        MethodMapper methodMapper = new MethodMapper(method);

        Assert.assertTrue(methodMapper.isSetter());
        Assert.assertTrue(methodMapper.getFieldName().equals("id"));
    }

    @Test
    public void fieldNameTest_03() throws NoSuchMethodException, NotSupportedException {
        Method method = MapAuditEntity.class.getMethod("getCreatedAt");

        MethodMapper methodMapper = new MethodMapper(method);

        Assert.assertTrue(methodMapper.isGetter());
        Assert.assertTrue(methodMapper.getFieldName().equals("createdAt"));

    }

    @Test
    public void invalidReturnTypeTest() throws NoSuchMethodException, NotSupportedException {
        Method invalidGetter = TestMethods.class.getMethod("getInvalid");
        NotSupportedException e = null;

        try {
            MethodMapper methodMapper = new MethodMapper(invalidGetter);
        } catch (NotSupportedException e1) {
            log.error(e1.toString());
            e = e1;
        }

        Assert.assertTrue(e != null);
    }

    @Test
    public void invalidSetterTest() throws NoSuchMethodException, NotSupportedException {
        Method invalidSetter = TestMethods.class.getMethod("setInvalid", String.class, String.class);
        NotSupportedException e = null;

        try {
            MethodMapper methodMapper = new MethodMapper(invalidSetter);
        } catch (NotSupportedException e1) {
            log.error(e1.toString());
            e = e1;
        }

        Assert.assertTrue(e != null);
    }

    @Test
    public void genericTypeTest_01() throws NoSuchMethodException, NotSupportedException {
        Method getter = TestMethods.class.getMethod("getLongList");
        Method setter = TestMethods.class.getMethod("setLongList", List.class);

        MethodMapper getMapper = new MethodMapper(getter);
        MethodMapper setMapper = new MethodMapper(setter);

        Assert.assertTrue(getMapper.getType().equals(setMapper.getType()));
    }

    @Test
    public void genericTypeTest_02() throws NoSuchMethodException, NotSupportedException {
        Method getter = TestMethods.class.getMethod("getLongList");
        Method setter = TestMethods.class.getMethod("setStringList", List.class);

        MethodMapper getMapper = new MethodMapper(getter);
        MethodMapper setMapper = new MethodMapper(setter);

        Assert.assertTrue(!getMapper.getType().equals(setMapper.getType()));
    }


}