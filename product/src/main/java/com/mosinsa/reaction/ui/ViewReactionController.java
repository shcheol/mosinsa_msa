package com.mosinsa.reaction.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.GuestOrLogin;
import com.mosinsa.reaction.query.application.ReactionReader;
import com.mosinsa.reaction.query.application.dto.ReactionSearchCondition;
import com.mosinsa.reaction.ui.request.ReactionRequest;
import com.mosinsa.reaction.ui.response.ReactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
public class ViewReactionController {

    private final ReactionReader reader;

    @GetMapping("/total")
    public ResponseEntity<ReactionResult> reactionCount(@ModelAttribute ReactionRequest request,
                                                        @GuestOrLogin CustomerInfo customerInfo) {
        ReactionSearchCondition condition =
                new ReactionSearchCondition(request.target(), request.targetId(), request.reactionType(), customerInfo.id());

        boolean reacted = reader.hasReacted(condition);
        long total = reader.total(condition);
        return ResponseEntity.ok().body(new ReactionResult(total, reacted));
    }

}
