package com.web.ecomm.app.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Error {
    private String severity;
    private String message;
    private String type;

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.severity == null ? 0 : this.severity.hashCode());
        result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj == null) return false;
        else if (this.getClass() != obj.getClass()) return false;
        else {
            Error other = (Error) obj;
            if (this.severity == null) {
                if (other.severity != null) {
                    return false;
                }
            } else if (!this.severity.equals(other.severity)) {
                return false;
            }

            if (this.message == null) {
                if (other.message != null) {
                    return false;
                }
            } else if (!this.message.equals(other.message)) {
                return false;
            }

            if (this.type == null) {
                return other.type == null;
            } else if (!this.type.equals(other.type)) {
                return false;
            }

            return true;
        }
    }
}
