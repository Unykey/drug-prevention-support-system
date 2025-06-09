import com.swp08.dpss.dto.SurveyDto;
import com.swp08.dpss.entity.Survey;
import com.swp08.dpss.repository.SurveyRepository;
import com.swp08.dpss.service.interfaces.SurveyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey createSurvey(SurveyDto dto) {
        Survey survey = new Survey();
        survey.setName(dto.getName());
        survey.setDescription(dto.getDescription());
        return surveyRepository.save(survey);
    }

    @Override
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    @Override
    public Survey getSurveyById(long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id " + id));
    }
}
