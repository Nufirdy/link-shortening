import org.example.utils.Base62ConvertUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.utils.Base62ConvertUtil.*;

public class MainTests {
    private Logger logger = LoggerFactory.getLogger(MainTests.class);

    @Test
    public void testBase62Conversion() {
        long value1 = Long.MAX_VALUE;
        long value2 = 0;
        long value3 = 15018571;

        String base62Value1 = fromBase10(value1);
        String base62Value2 = fromBase10(value2);
        String base62Value3 = fromBase10(value3);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Converted values are \n");
        stringBuilder.append(value1 + " " + base62Value1 + "\n");
        stringBuilder.append(value2 + " " + base62Value2 + "\n");
        stringBuilder.append(value3 + " " + base62Value3 + "\n");

        logger.info(stringBuilder.toString());

        long revertedValue1 = toBase10(base62Value1);
        long revertedValue2 = toBase10(base62Value2);
        long revertedValue3 = toBase10(base62Value3);

        Assert.assertEquals(value1, revertedValue1);
        Assert.assertEquals(value2, revertedValue2);
        Assert.assertEquals(value3, revertedValue3);
    }
}
