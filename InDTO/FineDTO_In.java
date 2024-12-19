package com.example.capston3.InDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FineDTO_In {
    @NotNull(message = "Empty user id ")
    private Integer userId;
    @NotNull(message = "Empty renting Request Id ")
    private Integer rentingRequestId;
    @NotEmpty(message = "Empty description ")
    private String description;


}
