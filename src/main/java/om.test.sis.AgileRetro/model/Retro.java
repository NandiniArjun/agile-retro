package com.test.sis.AgileRetro.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Retro {

    @Id
    private String name;

    private String summary;

    private Date date;

    private List<String> participants;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Map<String, String>> feedback;
}