package br.com.felipe.oderserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import models.exceptions.StandardError;
import models.request.CreateOrderRequest;
import models.request.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "OrderController", description = "Controller responsible for order operations")
@RequestMapping("/api/orders")
public interface OrderController {

    @PostMapping
    @Operation(summary = "Save new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            )
    })
    ResponseEntity<Void> save(
            @Valid @RequestBody final CreateOrderRequest request
    );

    @Operation(summary = "Update Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))

            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            )

    })
    @PutMapping(value = "/{id}")
    ResponseEntity<OrderResponse> update(
            @Parameter(description = "Order id", required = true, example = "10")
            @PathVariable(name = "id") Long id,
            @Parameter(description = "Update Order Request", required = true)
            @Valid @RequestBody final UpdateOrderRequest request
    );

    @Operation(summary = "Find Order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))

            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            )
    })
    @GetMapping(value = "/{id}")
    ResponseEntity<OrderResponse> findById(
            @NotNull(message = "Order id cannot be null")
            @Parameter(description = "Order id", required = true, example = "10")
            @PathVariable final Long id);

    @Operation(summary = "Delete Order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))

            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            )
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteById(
            @NotNull(message = "Order id cannot be null")
            @Parameter(description = "Order id", required = true, example = "10")
            @PathVariable final Long id);

}
