package concurrent_counter;

/**
 * Created by kv on 18.12.16.
 */

public class InputValidatorNegativeInt implements InputValidator {

    public boolean validInput(int number) {
        if((number < 0) && (number & 1) == 0){
            return true;
        } else {
            return false;
        }
    }
}
