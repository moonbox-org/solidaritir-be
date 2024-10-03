package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@ToString(exclude = "subCategories")
@NoArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = ALL, orphanRemoval = true)
    private Set<CategoryEntity> subCategories = new HashSet<>();

    // ----- Helper methods ----- //

    @PreUpdate
    @PrePersist
    private void validateParentCategory() {
        if (this.parentCategory != null && this.parentCategory.equals(this))
            throw new IllegalArgumentException("A category cannot be its own parent.");
    }

    /**
     * Adds a sub-category to this category.
     * This method ensures both sides of the bidirectional relationship are properly set.
     *
     * @param subCategory The sub-category to add.
     */
    public void addSubCategory(CategoryEntity subCategory) {
        Assert.notNull(subCategory, "SubCategory cannot be null");
        // Avoid adding the same sub-category multiple times
        if (this.subCategories.contains(subCategory))
            return;
        // Set this category as the parent of the sub-category
        subCategory.setParentCategory(this);
        // Add the sub-category to the set
        this.subCategories.add(subCategory);
    }

    /**
     * Removes a sub-category from this category.
     * This method ensures both sides of the bidirectional relationship are properly updated.
     *
     * @param subCategory The sub-category to remove.
     */
    public void removeSubCategory(CategoryEntity subCategory) {
        Assert.notNull(subCategory, "SubCategory cannot be null");
        // Remove the sub-category from the set
        if (this.subCategories.remove(subCategory)) {
            // Unset the parent of the sub-category
            subCategory.setParentCategory(null);
        }
    }

    /**
     * Helper method to check if the current category is a descendant of the potentialAncestor.
     * This helps in preventing cyclic relationships.
     *
     * @param potentialAncestor The category to check against.
     * @return true if the current category is a descendant, false otherwise.
     */
    private boolean isDescendantOf(CategoryEntity potentialAncestor) {
        CategoryEntity current = this.parentCategory;
        while (current != null) {
            if (current.equals(potentialAncestor)) {
                return true;
            }
            current = current.getParentCategory();
        }
        return false;
    }
}
