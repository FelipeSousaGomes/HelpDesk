package models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest(

        @Schema(description = "Requester id", example = "694c8c9fd49bc03503bd6aa1")
        @Size(min = 24, max = 36, message = "Invalid requester id")
        String requesterId,

        @Schema(description = "Requester id", example = "694c8c9fd49bc03503bd6aa1")
        @Size(min = 24, max = 36, message = "Invalid customer id")
        String customerId,

        @Schema(description = "Title of Order", example = "Fix my Computer")
        @Size(min = 3, max = 45, message = "The title must be between 3 and 45 characters")
        String title,
        @Schema(description = "Description of Order", example = "My computer is broken")
        @Size(min = 10, max = 3000, message = "The description must be between 10 and 3000 characters")
        String description,

        @Schema(description = "Status of Order", example = "Open")
        @Size(min = 4, max = 15, message = "The status must be between 4 and 15 characters")
        String status
) {
}
