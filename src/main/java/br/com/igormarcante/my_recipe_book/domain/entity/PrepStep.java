package br.com.igormarcante.my_recipe_book.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "prep_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrepStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Step order is mandatory")
    private Integer stepOrder;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}