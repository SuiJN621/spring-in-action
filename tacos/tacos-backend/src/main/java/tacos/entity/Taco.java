package tacos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author Sui
 * @date 2019.04.16 9:43
 */
@Data
@Entity
public class Taco {

    @Id
    //Hibernate 5.0开始对于mysql的AUTO策略默认为SEQUENCE, 需要手动设置为IDENTITY
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity=Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    /**
     * 设置AOP, 在保存之前调用
     * @see javax.persistence.PreUpdate;
     * @see javax.persistence.PreRemove;
     * 对应还有@PostXxx注解
     */
    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
