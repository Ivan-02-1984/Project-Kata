package stackover.profile.service.rest.out.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Error extends java.lang.Error {
    private Integer code;
    private String text;
    private Map<String, Object> meta;

    public Error(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Error(Integer code, String text, Map<String, Object> meta) {
        this.code = code;
        this.text = text;
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(code, error.code) &&
                Objects.equals(text, error.text) &&
                Objects.equals(meta, error.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, text, meta);
    }
}
