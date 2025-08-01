package com.swp08.dpss.service.impls.survey;

import com.swp08.dpss.dto.requests.survey.AnswerOptionRequest;
import com.swp08.dpss.entity.survey.AnswerOption;
import com.swp08.dpss.repository.survey.AnswerOptionRepository;
import com.swp08.dpss.service.interfaces.survey.AnswerOptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AnswerOptionServiceImpl implements AnswerOptionService {
    private final AnswerOptionRepository answerOptionRepository;

    @Override
    @Transactional
    public AnswerOption addAnswerOption(AnswerOptionRequest request) {
        AnswerOption newAnswerOption = new AnswerOption();
        newAnswerOption.setContent(request.getContent());
        newAnswerOption.setCorrect(request.isCorrect());
        return answerOptionRepository.save(newAnswerOption);
    }
}
