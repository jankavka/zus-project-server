package cz.kavka.service.serviceinterface;

import cz.kavka.dto.SchoolYearLinkDTO;

import java.io.IOException;

public interface SchoolYearLinkService {

    SchoolYearLinkDTO get() throws IOException;

    SchoolYearLinkDTO update(SchoolYearLinkDTO schoolYearLinkDTO) throws IOException;
}
