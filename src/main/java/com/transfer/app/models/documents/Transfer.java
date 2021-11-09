package com.transfer.app.models.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Document("Transfer")
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @Id
    private String id;
    @NotBlank
    @NotNull
    private String originAccount;
    @NotBlank
    @NotNull
    private String destinationAccount;
    @NotNull
    private Double amountTransference;
    @NotNull
    private String codeTransference;
    private String descriptionTransference;
    private LocalDateTime dateTransference;
}
