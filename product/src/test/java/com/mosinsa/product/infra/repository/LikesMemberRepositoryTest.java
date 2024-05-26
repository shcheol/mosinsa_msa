package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.product.LikesMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class LikesMemberRepositoryTest {

    @Autowired
    private LikesMemberRepository repository;

    @Test
    void equalsAndHashCode(){
        LikesMember likesMemberIdA = repository.findById("likesMemberId1").get();
        LikesMember likesMemberIdB = repository.findById("likesMemberId1").get();

        assertThat(likesMemberIdA).isEqualTo(likesMemberIdB).hasSameHashCodeAs(likesMemberIdB);

        LikesMember likesMemberIdC = repository.findById("likesMemberId2").get();
        assertThat(likesMemberIdA).isNotEqualTo(likesMemberIdC).doesNotHaveSameHashCodeAs(likesMemberIdC);


    }


}