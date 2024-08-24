package com.mosinsa.reaction.infra.kafka.channel;

import com.mosinsa.common.ex.ReviewError;
import com.mosinsa.common.ex.ReviewException;
import com.mosinsa.reaction.command.domain.TargetEntity;
import com.mosinsa.review.command.domain.ReviewId;
import com.mosinsa.review.infra.jpa.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewChannelProvider implements ChannelProvider {

    private final ReviewRepository repository;

    @Override
    public String provide(String targetId) {
        return repository.findById(ReviewId.of(targetId))
                .orElseThrow(() -> new ReviewException(ReviewError.NOT_FOUNT_REVIEW))
                .getProductId();
    }

    @Override
    public boolean isSupport(TargetEntity targetEntity) {
        return TargetEntity.REVIEW.equals(targetEntity);
    }
}
