import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.example.converter.Base62Encoder.fromBase10;
import static org.example.converter.Base62Encoder.toBase10;

@RunWith(Parameterized.class)
public class Base62ConversionTest {
    private Logger logger = LoggerFactory.getLogger(Base62ConversionTest.class);

    @Parameterized.Parameter(0)
    public long value;
    @Parameterized.Parameter(1)
    public String base62value;
    @Parameterized.Parameter(2)
    public long revertedValue;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        long[] values = {0, Long.MAX_VALUE, 15018571};
        Object[][] data = new Object[values.length][3];
        for (int i = 0; i < values.length; i++) {
            String base62 = fromBase10(values[i]);
            long reverted = toBase10(base62);
            data[i] = new Object[]{values[i], base62, reverted};
        }
        return Arrays.asList(data);
    }

    @Test
    public void testBase62Conversion() {
        logger.info("Original value is " + value + " base62 converted is " + base62value);
        Assert.assertEquals(value, revertedValue);
    }
}
