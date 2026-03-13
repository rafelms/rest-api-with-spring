package br.com.rafelms.rest_with_spring.data.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailRequestDTO {

    private String to;
    private String subject;
    private String body;

}
