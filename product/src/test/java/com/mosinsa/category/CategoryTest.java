package com.mosinsa.category;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CategoryTest {

    @Autowired
    CategoryRepository repository;

    @Test
    void ofSingleParam() {
        String testCategory = "test";

        Category test = Category.of(testCategory);
        Assertions.assertThat(test.getName()).isEqualTo(testCategory);
        Assertions.assertThat(test.getParent()).isNull();
        Assertions.assertThat(test.getChildren()).isEmpty();
    }

    @Test
    void ofMultiParamWithNull(){
        String testCategory = "test";

        Category test = Category.of(testCategory, null);
        Assertions.assertThat(test.getName()).isEqualTo(testCategory);
        Assertions.assertThat(test.getParent()).isNull();
        Assertions.assertThat(test.getChildren()).isEmpty();
    }


    @Test
    void ofMultiParamWithNormalData(){
        String testCategory = "test";
        String testParent = "testParent";

        Category parent = Category.of(testParent);
        Category test = Category.of(testCategory, parent);
        Assertions.assertThat(test.getName()).isEqualTo(testCategory);
        Assertions.assertThat(test.getParent()).isEqualTo(parent);
        Assertions.assertThat(test.getChildren()).isEmpty();
        Assertions.assertThat(parent.getChildren()).containsExactly(test);
    }

    @Test
    void equalsAndHashCode() {
        String value = "categoryB";
        Category categoryA = Category.of(value);
        Category save = repository.save(categoryA);
        Category categoryB = repository.findById(save.getId()).get();
        Category protectedConstructor = new Category();

        EqualsAndHashcodeUtils.equalsAndHashcode(categoryA, categoryB, protectedConstructor, Category.of(value+"XXX"));
    }
}