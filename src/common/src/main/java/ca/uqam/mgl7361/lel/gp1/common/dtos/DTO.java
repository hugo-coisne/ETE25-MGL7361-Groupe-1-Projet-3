package ca.uqam.mgl7361.lel.gp1.common.dtos;

import java.lang.reflect.Field;

public class DTO {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + " {");
        Field[] fields = this.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); // allow access to private fields
            try {
                sb.append(fields[i].getName()).append("=");
                if (fields[i].getType().equals(String.class)) {
                    sb.append("\"");
                }
                if (fields[i].getName().equals("password") && fields[i].get(this).toString().length() > 1) {
                    for (int j = 0; j < fields[i].get(this).toString().length(); j++){
                        sb.append("*");
                    }
                } else {
                    sb.append(fields[i].get(this));
                }
                if (fields[i].getType().equals(String.class)) {
                    sb.append("\"");
                }
            } catch (IllegalAccessException e) {
                sb.append("ERROR");
            }
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }

}
