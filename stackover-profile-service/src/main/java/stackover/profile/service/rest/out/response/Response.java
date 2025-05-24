package stackover.profile.service.rest.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Response<T> {
    @JsonProperty
    protected Boolean success;

    public static SuccessBuilder ok() {
        return new SuccessBuilderImpl();
    }

    public static <T> Response<T> ok(T data) {
        return new SuccessBuilderImpl().data(data);
    }

    public static ErrorBuilder error() {
        return new ErrorBuilderImpl();
    }

    public static class SuccessBuilderImpl implements SuccessBuilder {

        @Override
        public <T> SuccessResponse<T> data(T data) {
            return new SuccessResponse<>(data);
        }

        @Override
        public <T> SuccessResponse<T> build() {
            return new SuccessResponse<>();
        }
    }

    public interface SuccessBuilder {
        <T> SuccessResponse<T> data(T data);
        <T> SuccessResponse<T> build();
    }

    public static class ErrorBuilderImpl implements ErrorBuilder {

        protected Integer code;
        protected String text;
        private Map<String, Object> meta = new HashMap<>();
        private List<FieldError> fieldErrors = new ArrayList<>();

        private MetaBuilder metaBuilder;
        private FieldErrorBuilder fieldErrorBuilder;

        public ErrorBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder code(HttpStatus status) {
            this.code = status.value();
            return this;
        }

        public ErrorBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public MetaBuilder meta() {
            if (this.metaBuilder == null) {
                this.metaBuilder = new MetaBuilderImpl(this);
            }
            return this.metaBuilder;
        }

        @Override
        public FieldErrorBuilder fieldErrors() {
            if (this.fieldErrorBuilder == null) {
                this.fieldErrorBuilder = new FieldErrorBuilderImpl(this);
            }
            return this.fieldErrorBuilder;
        }

        @Override
        public <T> ErrorResponse<T> build() {
            if (!meta.isEmpty() && fieldErrors.isEmpty()) {
                Error error = new Error(code, text, meta);
                return new ErrorResponse<>(error);
            } else if (meta.isEmpty() && !fieldErrors.isEmpty()) {
                SeveralError severalError = new SeveralError(code, text, fieldErrors);
                return new ErrorResponse<>(severalError);
            } else if (!meta.isEmpty()) {
                SeveralError severalError = new SeveralError(code, text, meta, fieldErrors);
                return new ErrorResponse<>(severalError);
            }
            Error error = new Error(code, text);
            return new ErrorResponse<>(error);
        }

        public void addMeta(String key, Object value) {
            this.meta.put(key, value);
        }

        public void addFieldError(String field, String text) {
            this.fieldErrors.add(new FieldError(field, text));
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public interface ErrorBuilder {
        ErrorBuilder code(Integer code);
        ErrorBuilder code(HttpStatus status);
        ErrorBuilder text(String text);
        <T> ErrorResponse<T> build();
        MetaBuilder meta();
        FieldErrorBuilder fieldErrors();
    }

    public static class MetaBuilderImpl implements MetaBuilder {

        private ErrorBuilderImpl errorBuilder;

        public MetaBuilderImpl(ErrorBuilderImpl errorBuilder) {
            this.errorBuilder = errorBuilder;
        }

        @Override
        public MetaBuilder add(String key, Object value) {
            this.errorBuilder.addMeta(key, value);
            return this;
        }

        @Override
        public ErrorBuilder then() {
            return this.errorBuilder;
        }

        @Override
        public <T> ErrorResponse<T> build() {
            return this.errorBuilder.build();
        }
    }

    public interface MetaBuilder {
        MetaBuilder add(String key, Object value);
        ErrorBuilder then();
        <T> ErrorResponse<T> build();
    }

    public static class FieldErrorBuilderImpl implements FieldErrorBuilder {

        private ErrorBuilderImpl errorBuilder;

        public FieldErrorBuilderImpl(ErrorBuilderImpl errorBuilder) {
            this.errorBuilder = errorBuilder;
        }

        @Override
        public FieldErrorBuilder add(String field, String text) {
            this.errorBuilder.addFieldError(field, text);
            return this;
        }

        @Override
        public ErrorBuilder then() {
            return this.errorBuilder;
        }

        @Override
        public <T> ErrorResponse<T> build() {
            return this.errorBuilder.build();
        }
    }

    public interface FieldErrorBuilder {
        FieldErrorBuilder add(String field, String text);
        ErrorBuilder then();
        <T> ErrorResponse<T> build();
    }
}
