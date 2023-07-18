package loga.dev6.crud.dto;

import lombok.Data;
@Data
public class Client {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
