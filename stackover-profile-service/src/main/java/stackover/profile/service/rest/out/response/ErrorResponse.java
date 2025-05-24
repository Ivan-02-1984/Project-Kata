package stackover.profile.service.rest.out.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ErrorResponse<T> extends Response<T> {

    private Error error;

    public ErrorResponse(Error error) {
        this.success = false;
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse<?> that = (ErrorResponse<?>) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }
}
