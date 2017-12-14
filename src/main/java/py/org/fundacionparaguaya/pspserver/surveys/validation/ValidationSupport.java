package py.org.fundacionparaguaya.pspserver.surveys.validation;

import java.util.*;

/**
 * Created by rodrigovillalba on 10/24/17.
 */

public class ValidationSupport {
    private static final ValidationResult valid = new ValidationResult(){

        public boolean isValid(){ return true; }

        public Optional<String> getReason(){ return Optional.empty(); }

        @Override
        public String getPropertyName() {
            return null;
        }
    };

    private static final ValidationResults validList = new ValidationResults() {
        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Map<String, Collection<String>> asMap() {
            return Collections.emptyMap();
        }

        @Override
        public List<ValidationResult> asList() {
            return Collections.emptyList();
        }

        @Override
        public boolean add(ValidationResult result) {
            return false;
        }

        @Override
        public boolean addAll(ValidationResults result) {
            return false;
        }
    };

    static ValidationResult validResult(){
        return valid;
    }


    public static ValidationResults validResults() {return new ValidationList();}
}