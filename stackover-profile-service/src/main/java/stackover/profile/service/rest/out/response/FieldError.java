package stackover.profile.service.rest.out.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class FieldError {

    private String field;

    private String text;

    public FieldError(String field, String text) {
        this.field = field;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldError that = (FieldError) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, text);
    }
}
