package cz.kavka.service.serviceinterface;

import cz.kavka.dto.EntranceExamDTO;

import java.io.IOException;

public interface EntranceExamService {

    EntranceExamDTO updateEntranceExam(EntranceExamDTO entranceExamDTO) throws IOException;

    EntranceExamDTO getEntranceExam() throws IOException;

    boolean isHidden() throws IOException;


}
