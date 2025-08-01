package com.swp08.dpss.service.interfaces.survey;

import com.swp08.dpss.dto.requests.survey.AnswerOptionRequest;
import com.swp08.dpss.entity.survey.AnswerOption;

public interface AnswerOptionService {
    AnswerOption addAnswerOption(AnswerOptionRequest request);
}
