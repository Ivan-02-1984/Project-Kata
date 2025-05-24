package stackover.profile.service.rest.out.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class SeveralError extends Error {

    private List<FieldError> errors;

    public SeveralError(Integer code, String text, List<FieldError> fieldErrors) {
        super(code, text);
        this.errors = fieldErrors;
    }

    public SeveralError(Integer code, String text, Map<String, Object> meta, List<FieldError> fieldErrors) {
        super(code, text, meta);
        this.errors = fieldErrors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SeveralError that = (SeveralError) o;
        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), errors);
    }
}
