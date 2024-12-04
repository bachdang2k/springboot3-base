package vivas.service.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PmsDto {
    //@JsonProperty("method")
    private String method;
    //@JsonProperty("path")
    private String path;
}
