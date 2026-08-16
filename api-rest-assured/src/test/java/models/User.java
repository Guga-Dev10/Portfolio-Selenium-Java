package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Evita erro caso a API retorne campos extras que não mapeamos
public record User(
        Integer id,
        String name,
        String username,
        String email
) {}