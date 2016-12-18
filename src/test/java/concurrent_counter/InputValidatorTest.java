package concurrent_counter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kv on 18.12.16.
 */
public class InputValidatorTest {

    InputValidator nt = new InputValidatorNegativeInt();
    InputValidator pt = new InputValidatorPositiveInt();

    @Test
    public void negativeIntTest(){
        Assert.assertTrue("", nt.validInput(-1000));
    }

    @Test
    public void positiveIntTest(){
        Assert.assertTrue("", pt.validInput(1000));
    }
}
