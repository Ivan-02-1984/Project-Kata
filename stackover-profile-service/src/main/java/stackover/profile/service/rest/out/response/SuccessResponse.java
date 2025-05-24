package stackover.profile.service.rest.out.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class SuccessResponse<T> extends Response<T> {

    private T data;

    public SuccessResponse() {
        this.success = true;
    }

    public SuccessResponse(T data) {
        this.success = true;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessResponse<?> that = (SuccessResponse<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
