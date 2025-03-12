package cz.kavka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequiredInformationDTO {

    private String establishment;

    private String organizationalStructure;

    private String contact;

    private  BasicDataDTO basicData;

    private String documents;


}
